package com.prendus.prendus.activities.searchresults;

import java.util.Date;

/**
 * Created by mcrowder65 on 3/25/17.
 */

public class SearchResultsData {
    private String name;
    private String quizId;
    private int score;
    private Date date;

    public SearchResultsData(String name, String quizId, int score, long timestamp) {
        this.name = name;
        this.quizId = quizId;
        this.score = score;
        date = new Date(timestamp);
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

