package no.ntnu.epsilon_app.ui.faq;

public class Faq {
    private long id;
    private String question;
    private String answer;

    public Faq(Long id, String question, String answer) {

        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
