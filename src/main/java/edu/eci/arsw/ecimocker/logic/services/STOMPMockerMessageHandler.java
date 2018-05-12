package edu.eci.arsw.ecimocker.logic.services;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.CanvasObjectContainer;
import edu.eci.arsw.ecimocker.entities.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

    Map<CanvasObject, String> objectsTopics;

    public STOMPMockerMessageHandler() {
        objectsTopics = new HashMap<>();
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
                objectsTopics.put(obj, "/topic/" + obj.getSession() + ".object." + obj.getObjId());
            }
            
            assert !r.containsKey(obj.getObjId());
            r.put(obj.getObjId(), objectsTopics.get(obj));
        }
        
        return r;
    }

    public void sendObjectsTopics(int session) throws Exception {
        List<CanvasObject> objects = services.getObjectsFromSession(session);

        Map<Integer, String> r = mapObjectsToTopics(objects);
        
        msgt.convertAndSend("/topic/" + session + ".objects", r);
    }

    public void sendActiveUsers(int session) throws Exception {
        List<User> users = services.getUsersInSession(session);

        msgt.convertAndSend("/topic/" + session + ".users", users);
    }

}
