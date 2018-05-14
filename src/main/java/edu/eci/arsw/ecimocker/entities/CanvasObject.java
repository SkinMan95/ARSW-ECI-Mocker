package edu.eci.arsw.ecimocker.entities;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class CanvasObject implements Comparable<CanvasObject> {

    private int objId;
    private boolean selected;
    private int userId;
    private int session;
    private String type;
    private String attributes;

    private static AtomicInteger staticID = new AtomicInteger(0);

    public CanvasObject() {

    }

    public CanvasObject(int objId) {
        this.setObjId(objId);
    }

    public int getObjId() {
        return objId;
    }

    public void setObjId(int objId) {
        staticID.set(Math.max(objId, staticID.get()));
        this.objId = objId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public static AtomicInteger getStaticID() {
        return staticID;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    @Override
    public int compareTo(CanvasObject o) {
        return Integer.compare(this.objId, o.getObjId());
    }

}
