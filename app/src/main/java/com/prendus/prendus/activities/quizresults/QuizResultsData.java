package com.prendus.prendus.activities.quizresults;

/**
 * Created by mcrowder65 on 4/9/17.
 */

public class QuizResultsData {
    private String question;
    private String yourAnswer;
    private String correctAnswer;
    private String iconSrc;

    public QuizResultsData(String question, String yourAnswer, String correctAnswer, String iconSrc) {
        this.question = question;
        this.yourAnswer = yourAnswer;
        this.correctAnswer = correctAnswer;
        this.iconSrc = iconSrc;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getIconSrc() {
        return iconSrc;
    }

    public void setIconSrc(String iconSrc) {
        this.iconSrc = iconSrc;
    }
}
