package edu.eci.arsw.ecimocker.logic.services.impl;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.Session;
import edu.eci.arsw.ecimocker.entities.User;
import edu.eci.arsw.ecimocker.logic.services.MockerServices;
import edu.eci.arsw.ecimocker.logic.services.MockerServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
@Service
public class ServerMockerServices implements MockerServices {

    public static final int TOKEN_BYTE_SIZE = 32; // 256 bits

    private static int sessionsIDS = 0;
    private static int userIDS = 0;

    private static final Random rand = new Random();

    Map<String, Session> sessions;
    Map<String, User> users;
    Map<String, Pair<User, Session>> tokens;
    Set<String> tokensUsed;

    public ServerMockerServices() {
        sessions = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
        tokensUsed = new ConcurrentSkipListSet<>();
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
        List<User> us = new ArrayList<>(users.values());
        boolean res = false;

        for (int i = 0; i < us.size() && !res; i++) {
            if (us.get(i).getUserName().equals(u.getUserName())) {
                res = true;
            }
        }

        return res;
    }

    @Override
    public void registerUser(User newUser) throws MockerServicesException {
        if (userExists(newUser)) {
            throw new MockerServicesException("El usuario ya existe");
        }

        newUser.setUserId(userIDS++);
        users.put(newUser.getUserName(), newUser);
    }

    @Override
    public List<User> getUsersInSession(int session) throws MockerServicesException {
        List<Pair<User, Session>> us = new ArrayList<>(tokens.values());
        List<User> res = new ArrayList<>();

        for (Pair<User, Session> u : us) {
            if (u.getValue().getSessionID() == session) {
                res.add(u.getKey());
            }
        }

        return res;
    }

    private String getToken(int session, User user) {
        String res = null;

        for (String token : tokens.keySet()) {
            Pair<User, Session> us = tokens.get(token);
            if (us.getKey().getUserName().equals(user.getUserName())
                    && us.getValue().getSessionID() == session) {
                assert res == null;
                res = token;
            }
        }

        return res;
    }

    private boolean sessionExists(int session) {
        return getSession(session) != null;
    }

    private Session getSession(int session) {
        Session res = null;

        List<Session> s = new ArrayList<>(sessions.values());
        for (int i = 0; i < s.size() && res == null; i++) {
            if (s.get(i).getSessionID() == session) {
                res = s.get(i);
            }
        }

        return res;
    }

    private String generateNewToken() {
        String res = null;
        do {
            byte[] tkBytes = new byte[TOKEN_BYTE_SIZE];
            rand.nextBytes(tkBytes);
            res = new String(tkBytes);
        } while (tokensUsed.contains(res));

        return res;
    }

    @Override
    public String getNewToken(int session, User user) throws MockerServicesException {
        if (!sessionExists(session)) {
            throw new MockerServicesException("La sesion no existe.");
        }

        if (!userExists(user)) {
            throw new MockerServicesException("El usuario no esta registrado.");
        }

        String tk = getToken(session, user);
        Pair<User, Session> tkVal = (tk != null) ? tokens.get(tk) : new Pair<>(user, getSession(session));

        tokens.remove(tk);

        String newToken;
        do {
            newToken = generateNewToken();
        } while (tokens.containsKey(newToken));
        tokens.put(newToken, tkVal);

        return newToken;
    }

    @Override
    public boolean isValidToken(int session, String token) throws MockerServicesException {
        if (!sessionExists(session)) {
            throw new MockerServicesException("La sesion " + session + " no existe");
        }

        if (!tokens.containsKey(token)) {
            throw new MockerServicesException("El token no existe");
        }

        return tokens.get(token).getValue().getSessionID() == session;
    }

    @Override
    public void dissociateUserFromSession(String token) throws MockerServicesException {
        if (!tokens.containsKey(token)) {
            throw new MockerServicesException("El token no existe");
        }

        tokens.remove(token);
        tokensUsed.remove(token);
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
