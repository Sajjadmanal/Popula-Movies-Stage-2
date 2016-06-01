package com.example.android.movies.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mPosters;
    private OnTrailerClick mListener;

    //Listener listening clicks
    public interface OnTrailerClick{
        void onTrailerClicked(int position, View v);
    }

    public TrailerAdapter(Context context){
        mContext = context;
    }

    //Set poster url
    public void setPoster(ArrayList<String> poster){
        mPosters = poster;
        notifyDataSetChanged();
    }

    //Set listener
    public void setOnClickListener(OnTrailerClick listener){
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(mContext).load(mPosters.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(mPosters!=null) {
            return mPosters.size();
        }
        return 0;
    }

    //View holder for adapter
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.card_image);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onTrailerClicked(getPosition(), v);
        }
    }
}