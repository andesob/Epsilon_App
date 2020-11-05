package no.ntnu.epsilon_app.ui.faq;

public class Faq {
    private long questionId;
    private String question;
    private String answer;

    public Faq(Long id, String question, String answer) {

        this.questionId = id;
        this.question = question;
        this.answer = answer;
    }

    public long getId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
