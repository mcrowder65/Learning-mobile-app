package com.prendus.prendus.manipulators.searchresults;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.PrendusActivity;
import com.prendus.prendus.activities.searchresults.SearchResultsData;
import com.prendus.prendus.activities.searchresults.SearchResultsRVAdapter;
import com.prendus.prendus.activities.searchresults.SearchResultsActivity;
import com.prendus.prendus.constants.IntentExtras;
import com.prendus.prendus.manipulators.IPrendusManipulator;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by matt on 3/25/17.
 */

public class SearchResultsManipulator implements IPrendusManipulator {
    private RecyclerView recyclerView;
    private TextView searchInput;
    private Intent intent;
    private SearchResultsActivity searchResultsActivity;


    public SearchResultsManipulator(RecyclerView recyclerView, TextView searchInput, Intent intent, SearchResultsActivity activity) {
        this.recyclerView = recyclerView;
        this.searchInput = searchInput;
        this.intent = intent;
        this.searchResultsActivity = activity;
    }

    private void makeSnackBar(String message) {
        searchResultsActivity.makeSnackBar(message);
    }


    @Override
    public void manipulate() {
        String s = intent.getStringExtra(IntentExtras.SEARCH_INPUT);
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
                List<SearchResultsData> quizzes = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Quiz quiz = child.getValue(Quiz.class);
                    if (quiz.getQuestions() != null) {
                        quiz.setId(child.getKey());
                        String lowerCaseSearch = Utilities.stripEverything(String.valueOf(searchInput.getText()));

                        String lowerCaseTitle = Utilities.stripEverything(quiz.getTitle());
                        if (lowerCaseTitle.contains(lowerCaseSearch)) {
                            quizzes.add(new SearchResultsData(quiz.getTitle(), quiz.getId(), quiz.getScore(), quiz.getTimestamp()));
                        }

                    }

                }
                SearchResultsRVAdapter adapter = new SearchResultsRVAdapter(quizzes, searchResultsActivity);
                recyclerView.setAdapter(adapter);
                if (quizzes.size() == 0) {
                    searchResultsActivity.noQuizzesYetText.setText("No quizzes match this search");
                } else {
                    searchResultsActivity.noQuizzesYetText.setText("");
                }
            }

        });


    }


    public PrendusActivity getActivity() {
        return searchResultsActivity;
    }

    /**
     * This is called when a star is being initialized and decides whether or not to star it!
     *
     * @param quizId
     */

    public void setStar(final String quizId, final ImageView star, final SearchResultsRVAdapter.SearchResultsContainer container) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            star.setImageResource(R.drawable.star_border);
            container.setFilled(false);
            return;
        }
        String path = "users/" + auth.getCurrentUser().getUid() + "/starredQuizzes";

        DatabaseReference ref = database.getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onCancelled(DatabaseError arg0) {

            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> quizIds = new HashMap<>();
                star.setImageResource(R.drawable.star_border);
                container.setFilled(false);
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    if (key.equals(quizId)) {
                        star.setImageResource(R.drawable.star_filled);
                        container.setFilled(true);
                    }
                }
            }

        });
    }

    public void addQuizToMySavedQuizzes(final String quizId, final ImageView star, final SearchResultsRVAdapter.SearchResultsContainer container, PrendusActivity activity) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            star.setImageResource(R.drawable.star_border);
            makeSnackBar("You can't save a quiz since you're not logged in");
            return;
        }
        String path = "users/" + auth.getCurrentUser().getUid() + "/starredQuizzes/" + quizId;
        Utilities.firebase.update(path, quizId);
        setStar(quizId, star, container);
    }

    public void deleteQuizFromMySavedQuizzes(final String quizId, final ImageView star, SearchResultsRVAdapter.SearchResultsContainer container, PrendusActivity activity) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            star.setImageResource(R.drawable.star_border);
            makeSnackBar("IDK how you got here because how was it saved in the first place?");
            return;
        }
        String path = "users/" + auth.getCurrentUser().getUid() + "/starredQuizzes/" + quizId;
        Utilities.firebase.delete(path);
        setStar(quizId, star, container);
    }


}
