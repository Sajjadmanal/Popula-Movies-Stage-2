package com.example.android.movies;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.adapters.FavoriteAdapter;
import com.example.android.movies.data.Movie;
import com.example.android.movies.data.MovieContract;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,FavoriteAdapter.OnClick {
    private ArrayList<Movie> movies = new ArrayList<>();
    private static final int LOADER_ID = 0;
    private Boolean mState;
    private FavoriteClicked mListener;
    private FavoriteAdapter mFavoriteAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public interface FavoriteClicked{
        void onFavClicked(Movie movie);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener = (FavoriteClicked)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        //Initialising adapter
        mFavoriteAdapter = new FavoriteAdapter(getContext());
        mFavoriteAdapter.setOnClickListener(this);
        RecyclerView favoriteView = (RecyclerView) view.findViewById(R.id.favorite_view);
        favoriteView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        favoriteView.setAdapter(mFavoriteAdapter);
        //Starting loader
        getLoaderManager().restartLoader(LOADER_ID, null, this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mState = Utility.checkNetwork(getContext());
    }

    //Get fragment instance
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MovieContract.FavoriteMovies.CONTENT_URI, null,
                MovieContract.FavoriteMovies.COLUMN_FAVORITES + "=?", new String[]{Integer.toString(1)}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> posters = new ArrayList<>();
      while (data.moveToNext()){
          if(mState) {
              posters.add(data.getString(3));
          }else {
              posters.add(data.getString(2));
          }
      }
        mFavoriteAdapter.setPoster(posters);
        //Get data from cursor
        getDataFromCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    //Getting data from cursor
    private void getDataFromCursor(Cursor cursor) {
        movies.clear();
        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // do what you need with the cursor here
                Movie movie = new Movie();
                movie.setId(cursor.getString(1));
                movie.setTitle(cursor.getString(2));
                movie.setPoster(cursor.getString(3));
                movie.setBack_poster(cursor.getString(4));
                movie.setOverview(cursor.getString(5));
                movie.setDate(cursor.getString(6));
                movie.setVote_avg(cursor.getDouble(7));
                movie.setTotal_votes(cursor.getInt(8));
                movies.add(movie);
            }
        }
    }

    @Override
    public void onClicked(int position, View v) {
        //Passing control to main activity
        mListener.onFavClicked(movies.get(position));
    }
}
