package no.ntnu.epsilon_app.data;

public class LoggedInUser {

    private long userId;
    private String displayName;

    public LoggedInUser(long userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public long getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
