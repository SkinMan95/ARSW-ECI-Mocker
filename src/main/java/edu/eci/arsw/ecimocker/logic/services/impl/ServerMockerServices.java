package edu.eci.arsw.ecimocker.logic.services.impl;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.Session;
import edu.eci.arsw.ecimocker.entities.User;
import edu.eci.arsw.ecimocker.logic.services.MockerServices;
import edu.eci.arsw.ecimocker.logic.services.MockerServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
@Service
public class ServerMockerServices implements MockerServices {

    private static int sessionsIDS = 0;
    private static int userIDS = 0;

    Map<String, Session> sessions;
    Map<String, User> users;
    Map<String, Pair<User, Session>> tokens;

    public ServerMockerServices() {
        sessions = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
    }

    @Override
    public int addNewSession(String sessionName) throws MockerServicesException {
        if (sessions.containsKey(sessionName)) {
            throw new MockerServicesException("Ya existe una sesion con el mismo nombre");
        }

        int id = sessionsIDS++;
        sessions.put(sessionName, new Session(id, sessionName));
        return id;
    }

    @Override
    public List<Session> getActiveSessions() {
        return new ArrayList<>(sessions.values());
    }

    @Override
    public void removeSession(int sessionId) throws MockerServicesException {
        List<Session> s = new ArrayList<>(sessions.values());
        Session res = null;
        for (int i = 0; i < s.size() && res == null; i++) {
            if (s.get(i).getSessionID() == sessionId) {
                res = s.get(i);
            }
        }
        
        if (res == null) {
            throw new MockerServicesException("La sesion con ID: " + sessionId + " no existe.");
        }
        
        if (sessionHasUsers(res)) {
            throw new MockerServicesException("La sesion con ID: " + sessionId + " contiene usuarios actualmente. Debe esta vacia.");
        }
        
        sessions.remove(res.getSessionName());
    }
    
    private boolean sessionHasUsers(Session s) {
        List<Pair<User, Session>> rel = new ArrayList<>(tokens.values());
        
        boolean r = false;
        for (int i = 0; i < rel.size() && !r; i++) {
            if (rel.get(i).getValue() == s) { // Comparacion literal
                r = true;
            }
        }
        
        return r;
    }

    private boolean userExists(User u) {
        return false;
    }
    
    @Override
    public void registerUser(User newUser) throws MockerServicesException {
        if (userExists(newUser)) {
            throw new MockerServicesException("El usuario ya existe");
        }
        
        users.put(newUser.getUserName(), newUser);
    }

    @Override
    public List<User> getUsersInSession(int session) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNewToken(int session, User user) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidToken(int session, String token) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dissociateUserFromSession(String token) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CanvasObject> getObjectsFromSession(int session) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CanvasObject getObject(int session, int objId) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObject(int session, CanvasObject newObj, String token) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeObject(int session, int objId, String token) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateObject(int session, CanvasObject updObj, String token) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
