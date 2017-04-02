package com.prendus.prendus.activities.searchresults;

/**
 * Created by mcrowder65 on 3/25/17.
 */

public class SearchResultsData {
    private String name;
    private String quizId;
    public SearchResultsData(String name, String quizId) {
        this.name = name;
        this.quizId = quizId;
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

