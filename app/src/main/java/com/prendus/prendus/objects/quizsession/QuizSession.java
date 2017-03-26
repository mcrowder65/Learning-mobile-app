package com.prendus.prendus.objects.quizsession;

import com.prendus.prendus.objects.PrendusObject;
import com.prendus.prendus.objects.quiz.Quiz;

import java.util.Map;

/**
 * Created by matt on 3/26/17.
 */

public class QuizSession extends PrendusObject {


    private Quiz quiz;
    private String quizId;
    private long start;
    private long end;
    private Map<String, QuestionSession> questionSessions;

    public QuizSession() {
    }

    public QuizSession(long timestamp, Quiz quiz, String quizId, long start, long end, Map<String, QuestionSession> questionSessions) {
        super(timestamp);
        this.quiz = quiz;
        this.quizId = quizId;
        this.start = start;
        this.end = end;
        this.questionSessions = questionSessions;
    }

    public QuizSession(Quiz quiz, String quizId, long start, long end, Map<String, QuestionSession> questionSessions) {
        this.quiz = quiz;
        this.quizId = quizId;
        this.start = start;
        this.end = end;
        this.questionSessions = questionSessions;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public Map<String, QuestionSession> getQuestionSessions() {
        return questionSessions;
    }

    public void setQuestionSessions(Map<String, QuestionSession> questionSessions) {
        this.questionSessions = questionSessions;
    }

    @Override
    public String toString() {
        return "QuizSession{" +
                "quiz=" + quiz +
                ", quizId='" + quizId + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", questionSessions=" + questionSessions +
                '}';
    }

}
