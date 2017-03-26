package com.prendus.prendus.objects.quizsession;

/**
 * Created by matt on 3/25/17.
 * Wraps the quiz sessions into a data structure
 */
public class QuizSessionWrapper {

    private QuizSession quizSession;

    public QuizSession getQuizSession() {
        return quizSession;
    }

    public void setQuizSession(QuizSession quizSession) {
        this.quizSession = quizSession;
    }

    @Override
    public String toString() {
        return "QuizSessionWrapper{" +
                "quizSession=" + quizSession +
                '}';
    }

    public QuizSessionWrapper(QuizSession quizSession) {

        this.quizSession = quizSession;
    }
}
