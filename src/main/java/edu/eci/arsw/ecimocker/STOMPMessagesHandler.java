package edu.eci.arsw.ecimocker;

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
    public void handlePointEvent(String pt, @DestinationVariable String drawnum) throws Exception {
        System.out.println("New point received!: " + pt);
        msgt.convertAndSend("/topic/newpoint." + drawnum, pt);
    }
}
