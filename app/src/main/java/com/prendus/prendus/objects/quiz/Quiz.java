package com.prendus.prendus.objects.quiz;

import com.prendus.prendus.objects.PrendusObject;

import java.util.Map;

/**
 * Created by matt on 3/25/17.
 */

public class Quiz extends PrendusObject {
    private String title;
    private String uid;
    //TODO figure out how to get this as an interface or enum of sorts
    private String visibility;
    private Map<String, Boolean> quizQuestionSettings;
    private Map<String, Map<String, Map<String, Boolean>>> questions;
    private int score;

    public Quiz() {
    }

    public Quiz(String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions, int score) {
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
        this.score = score;
    }

    public Quiz(long timestamp, String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions, int score) {
        super(timestamp);
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
        this.score = score;
    }

    public Quiz(long timestamp, String id, String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions, int score) {
        super(timestamp, id);
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
        this.score = score;
    }

    public Quiz(String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions) {
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
    }

    public Quiz(long timestamp, String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions) {
        super(timestamp);
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
    }

    public Quiz(long timestamp, String id, String title, String uid, String visibility, Map<String, Boolean> quizQuestionSettings, Map<String, Map<String, Map<String, Boolean>>> questions) {
        super(timestamp, id);
        this.title = title;
        this.uid = uid;
        this.visibility = visibility;
        this.quizQuestionSettings = quizQuestionSettings;
        this.questions = questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public String[] getQuestionIds() {
        return questions.keySet().toArray(new String[questions.keySet().size()]);
    }

    public void upvote() {
        score++;
    }

    public void downvote() {
        score--;
    }
}

