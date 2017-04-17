package com.prendus.prendus.activities.quizresults;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prendus.prendus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 4/9/17.
 */

public class QuizResultsRVAdapter extends RecyclerView.Adapter<QuizResultsRVAdapter.QuizResultsContainer> {
    List<QuizResultsData> data;
    int currentDataIndex = 0;
    QuizResultsActivity quizResultsActivity;

    @Override
    public QuizResultsContainer onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_result_card, viewGroup, false);
        QuizResultsContainer quizResultsContainer = new QuizResultsContainer(v);
        return quizResultsContainer;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(QuizResultsContainer c, int i) {
        QuizResultsData data = this.data.get(i);
        c.setQuestion(data.getQuestion());
        c.setYourAnswer(data.getYourAnswer());
        c.setCorrectAnswer(data.getCorrectAnswer());
        c.setIcon(data.isCorrect());
    }


    @Override
    public int getItemCount() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data.size();
    }

    public QuizResultsRVAdapter(List<QuizResultsData> data, QuizResultsActivity quizResultsActivity) {
        this.data = data;
        this.quizResultsActivity = quizResultsActivity;
    }

    public class QuizResultsContainer extends RecyclerView.ViewHolder {
        private CardView cv;
        private ImageView icon;
        private TextView question;
        private TextView yourAnswer;
        private TextView correctAnswer;

        public QuizResultsContainer(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            question = (TextView) itemView.findViewById(R.id.questionText);
            yourAnswer = (TextView) itemView.findViewById(R.id.yourAnswer);
            correctAnswer = (TextView) itemView.findViewById(R.id.correctAnswer);


        }

        public void setYourAnswer(String s) {

            yourAnswer.setText((s.equals("") ? " you put nothing!" : s));
        }

        public void setCorrectAnswer(String s) {
            correctAnswer.setText(s);
        }

        public void setQuestion(String s) {
            question.setText(s);
        }


        public void setIcon(boolean correct) {
            icon.setImageResource(correct ? R.drawable.correct : R.drawable.incorrect);
        }
    }
}
