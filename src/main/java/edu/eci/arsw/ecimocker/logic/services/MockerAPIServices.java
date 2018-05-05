package edu.eci.arsw.ecimocker.logic.services;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.Session;
import edu.eci.arsw.ecimocker.entities.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/mocker")
public class MockerAPIServices {
    
    @Autowired
    MockerServices services;
    
    @PostMapping("/sessions/newsession")
    public ResponseEntity<?> addNewSession(@RequestBody String sessionName) {
        try {
            return new ResponseEntity<>(services.addNewSession(sessionName), HttpStatus.CREATED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/sessions")
    public ResponseEntity<List<Session>> getActiveSessions() {
        return new ResponseEntity<>(services.getActiveSessions(), HttpStatus.OK);
    }
    
    @DeleteMapping("/sessions/{sessionid}")
    public ResponseEntity<?> removeSession(@PathVariable("sessionid") int session) {
        try {
            services.removeSession(session);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @PostMapping("/users/newuser")
    public ResponseEntity<?> registerUser(@RequestBody User newUser) {
        try {
            services.registerUser(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/users/{sessionid}")
    public ResponseEntity<?> getUsersInSession(@PathVariable int sessionid) {
        try {
            return new ResponseEntity<>(services.getUsersInSession(sessionid), HttpStatus.OK);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @PostMapping("/tokens/{sessionid}")
    public ResponseEntity<String> getNewToken(@PathVariable("sessionid") int session, @RequestBody User user) {
        try {
            return new ResponseEntity<>(services.getNewToken(session, user), HttpStatus.CREATED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/token/isvalid/{sessionid}")
    public ResponseEntity<?> isValidToken(@PathVariable("sessionid") int session, @RequestParam("token") String token) {
        try {
            return new ResponseEntity<>(services.isValidToken(session, token), HttpStatus.OK);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @DeleteMapping("/users")
    public ResponseEntity<?> dissociateUserFromSession(@RequestBody String token) {
        try {
            services.dissociateUserFromSession(token);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/sessions/{sessionid}/objects")
    public ResponseEntity<?> getObjectsFromSession(@PathVariable("sessionid") int session) {
        try {
            return new ResponseEntity<>(services.getObjectsFromSession(session), HttpStatus.OK);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @GetMapping("/sessions/{sessionid}/objects/{objid}")
    public ResponseEntity<?> getObject(@PathVariable("sessionid") int session, @PathVariable("objid") int objId) {
        try {
            return new ResponseEntity<>(services.getObject(session, objId), HttpStatus.OK);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @PostMapping("/sessions/{sessionid}/newobject")
    public ResponseEntity<?> addObject(@PathVariable("sessionid") int session, @RequestBody CanvasObject newObj, @RequestParam("token") String token) {
        try {
            services.addObject(session, newObj, token);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @DeleteMapping("/sessions/{sessionid}/object/{objid}")
    public ResponseEntity<?> removeObject(@PathVariable("sessionid") int session, @PathVariable("objid") int objId, @RequestParam("token") String token) {
        try {
            services.removeObject(session, objId, token);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
    
    @PutMapping("/sessions/{sessionid}/object")
    public ResponseEntity<?> updateObject(@PathVariable("sessionid") int session, @RequestBody CanvasObject updObj, @RequestParam("token") String token) {
        try {
            services.updateObject(session, updObj, token);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (MockerServicesException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
