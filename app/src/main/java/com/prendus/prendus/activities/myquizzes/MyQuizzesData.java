package com.prendus.prendus.activities.myquizzes;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mcrowder65 on 4/2/17.
 */

public class MyQuizzesData {
    private String name;
    private String quizId;
    private int score;
    private Calendar date;

    public MyQuizzesData(String name, String quizId, int score, long timestamp) {
        this.name = name;
        this.quizId = quizId;
        this.score = score;
        date = new GregorianCalendar();
        date.setTimeInMillis(timestamp);
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
