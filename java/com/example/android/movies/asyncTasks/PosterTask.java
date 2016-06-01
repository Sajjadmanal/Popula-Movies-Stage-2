package com.example.android.movies.asyncTasks;


import android.os.AsyncTask;

import com.example.android.movies.Utility;
import com.example.android.movies.data.Movie;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PosterTask extends AsyncTask<String,Void,String> {
    private GetPoster mListener;

    public PosterTask(GetPoster listener){
      mListener = listener;
    }

    //Listener to pass data to fragment
    public interface GetPoster{
        void onPosterCompleted(ArrayList<Movie> poster);
    }

    @Override
    protected String doInBackground(String... uri) {
        return HttpRequest.get(uri[0]).body();
    }

    @Override
    protected void onPostExecute(String json) {
        super.onPostExecute(json);
        ArrayList<Movie> movies = new ArrayList<>();
        //Getting json data
        try {
            JSONObject object = new JSONObject(json);
            JSONArray jsonArray = object.getJSONArray("results");
            for (int i=0; i<jsonArray.length(); i++){
                Movie movie = new Movie();
                JSONObject object1 = jsonArray.getJSONObject(i);
                movie.setId(object1.getString("id"));
                movie.setTitle(object1.getString("original_title"));
                movie.setPoster(Utility.IMAGE_CONSTANT + object1.getString("poster_path"));
                movie.setBack_poster(Utility.IMAGE_CONSTANT + object1.getString("backdrop_path"));
                movie.setOverview(object1.getString("overview"));
                movie.setDate(object1.getString("release_date"));
                movie.setVote_avg(object1.getDouble("vote_average"));
                movie.setTotal_votes(object1.getInt("vote_count"));
                movies.add(movie);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            mListener.onPosterCompleted(movies);
        }
    }
}
