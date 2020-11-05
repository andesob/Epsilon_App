package no.ntnu.epsilon_app.data;

public class User {
    long userid;
    String firstName;
    String lastName;
    String email;

    public User(long userid, String firstName, String lastName, String email) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
