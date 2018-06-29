package gr.redefine;

import java.io.Serializable;
import java.util.Date;

public class Message  implements Serializable {
    private String message;
    private Long timestamp;
    private String user;
    private String type;

    public Message(String text, String user, String type) {
        this.setMessage(text);
        this.setUser(user);
        this.setTimestamp(new Date().getTime());
        this.setType(type);
    }

    public Message(String text, String user) {
        this(text,user, "general");
    }

    public Message(){ }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
