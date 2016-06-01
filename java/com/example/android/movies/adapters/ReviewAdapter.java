package com.example.android.movies.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.movies.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mReviews;

    public ReviewAdapter(Context context){
        mContext = context;
    }

    //Set poster url
    public void setReviews(ArrayList<String> reviews){
        mReviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.review_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Loading reviews
        holder.textView.setText(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        if(mReviews!=null) {
            return mReviews.size();
        }
        return 0;
    }

    //View holder for adapter
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.review_text);
        }
    }
}

