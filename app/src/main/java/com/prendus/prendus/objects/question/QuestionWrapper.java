package com.prendus.prendus.objects.question;

/**
 * Created by mcrowder65 on 3/27/17.
 */

public class QuestionWrapper {
    private Question question;
    public QuestionWrapper() {}
    public QuestionWrapper(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
