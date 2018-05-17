package edu.eci.arsw.ecimocker.entities;

/**
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class User {

    private String userName;
    private int userId;
    private String color;

    public User() {
        this(0, "");
    }

    public User(int id) {
        this(id, "");
    }

    public User(int id, String name) {
        this(id, name, "");
    }
    
    public User(int id, String name, String color) {
        this.userId = id;
        this.userName = name;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", userId=" + userId + ", color=" + color + ", classId=" + super.toString() + '}';
    }

}
