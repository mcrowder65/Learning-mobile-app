package com.prendus.prendus.objects.quizsession;

import java.util.Map;

/**
 * Created by matt on 3/25/17.
 * Wraps the quiz sessions into a data structure
 */
public class QuizSessionDataStructure {
    @Override
    public String toString() {
        return "QuizSessionDataStructure{" +
                "quizSessions=" + quizSessions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuizSessionDataStructure that = (QuizSessionDataStructure) o;

        return quizSessions != null ? quizSessions.equals(that.quizSessions) : that.quizSessions == null;

    }

    @Override
    public int hashCode() {
        return quizSessions != null ? quizSessions.hashCode() : 0;
    }

    public Map<String, Map<String, QuizSession>> getQuizSessions() {
        return quizSessions;
    }

    public void setQuizSessions(Map<String, Map<String, QuizSession>> quizSessions) {
        this.quizSessions = quizSessions;
    }

    private Map<String, Map<String, QuizSession>> quizSessions;

}
