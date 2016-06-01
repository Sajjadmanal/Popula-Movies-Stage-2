package com.example.android.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.adapters.ReviewAdapter;
import com.example.android.movies.adapters.TrailerAdapter;
import com.example.android.movies.asyncTasks.ReviewTask;
import com.example.android.movies.asyncTasks.TrailerTask;
import com.example.android.movies.data.Movie;
import com.example.android.movies.data.MovieContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements TrailerTask.TrailerCompleted,TrailerAdapter.OnTrailerClick,ReviewTask.ReviewCompleted,View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    @Bind(R.id.movie_title)TextView titleView;
    @Bind(R.id.movie_date)TextView dateView;
    @Bind(R.id.movie_image)ImageView imageView;
    @Bind(R.id.movie_vote_avg)TextView voteView;
    @Bind(R.id.movie_votes)TextView totalView;
    @Bind(R.id.favorite_button)CheckBox favBox;
    @Bind(R.id.movie_overview)TextView overView;
    @Bind(R.id.trailer_view)RecyclerView trailerView;
    @Bind(R.id.review_view)RecyclerView reviewView;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private Boolean mNet = false;
    private ArrayList<String> mTrailers;
    private Movie movie;
    private static final int LOADER_ID = 0;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        //Binding views
        ButterKnife.bind(this, view);
        //Getting clicked movie
        if(getActivity().getIntent()!=null) {
            movie = getActivity().getIntent().getParcelableExtra(Utility.INTENT_CONSTANT);
        }
        if(getArguments()!=null){
            movie = getArguments().getParcelable(Utility.BUNDLE_CONSTANT);
        }
        //Initialising review adapter
        mReviewAdapter = new ReviewAdapter(getContext());
        //Binding listener
        mTrailerAdapter = new TrailerAdapter(getActivity());
        mTrailerAdapter.setOnClickListener(this);
        //Setting adapter to trailerView
        trailerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trailerView.setAdapter(mTrailerAdapter);
        //Setting adapter to reviewView
        reviewView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        reviewView.setAdapter(mReviewAdapter);
        if(Utility.checkNetwork(getContext())) {
            mNet = true;
            // Starting asyncTask
            new TrailerTask(this).execute(movie.getId());
            new ReviewTask(this).execute(movie.getId());
        }
        //Setting listener to button
        favBox.setOnClickListener(this);
       // Binding data
        bindData(movie);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Starting loader
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    //Binds data to views
    private void bindData(Movie movie){
        titleView.setText(movie.getTitle());
        dateView.setText(movie.getDate());
        Picasso.with(getContext()).load(movie.getBack_poster()).into(imageView);
        voteView.setText(String.valueOf(movie.getVote_avg()));
        String total = getString(R.string.before) + movie.getTotal_votes() + getString(R.string.after);
        totalView.setText(total);
        overView.setText(movie.getOverview());
    }


    @Override
    public void onTrailerClicked(int position, View v) {
        //Building intent
        Uri uri = Uri.parse(mTrailers.get(position)).buildUpon().build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onTrailerCompleted(ArrayList<String> trailers, ArrayList<String> trailer_posters) {
        mTrailers = trailers;
        mTrailerAdapter.setPoster(trailer_posters);
    }

    @Override
    public void onReviewCompleted(ArrayList<String> reviews) {
      //Passing review to adapter
        mReviewAdapter.setReviews(reviews);
    }

    //Favorite button clicked
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.favorite_button) {
            if (favBox.isChecked()) {
                //Add item to database
                getActivity().getContentResolver().insert(MovieContract.FavoriteMovies.CONTENT_URI, getContentValues());
                }
            } if(!favBox.isChecked()) {
                //Delete item from database
                getActivity().getContentResolver().delete(MovieContract.FavoriteMovies.CONTENT_URI,
                        MovieContract.FavoriteMovies.COLUMN_ID + "=?", new String[]{movie.getId()});
            }
        }

    //Get content values
    private ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(MovieContract.FavoriteMovies.COLUMN_ID, movie.getId());
        values.put(MovieContract.FavoriteMovies.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.FavoriteMovies.COLUMN_POSTER, movie.getPoster());
        values.put(MovieContract.FavoriteMovies.COLUMN_BACK_POSTER, movie.getBack_poster());
        values.put(MovieContract.FavoriteMovies.COLUMN_SUMMARY, movie.getOverview());
        values.put(MovieContract.FavoriteMovies.COLUMN_DATE, movie.getDate());
        values.put(MovieContract.FavoriteMovies.COLUMN_RATING, movie.getVote_avg());
        values.put(MovieContract.FavoriteMovies.COLUMN_VOTES, movie.getTotal_votes());
        values.put(MovieContract.FavoriteMovies.COLUMN_FAVORITES, 1);
        return values;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(), MovieContract.FavoriteMovies.CONTENT_URI,
                new String[]{MovieContract.FavoriteMovies.COLUMN_FAVORITES},
                MovieContract.FavoriteMovies.COLUMN_ID + "=?", new String[]{movie.getId()}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int fav = 0;
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                fav = cursor.getInt(0);
            }
        }
        if (fav == 1) {
            favBox.setChecked(true);
        } else {
            favBox.setChecked(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(mNet)
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_share){
            startIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Starts ativity for sharing data
    private void startIntent(){
        String text = "#PopularMovies";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if(mTrailers.size()!=0) {
            intent.putExtra(Intent.EXTRA_TEXT, mTrailers.get(0) + text);
        }else { intent.putExtra(Intent.EXTRA_TEXT, "No trailers "+text); }
        startActivity(Intent.createChooser(intent, "Send"));
    }

}
