package no.ntnu.epsilon_app.ui.about_us;

public class AboutUsObject {
    private long id;
    private long userid;
    private String name;
    private String position;
    private String email;

    public AboutUsObject(long id, String name, String position, String email, long userid) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.email = email;
        this.userid = userid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public long getUserid() {
        return userid;
    }
}
