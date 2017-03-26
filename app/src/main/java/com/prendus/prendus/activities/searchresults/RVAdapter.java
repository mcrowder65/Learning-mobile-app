package com.prendus.prendus.activities.searchresults;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.prendus.prendus.R;
import com.prendus.prendus.objects.quizsession.QuizSessionWrapper;
import com.prendus.prendus.utilities.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.QuizResultContainer> {

    @Override
    public QuizResultContainer onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_search_results, viewGroup, false);
        QuizResultContainer qvc = new QuizResultContainer(v);
        return qvc;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(QuizResultContainer personViewHolder, int i) {
        personViewHolder.personName.setText(datas.get(i).getName());
        personViewHolder.quizId = datas.get(i).getQuizId();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    List<Data> datas;

    public RVAdapter(List<Data> persons) {
        this.datas = persons;
    }


    public class QuizResultContainer extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        String quizId;

        QuizResultContainer(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO go to quiz!
                    new HitServer().execute(quizId);
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
                QuizSessionWrapper session = Utilities.g.fromJson(text, QuizSessionWrapper.class);
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