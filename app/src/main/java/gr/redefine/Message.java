package gr.redefine;

import java.io.Serializable;
import java.util.Date;

import gr.redefine.extras.Location;

public class Message  implements Serializable {
    public enum TYPES {GENERAL, PUBLIC, ORG}
    private String message;
    private Long timestamp;
    private String user;
    private TYPES type;
    private Location location;

    public Message(String text, String user, TYPES type, Location location) {
        this.setMessage(text);
        this.setUser(user);
        this.setTimestamp(new Date().getTime());
        this.setType(type);
        this.setLocation(location);
    }

    public Message(String text, String user, Location location) {
        this(text,user, TYPES.GENERAL, location);
    }

    public Message(){ }

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
