package com.prendus.prendus.manipulators.searchresults;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.activities.searchresults.Data;
import com.prendus.prendus.activities.searchresults.RVAdapter;
import com.prendus.prendus.constants.Constants;
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
    private TextView searchInput;
    private Intent intent;
    public SearchResultsManipulator(RecyclerView recyclerView, TextView searchInput, Intent intent) {
        this.recyclerView = recyclerView;
        this.searchInput = searchInput;
        this.intent = intent;
    }
    @Override
    public void update() {

    }

    @Override
    public void manipulate() {
        String s = intent.getStringExtra(Constants.SEARCH_INPUT);
        searchInput.setText(s);
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
