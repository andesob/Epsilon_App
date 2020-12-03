package no.ntnu.epsilon_app.ui.faq;

/**
 * A representation of the faq object that is stored in the database.
 */
public class Faq {

    private long questionId;
    private String question;
    private String answer;

    public Faq(Long id, String question, String answer) {
        this.questionId = id;
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the question id
     *
     * @return - the questionId
     */
    public long getId() {
        return questionId;
    }

    /**
     * Returns the faq question.
     *
     * @return - the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the faq answer.
     *
     * @return - the answer
     */
    public String getAnswer() {
        return answer;
    }
}
