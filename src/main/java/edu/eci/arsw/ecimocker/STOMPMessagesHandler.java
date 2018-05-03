package edu.eci.arsw.ecimocker;

import edu.eci.arsw.ecimocker.CanvasObject.CanvasObject;
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
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("newpoint.{drawnum}")
    public void handleObjectEvent(CanvasObject obj, @DestinationVariable String session) throws Exception {
        System.out.println("New object received!: " + obj);
        msgt.convertAndSend("/topic/newobject." + session, obj);
    }
}
