package com.prendus.prendus.activities.myquizzes;

/**
 * Created by mcrowder65 on 4/2/17.
 */

public class MyQuizzesData {
    private String name;
    private String quizId;

    public MyQuizzesData(String name, String quizId) {
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
