package no.ntnu.epsilon_app.data;

import java.util.List;

public class LoggedInUser {

    private long userId;
    private String displayName;
    private List<String> groups;

    public LoggedInUser(long userId, String displayName, List<String> groups) {
        this.userId = userId;
        this.displayName = displayName;
        this.groups = groups;
    }

    public long getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getGroups(){
        return groups;
    }
}
