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
import java.util.concurrent.atomic.AtomicInteger;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
@Service
public class ServerMockerServices implements MockerServices {

    public static final int TOKEN_BYTE_SIZE = 32; // 256 bits
    public static final String TOKEN_DICT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final AtomicInteger SESSIONS_IDS = new AtomicInteger(0);
    private static final AtomicInteger USER_IDS = new AtomicInteger(0);

    private static final Random RANDOM = new Random();

    Map<String, Session> sessions;
    Map<String, User> users;
    Map<String, Pair<User, Session>> tokens;
    Set<String> tokensUsed;
    Map<Integer, Map<Integer, CanvasObject>> sessionObjects;

    public ServerMockerServices() {
        sessions = new ConcurrentHashMap<>();
        users = new ConcurrentHashMap<>();
        tokens = new ConcurrentHashMap<>();
        tokensUsed = new ConcurrentSkipListSet<>();
        sessionObjects = new ConcurrentHashMap<>();
    }

    @Override
    public int addNewSession(String sessionName) throws MockerServicesException {
        if (sessionName == null) {
            throw new NullPointerException("sessionName es null");
        }

        if (sessions.containsKey(sessionName)) {
            throw new MockerServicesException("Ya existe una sesion con el mismo nombre");
        }

        int id = SESSIONS_IDS.getAndAdd(1);
        sessions.put(sessionName, new Session(id, sessionName));
        sessionObjects.put(id, new ConcurrentHashMap<>());
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
        if (s == null) {
            throw new NullPointerException("session es null");
        }

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
        if (u == null) {
            throw new NullPointerException("user es null");
        }

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
        if (newUser == null) {
            throw new NullPointerException("newUser es null");
        }

        if (userExists(newUser)) {
            throw new MockerServicesException("El usuario ya existe");
        }

        newUser.setUserId(USER_IDS.getAndAdd(1));
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
        if (user == null) {
            throw new NullPointerException("user es null");
        }

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

    private String generateNewToken() { // TODO: cambiar a alfanumerico
        StringBuilder res = new StringBuilder();
        do {
            for (int i = 0; i < TOKEN_BYTE_SIZE; i++) {
                res.append(TOKEN_DICT.charAt(RANDOM.nextInt(TOKEN_DICT.length())));
            }
        } while (tokensUsed.contains(res.toString()));

        return res.toString();
    }

    @Override
    public String getNewToken(int session, User user) throws MockerServicesException {
        if (user == null) {
            throw new NullPointerException("user es null");
        }

        if (!sessionExists(session)) {
            throw new MockerServicesException("La sesion no existe.");
        }

        if (!userExists(user)) {
            throw new MockerServicesException("El usuario no esta registrado.");
        }

        String tk = getToken(session, user);
        Pair<User, Session> tkVal = (tk != null) ? tokens.get(tk) : new Pair<>(user, getSession(session));

        if (tk != null) {
            tokens.remove(tk);
        }

        String newToken;
        do {
            newToken = generateNewToken();
        } while (tokens.containsKey(newToken));
        tokens.put(newToken, tkVal);

        return newToken;
    }

    @Override
    public boolean isValidToken(int session, String token) throws MockerServicesException {
        if (token == null) {
            throw new NullPointerException("token es null");
        }

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
        if (token == null) {
            throw new NullPointerException("token es null");
        }

        if (!tokens.containsKey(token)) {
            throw new MockerServicesException("El token no existe");
        }

        tokens.remove(token);
        assert tokensUsed.contains(token);
        tokensUsed.remove(token);
    }

    @Override
    public List<CanvasObject> getObjectsFromSession(int session) throws MockerServicesException {
        if (!sessionObjects.containsKey(session)) {
            throw new MockerServicesException("La sesion no existe");
        }

        return new ArrayList<>(sessionObjects.get(session).values());
    }

    @Override
    public CanvasObject getObject(int session, int objId) throws MockerServicesException {
        if (!sessionObjects.containsKey(session)) {
            throw new MockerServicesException("La sesion no existe");
        }

        Map<Integer, CanvasObject> objects = sessionObjects.get(session);

        if (!objects.containsKey(objId)) {
            throw new MockerServicesException("El objeto no existe en la sesion " + session);
        }

        return objects.get(objId);
    }

    @Override
    public void addObject(int session, CanvasObject newObj, String token) throws MockerServicesException {
        if (!sessionObjects.containsKey(session)) {
            throw new MockerServicesException("La sesion no existe");
        }

        if (!isValidToken(session, token)) {
            throw new MockerServicesException("El token no es valido");
        }

        sessionObjects.get(session).put(newObj.getObjId(), newObj);
    }

    @Override
    public void removeObject(int session, int objId, String token) throws MockerServicesException {
        if (!sessionObjects.containsKey(session)) {
            throw new MockerServicesException("La sesion no existe");
        }

        if (!isValidToken(session, token)) {
            throw new MockerServicesException("El token no es valido");
        }

        if (!sessionObjects.get(session).containsKey(objId)) {
            throw new MockerServicesException("El objeto no existe");
        }

        sessionObjects.get(session).remove(objId);
    }

    @Override
    public void updateObject(int session, CanvasObject updObj, String token) throws MockerServicesException {
        if (!sessionObjects.containsKey(session)) {
            throw new MockerServicesException("La sesion no existe");
        }

        if (!isValidToken(session, token)) {
            throw new MockerServicesException("El token no es valido");
        }

        if (!sessionObjects.get(session).containsKey(updObj.getObjId())) {
            throw new MockerServicesException("El objeto no existe");
        }

        sessionObjects.get(session).put(updObj.getObjId(), updObj);
    }

}
