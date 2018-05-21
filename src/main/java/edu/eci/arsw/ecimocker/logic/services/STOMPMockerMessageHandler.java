package edu.eci.arsw.ecimocker.logic.services;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.CanvasObjectContainer;
import edu.eci.arsw.ecimocker.entities.User;
import static edu.eci.arsw.ecimocker.logic.services.MockerAPIServices.LOGGING;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
@Controller
public class STOMPMockerMessageHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    MockerServices services;
    
    private void log(String str) {
        if (LOGGING) {
            System.out.println(str);
        }
    }

    private void error(String str) {
        System.err.println(str);
    }

    Map<CanvasObject, String> objectsTopics;

    public STOMPMockerMessageHandler() {
        objectsTopics = new HashMap<>();
    }
    
    @PostConstruct
    public void assignActionsToServices() throws Exception {
        Callback c = (atts) -> {
            try {
                sendObjectsTopics(Integer.parseInt(atts.get("session")));
            } catch (Exception ex) {
                System.err.println("Error parseando entero en callback: " + ex.getMessage());
            }
        };
        
        services.addAction("newObject", c);
        services.addAction("deleteObject", c);
        
        c = (atts) -> {
            try {
                sendActiveUsers(Integer.parseInt(atts.get("session")));
            } catch (Exception ex) {
                System.err.println("Error parseando entero en callback: " + ex.getMessage());
            }
        };
        
        services.addAction("updateUser", c);
    }

    @MessageMapping("{session}.object.{objid}")
    public void handleObjectEvent(CanvasObjectContainer objcont, @DestinationVariable String session, @DestinationVariable String objid) throws Exception {
        if (Integer.parseInt(objid) != objcont.getObject().getObjId()) {
            throw new Exception("objeto invalido: " + Integer.parseInt(objid) + " vs " + objcont.getObject());
        }
        services.updateObject(Integer.parseInt(session), objcont.getObject(), objcont.getToken());
        msgt.convertAndSend("/topic/" + session + ".object." + objid, objcont.getObject());
    }

    private Map<Integer, String> mapObjectsToTopics(List<CanvasObject> objs) {
        Map<Integer, String> r = new TreeMap<>();

        for (CanvasObject obj : objs) {
            if (!objectsTopics.containsKey(obj)) {
                objectsTopics.put(obj, "/topic/" + obj.getSession() + "/object." + obj.getObjId());
            }
            
            assert !r.containsKey(obj.getObjId());
            r.put(obj.getObjId(), objectsTopics.get(obj));
        }
        
        return r;
    }

    public void sendObjectsTopics(int session) throws Exception {
        List<CanvasObject> objects = services.getObjectsFromSession(session);

        Map<Integer, String> r = mapObjectsToTopics(objects);
        
        log("Envia objetos por topico: " + "/topic/" + session + "/objects");
        
        msgt.convertAndSend("/topic/" + session + "/objects", r);
    }

    public void sendActiveUsers(int session) throws Exception {
        List<User> users = services.getUsersInSession(session);

        log("Envia usuarios por topico: " + "/topic/" + session + "/users");
        
        msgt.convertAndSend("/topic/" + session + "/users", users);
    }
    
}
