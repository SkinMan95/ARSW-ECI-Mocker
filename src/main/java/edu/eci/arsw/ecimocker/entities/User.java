package edu.eci.arsw.ecimocker.entities;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class User {

    private String userName;
    private int userId;

    public User() {
        this(0, "");
    }

    public User(int id) {
        this(id, "");
    }

    public User(int id, String name) {
        this.userId = id;
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
