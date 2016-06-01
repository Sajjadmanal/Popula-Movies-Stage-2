package com.example.android.movies.asyncTasks;


import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.movies.Utility;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ReviewTask extends AsyncTask<String,Void,String> {
    private ReviewCompleted mListener;

    //Interface Listener
    public interface ReviewCompleted{
        void onReviewCompleted(ArrayList<String> reviews);
    }

    public ReviewTask(ReviewCompleted listener){
        mListener = listener;
    }

    //Get review url from id
    private String getReviewUrl(String id){
        return Uri.parse(Utility.TRAILER_URL).buildUpon()
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter("api_key", "b6f6fcfbb225d8c500e4404655ccadcc")
                .build().toString();
    }

    @Override
    protected String doInBackground(String... id) {
        return HttpRequest.get(getReviewUrl(id[0])).body();
    }

    @Override
    protected void onPostExecute(String jsonStr) {
        super.onPostExecute(jsonStr);
        //Storing movie keys
        ArrayList<String> reviews = new ArrayList<>();
        //Getting json data
        try{
            JSONObject object = new JSONObject(jsonStr);
            JSONArray array = object.getJSONArray("results");
            for(int i=0;i<array.length();i++){
                JSONObject object1 = array.getJSONObject(i);
                reviews.add(object1.getString("content"));
            }
            //Building trailer url with keys
        }catch (JSONException e){
            e.printStackTrace();
        }finally {
            mListener.onReviewCompleted(reviews);
        }
    }
}
