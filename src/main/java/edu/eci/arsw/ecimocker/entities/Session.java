package edu.eci.arsw.ecimocker.entities;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class Session {

    private int sessionID;
    private String sessionName;

    public Session() {
        this(0, "");
    }

    public Session(int id) {
        this(id, "");
    }

    public Session(int id, String sessionName) {
        this.sessionID = id;
        this.sessionName = sessionName;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

}
