package com.prendus.prendus.objects.quiz;

import com.prendus.prendus.objects.PrendusObject;

import java.util.Map;

/**
 * Created by matt on 3/25/17.
 */

public class Quiz extends PrendusObject {
    private String title;
    private String uid;
    private String visibility;
    private Map<String, Boolean> quizQuestionSettings;
    private Map<String, Map<String, Map<String, Boolean>>> questions;
    public Quiz(){}
    public Quiz(String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions) {
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Map<String, Boolean> getQuizQuestionSettings() {
        return quizQuestionSettings;
    }

    public boolean getQuizQuestionSetting(String id) {
        return getQuizQuestionSettings().get(id);
    }
    public void setQuizQuestionSettings(Map<String, Boolean> quizQuestionSettings) {
        this.quizQuestionSettings = quizQuestionSettings;
    }

    public Map<String, Map<String, Map<String, Boolean>>> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<String, Map<String, Map<String, Boolean>>> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return toJson();
    }
}
