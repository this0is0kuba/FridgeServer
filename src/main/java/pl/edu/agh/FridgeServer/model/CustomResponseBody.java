package pl.edu.agh.FridgeServer.model;

import java.util.Date;

public class CustomResponseBody {

    private int userId;
    private Date sessionStart;
    private Date sessionEnd;
    private String username;

    public CustomResponseBody(int userId, Date sessionStart, Date sessionEnd, String username) {
        this.userId = userId;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(Date sessionStart) {
        this.sessionStart = sessionStart;
    }

    public Date getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(Date sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CustomResponseBody{" +
                "userId=" + userId +
                ", sessionStart=" + sessionStart +
                ", sessionEnd=" + sessionEnd +
                ", username='" + username + '\'' +
                '}';
    }
}
