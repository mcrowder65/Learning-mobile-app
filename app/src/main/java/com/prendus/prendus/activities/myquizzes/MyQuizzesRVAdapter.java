package com.prendus.prendus.activities.myquizzes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.util.List;

public class MyQuizzesRVAdapter extends RecyclerView.Adapter<MyQuizzesRVAdapter.QuizResultContainer> {

    @Override
    public QuizResultContainer onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_my_quizzes, viewGroup, false);
        QuizResultContainer qvc = new QuizResultContainer(v);
        return qvc;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(QuizResultContainer personViewHolder, int i) {
        personViewHolder.quizTitle.setText(myQuizzesDatas.get(i).getName());
        personViewHolder.quizId = myQuizzesDatas.get(i).getQuizId();
    }

    @Override
    public int getItemCount() {
        return myQuizzesDatas.size();
    }

    List<MyQuizzesData> myQuizzesDatas;
    int currentDataIndex = 0;
    MyQuizzesActivity myQuizzesActivity;

    public MyQuizzesRVAdapter(List<MyQuizzesData> quizzes, MyQuizzesActivity activity) {
        this.myQuizzesDatas = quizzes;
        this.myQuizzesActivity = activity;
    }


    public class QuizResultContainer extends RecyclerView.ViewHolder {
        CardView cv;
        TextView quizTitle;
        String quizId;
        ImageView star;


        QuizResultContainer(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            quizTitle = (TextView) itemView.findViewById(R.id.quiz_title);
            star = (ImageView) itemView.findViewById(R.id.star);
            final QuizResultContainer self = this;
            star.setImageResource(R.drawable.star_filled);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() == null) {
                        myQuizzesActivity.makeSnackBar("how did you get here?!?!?!?!");
                    } else {
                        myQuizzesActivity.makeSnackBar("Quiz removed!");
                        myQuizzesActivity.getManipulator().deleteQuizFromMySavedQuizzes(quizId, star, self);
                    }

                }


            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String path = "quizzes/" + quizId;
                    try {
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference(path);

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onCancelled(DatabaseError arg0) {

                            }

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    Quiz quiz = dataSnapshot.getValue(Quiz.class);
                                    quiz.setId(dataSnapshot.getKey());
                                    Utilities.goToActivity(TakeQuizActivity.class, myQuizzesActivity, quiz);

                                } catch (Exception e) {
                                    Utilities.log(e);
                                }

                            }

                        });
                    } catch (Exception e) {
                        Utilities.log(e);
                    }

                }
            });

        }


    }


}