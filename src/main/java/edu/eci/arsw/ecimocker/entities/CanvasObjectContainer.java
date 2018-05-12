package edu.eci.arsw.ecimocker.entities;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class CanvasObjectContainer {

    private CanvasObject object;
    private String token;

    public CanvasObject getObject() {
        return object;
    }

    public void setObject(CanvasObject object) {
        this.object = object;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
