package com.prendus.prendus.manipulators.myquizzes;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesActivity;
import com.prendus.prendus.activities.myquizzes.MyQuizzesData;
import com.prendus.prendus.activities.myquizzes.MyQuizzesRVAdapter;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mcrowder65 on 4/2/17.
 */

public class MyQuizzesManipulator implements IPrendusManipulator {
    private RecyclerView recyclerView;
    private Intent intent;
    private MyQuizzesActivity myQuizzesActivity;
    private MyQuizzesManipulator self;


    @Override
    public void manipulate() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            makeSnackBar("How the heck did you get here?");
            return;
        }
        String uid = auth.getCurrentUser().getUid();
        String path = "users/" + uid + "/starredQuizzes";
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> quizIds = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String quizId = child.getKey();
                    quizIds.add(quizId);
                }
                String path = "quizzes";
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(path);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            List<MyQuizzesData> quizzes = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {

                                if (quizIds.indexOf(child.getKey()) != -1) {
                                    Quiz quiz = child.getValue(Quiz.class);
                                    quiz.setId(child.getKey());
                                    quizzes.add(new MyQuizzesData(quiz.getTitle(), quiz.getId(), quiz.getScore(), quiz.getTimestamp()));
                                }

                            }
                            MyQuizzesRVAdapter adapter = new MyQuizzesRVAdapter(quizzes, myQuizzesActivity);
                            recyclerView.setAdapter(adapter);
                            if (quizzes.size() == 0) {
                                myQuizzesActivity.noQuizzesYetText.setText("You don't have any quizzes saved yet!");
                            } else {
                                myQuizzesActivity.noQuizzesYetText.setText("");
                            }
                        } catch (Exception e) {
                            Utilities.log(e);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });
    }

    private void makeSnackBar(String message) {
        myQuizzesActivity.makeSnackBar(message);
    }

    public MyQuizzesManipulator(RecyclerView recyclerView, Intent intent, MyQuizzesActivity activity) {
        this.recyclerView = recyclerView;
        this.intent = intent;
        this.myQuizzesActivity = activity;
        self = this;
    }


    public PrendusActivity getActivity() {
        return myQuizzesActivity;
    }


    public void deleteQuizFromMySavedQuizzes(final String quizId, final ImageView star, MyQuizzesRVAdapter.MyQuizzesContainer container) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            star.setImageResource(R.drawable.star_border);
            makeSnackBar("IDK how you got here because how was it saved in the first place?");
            return;
        }
        String path = "users/" + auth.getCurrentUser().getUid() + "/starredQuizzes/" + quizId;
        Utilities.firebase.delete(path);
        this.manipulate();
    }

}
