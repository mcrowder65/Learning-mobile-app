package com.prendus.prendus.questionresult;

import com.google.gson.internal.LinkedTreeMap;
import com.prendus.prendus.objects.question.Question;
import com.prendus.prendus.utilities.Utilities;

/**
 * Created by mcrowder65 on 4/6/17.
 */

public class QuestionResult {
    private Question question;
    private String userAnswer;
    private boolean correct;

    public QuestionResult() {
    }

    public QuestionResult(Question question, String userAnswer, boolean correct) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correct = correct;
    }

    public QuestionResult(LinkedTreeMap linkedTreeMap) {
        this.correct = (boolean) linkedTreeMap.get("correct");
        this.question = new Question((LinkedTreeMap) linkedTreeMap.get("question"));
        this.userAnswer = (String) linkedTreeMap.get("userAnswer");
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String toJson() {
        return Utilities.g.toJson(this);
    }
}
