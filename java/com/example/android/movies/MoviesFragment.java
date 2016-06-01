package com.example.android.movies;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.movies.adapters.RecyclerAdapter;
import com.example.android.movies.asyncTasks.PosterTask;
import com.example.android.movies.data.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements PosterTask.GetPoster,RecyclerAdapter.OnClick{
    private RecyclerAdapter mRecyclerAdapter;
    private ProgressDialog mProgressBar;
    private String mUri;
    private MovieClicked mListener;
    private ArrayList<Movie> movies = new ArrayList<>();

    public MoviesFragment() {
        // Required empty public constructor
    }

    //Called when movie clicked
    public interface MovieClicked{
        void onMovieClicked(Movie movie);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        //Getting uri
        mUri = getArguments().getString("fragment");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //Setting layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(inflater.getContext(), 2));
        //Setting adapter
        mRecyclerAdapter = new RecyclerAdapter(getActivity());
        //Setting listener to cards
        mRecyclerAdapter.setOnClickListener(this);
        recyclerView.setAdapter(mRecyclerAdapter);
        updateData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener = (MovieClicked)getActivity();
    }

    private void updateData(){
        if(Utility.checkNetwork(getContext())) {
            //Show progress bar
            showProgressBar();
            //Starting asyncTask
            new PosterTask(this).execute(mUri);
        }else {
            //Handling no network condition
            Toast.makeText(getContext(), "No network available", Toast.LENGTH_SHORT).show();
        }
    }

    public static MoviesFragment newInstance(String uri){
        MoviesFragment fragment = new MoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fragment", uri);
        fragment.setArguments(bundle);
        return fragment;
    }

    //Called after getting posters url's
    @Override
    public void onPosterCompleted(ArrayList<Movie> movies) {
        //Removing progress bar
        mProgressBar.hide();
        ArrayList<String> poster = new ArrayList<>();
        for(int i=0;i<movies.size();i++){
            poster.add(movies.get(i).getPoster());
        }
        //Passing data to adapter
        mRecyclerAdapter.setPoster(poster);
        this.movies = movies;
    }

    //Showing progress bar
    private void showProgressBar(){
        mProgressBar = new ProgressDialog(getContext());
        mProgressBar.setMessage("Fetching data...");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
    }

    @Override
    public void onClicked(int position, View v) {
      //passing control to main activity
        mListener.onMovieClicked(movies.get(position));
    }
}
