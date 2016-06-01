package com.example.android.movies.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    //authority of uri
    public static final String AUTHORITY = "com.example.android.movies";
    //content uri
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static class FavoriteMovies implements BaseColumns {
        //table name
        public static final String TABLE_NAME = "favorite";
        //Column for movie id
        public static final String COLUMN_ID = "movie_id";
        //Column for title
        public static final String COLUMN_TITLE = "title";
        //Column for poster
        public static final String COLUMN_POSTER = "poster";
        //Column for back poster
        public static final String COLUMN_BACK_POSTER = "back_poster";
        //Column for summary
        public static final String COLUMN_SUMMARY = "summary";
        //Column for date
        public static final String COLUMN_DATE = "date";
        //Column for rating
        public static final String COLUMN_RATING = "rating";
        //Column for votes
        public static final String COLUMN_VOTES = "votes";
        //Column for favorites
        public static final String COLUMN_FAVORITES = "favorites";
        //content uri for table
        public static final Uri CONTENT_URI = MovieContract.CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
        //MIME type for directory of items
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI.toString();
        //MIME type for single item
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_URI.toString();
    }

    //Apppending id to uri
    public static Uri getUriForId(long id){
        return FavoriteMovies.CONTENT_URI.buildUpon().appendPath(Integer.toString((int)id)).build();
    }
}
