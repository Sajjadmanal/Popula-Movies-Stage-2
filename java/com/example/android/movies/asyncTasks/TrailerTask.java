package com.example.android.movies.asyncTasks;


import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.movies.Utility;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrailerTask extends AsyncTask<String,Void,String> {
    private TrailerCompleted mListener;

    //interface listener
    public interface TrailerCompleted{
        void onTrailerCompleted(ArrayList<String> trailers, ArrayList<String> trailer_posters);
    }

    //Returns video url
    private Uri getTrailerUrl(String id){
        return Uri.parse(Utility.TRAILER_URL).buildUpon()
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter("api_key", "b6f6fcfbb225d8c500e4404655ccadcc")
                .build();
    }

    //Returns youtube urls
    private ArrayList<String> getYoutubeUrl(ArrayList<String> keys){
        ArrayList<String> trailers = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++){
            Uri uri = Uri.parse(Utility.YOUTUBE_URL).buildUpon()
                    .appendQueryParameter("v", keys.get(i))
                    .build();
            trailers.add(uri.toString());
        }
        return trailers;
    }

    //Returns trailer poster path
    private ArrayList<String> getTrailerPoster(ArrayList<String> keys){
        ArrayList<String> trailer_posters = new ArrayList<>();
        for(int i=0;i<keys.size();i++){
            trailer_posters.add(Utility.TRAILER_POSTER_URL + keys.get(i) + "/0.jpg");
        }
        return trailer_posters;
    }

    public TrailerTask(TrailerCompleted listener){
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... id) {
        //Getting url and json string
        return HttpRequest.get(getTrailerUrl(id[0]).toString()).body();
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);
        //Storing movie keys
        ArrayList<String> keys = new ArrayList<>();
        //Getting json data
        try{
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.getJSONArray("results");
            for(int i=0;i<array.length();i++){
                JSONObject object1 = array.getJSONObject(i);
                keys.add(object1.getString("key"));
            }
            //Building trailer url with keys
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            mListener.onTrailerCompleted(getYoutubeUrl(keys), getTrailerPoster(keys));
        }
    }
}
