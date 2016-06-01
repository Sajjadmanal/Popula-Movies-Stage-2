package com.example.android.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.R;
import com.example.android.movies.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mPosters;
    private Boolean mState;
    private OnClick mListener;

    //Listener listening clicks
    public interface OnClick{
        void onClicked(int position, View v);
    }

    public FavoriteAdapter(Context context){
        mContext = context;
        mState = Utility.checkNetwork(mContext);
    }

    //Set poster url
    public void setPoster(ArrayList<String> poster){
        mPosters = poster;
        notifyDataSetChanged();
    }

    //Set listener
    public void setOnClickListener(OnClick listener){
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(mState){
            view = LayoutInflater.from(mContext).inflate(R.layout.card_layout, parent, false);
    }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.card_layout_no_net, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Loading url images to image view in cards
        if(mState) {
            Picasso.with(mContext).load(mPosters.get(position)).into(holder.imageView);
        }else {
            holder.textView.setText(mPosters.get(position));
        }
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
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            if(mState) {
                imageView = (ImageView) itemView.findViewById(R.id.card_image);
            }else {
                imageView = (ImageView) itemView.findViewById(R.id.card_image_no_net);
                textView = (TextView) itemView.findViewById(R.id.title_no_net);
            }
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClicked(getPosition(), v);
        }
    }
}
