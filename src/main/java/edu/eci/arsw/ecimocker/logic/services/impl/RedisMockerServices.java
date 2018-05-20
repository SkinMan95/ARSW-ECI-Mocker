/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.ecimocker.logic.services.impl;

import edu.eci.arsw.ecimocker.entities.CanvasObject;
import edu.eci.arsw.ecimocker.entities.Session;
import edu.eci.arsw.ecimocker.entities.User;
import edu.eci.arsw.ecimocker.logic.services.MockerServices;
import edu.eci.arsw.ecimocker.logic.services.MockerServicesException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author camila
 */
public class RedisMockerServices implements MockerServices{
    
    public static final int TOKEN_BYTE_SIZE = 32; // 256 bits
    public static final String TOKEN_DICT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final AtomicInteger SESSIONS_IDS = new AtomicInteger(0);
    private static final AtomicInteger USER_IDS = new AtomicInteger(0);

    private static final Random RANDOM = new Random();

    @Override
    public int addNewSession(String sessionName) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Session> getActiveSessions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeSession(int sessionId) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerUser(User newUser) throws MockerServicesException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
