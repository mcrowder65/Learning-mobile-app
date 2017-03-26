package com.prendus.prendus.objects.quizsession;

import java.util.Map;

/**
 * Created by matt on 3/26/17.
 */

public class QuestionSession {
    private Map<String, String> questionInfo;
    private String answer;
    private boolean correct;
    private int correctNumberOfAttempts;
    private String questionId;

    public QuestionSession(Map<String, String> questionInfo, String answer, boolean correct, int correctNumberOfAttempts, String questionId) {
        this.questionInfo = questionInfo;
        this.answer = answer;
        this.correct = correct;
        this.correctNumberOfAttempts = correctNumberOfAttempts;
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuestionSession{" +
                "questionInfo=" + questionInfo +
                ", answer='" + answer + '\'' +
                ", correct=" + correct +
                ", correctNumberOfAttempts=" + correctNumberOfAttempts +
                ", questionId='" + questionId + '\'' +
                '}';
    }

    public Map<String, String> getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(Map<String, String> questionInfo) {
        this.questionInfo = questionInfo;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public int getCorrectNumberOfAttempts() {
        return correctNumberOfAttempts;
    }

    public void setCorrectNumberOfAttempts(int correctNumberOfAttempts) {
        this.correctNumberOfAttempts = correctNumberOfAttempts;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
