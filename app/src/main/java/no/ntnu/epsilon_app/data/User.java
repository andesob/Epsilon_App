package no.ntnu.epsilon_app.data;

import java.util.ArrayList;
import java.util.List;

public class User {
    private long userid;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> groups;

    public User(long userid, String firstName, String lastName, String email, ArrayList<String> groups) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.groups = groups;
    }

    public long getUserid() {
        return userid;
    }

    public String getFirstName(){
        return firstName;
    }

    public List<String> getGroups() {
        return groups;
    }

    public String getEmail() {
        return email;
    }
}
