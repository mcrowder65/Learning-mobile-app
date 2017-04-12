package com.prendus.prendus.activities.searchresults;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prendus.prendus.R;
import com.prendus.prendus.activities.takequiz.TakeQuizActivity;
import com.prendus.prendus.objects.quiz.Quiz;
import com.prendus.prendus.objects.quizsession.QuizSession;
import com.prendus.prendus.objects.quizsession.QuizSessionWrapper;
import com.prendus.prendus.utilities.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsRVAdapter extends RecyclerView.Adapter<SearchResultsRVAdapter.SearchResultsContainer> {
    List<SearchResultsData> searchResultsDatas;
    int currentDataIndex = 0;
    SearchResultsActivity searchResultsActivity;

    @Override
    public SearchResultsContainer onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.quiz_card, viewGroup, false);
        SearchResultsContainer qvc = new SearchResultsContainer(v);
        return qvc;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(SearchResultsContainer searchResultsContainer, int i) {
        searchResultsContainer.quizTitle.setText(searchResultsDatas.get(i).getName());
        searchResultsContainer.quizId = searchResultsDatas.get(i).getQuizId();
        searchResultsContainer.score.setText("score: " + searchResultsDatas.get(i).getScore());
        Calendar calendar = searchResultsDatas.get(i).getDate();
        long timeElapsed = new GregorianCalendar().getTimeInMillis() - calendar.getTimeInMillis();
        long days = getNumberOfDays(timeElapsed);
        searchResultsContainer.daysAgoMade.setText(String.valueOf(days) + " days old");
    }

    private long getNumberOfDays(long timeElapsed) {
        long millisecondsInSecond = 1000;
        long millisecondsInMinute = millisecondsInSecond * 60;
        long millisecondsInHour = millisecondsInMinute * 60;
        long millisecondsInDay = millisecondsInHour * 24;
        long days = (long) timeElapsed / (long) millisecondsInDay;
        return days;
    }

    @Override
    public int getItemCount() {
        return searchResultsDatas.size();
    }


    public SearchResultsRVAdapter(List<SearchResultsData> searchResultsDatas, SearchResultsActivity activity) {
        this.searchResultsDatas = searchResultsDatas;
        this.searchResultsActivity = activity;
    }


    public class SearchResultsContainer extends RecyclerView.ViewHolder {
        CardView cv;
        TextView quizTitle;
        TextView score;
        TextView daysAgoMade;
        String quizId;
        ImageView star;
        boolean isFilled = false;

        public void setFilled(boolean filled) {
            isFilled = filled;
        }

        SearchResultsContainer(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            quizTitle = (TextView) itemView.findViewById(R.id.quiz_title);
            star = (ImageView) itemView.findViewById(R.id.star);
            score = (TextView) itemView.findViewById(R.id.score);
            daysAgoMade = (TextView) itemView.findViewById(R.id.daysAgoMade);
            final SearchResultsContainer self = this;
            searchResultsActivity.getManipulator().setStar(searchResultsDatas.get(currentDataIndex++).getQuizId(), star, this);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseAuth auth = FirebaseAuth.getInstance();
                    if (auth.getCurrentUser() == null) {
                        searchResultsActivity.makeSnackBar("You must log in to save quizzes");
                    } else {

                        if (!isFilled) {
                            searchResultsActivity.makeSnackBar("Quiz saved!");
                            isFilled = true;
                            searchResultsActivity.getManipulator().addQuizToMySavedQuizzes(quizId, star, self, searchResultsActivity);
                        } else {
                            searchResultsActivity.makeSnackBar("Quiz not saved now!");
                            isFilled = false;
                            searchResultsActivity.getManipulator().deleteQuizFromMySavedQuizzes(quizId, star, self, searchResultsActivity);
                        }

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
                                    Utilities.goToActivity(TakeQuizActivity.class, searchResultsActivity, quiz);

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

    class Obj {
        private String jwt;
        private String quizId;

        public Obj(String jwt, String quizId) {
            this.jwt = jwt;
            this.quizId = quizId;
        }

        public Obj(String jwt) {
            this.jwt = jwt;
        }

        public Obj() {
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }

        public String getQuizId() {
            return quizId;
        }

        public void setQuizId(String quizId) {
            this.quizId = quizId;
        }

        public String toJson() {
            return Utilities.g.toJson(this);
        }
    }

    class HitServer extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String quizId = strings[0];
            // Create data variable for sent values to server
            URLConnection conn = null;
            BufferedReader reader = null;
            String text = null;
            OutputStreamWriter wr = null;
            // Send data
            try {

                // Defined URL  where to send data
                URL url = new URL("http://192.168.0.108:5000/api/quiz/start-session");
                Task jwtTask = FirebaseAuth.getInstance().getCurrentUser().getToken(false);

                // Send POST data request
                GetTokenResult result = (GetTokenResult) jwtTask.getResult();
                String jwt = result.getToken();


                conn = url.openConnection();
                conn.setDoOutput(true);
                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                Obj sender = new Obj(jwt, quizId);
                String json = sender.toJson();
                Map<String, String> parameters = new HashMap<>();
                parameters.put("jwt", jwt);
                parameters.put("quizId", quizId);
                String params = Utilities.buildParametersForServer(parameters);
                wr.write(params);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                text = sb.toString();
                QuizSessionWrapper wrapper = Utilities.g.fromJson(text, QuizSessionWrapper.class);
                QuizSession session = wrapper.getQuizSession();
                Utilities.log(session.toString());
            } catch (NetworkOnMainThreadException e) {
                Utilities.log(e.toString());
            } catch (MalformedURLException e) {
                Utilities.log(e.toString());
            } catch (IOException e) {
                Utilities.log(e.toString());
            } catch (Exception e) {
                Utilities.log(e.toString());
            } finally {
                try {
                    wr.close();
                } catch (IOException e) {
                    Utilities.log(e.toString());
                }
            }
            return text;
        }

        protected void onPostExecute(String str) {

        }
    }

}