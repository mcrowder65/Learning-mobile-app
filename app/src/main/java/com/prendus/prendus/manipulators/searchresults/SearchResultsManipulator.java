package com.prendus.prendus.manipulators.searchresults;

import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.activities.searchresults.Data;
import com.prendus.prendus.activities.searchresults.RVAdapter;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.PrendusObject;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 3/25/17.
 */

public class SearchResultsManipulator implements IPrendusManipulator {
    private RecyclerView recyclerView;
    public SearchResultsManipulator(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;

    }
    @Override
    public void update() {

    }

    @Override
    public void manipulate() {

        String path = "quizzes";
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(path);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Data> quizTitles = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Quiz obj = child.getValue(Quiz.class);
                    quizTitles.add(new Data(obj.getTitle()));
                }
                RVAdapter adapter = new RVAdapter(quizTitles);
                recyclerView.setAdapter(adapter);
            }

        });


    }


}
