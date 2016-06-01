package com.example.android.movies.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.movies.data.MovieContract.FavoriteMovies;

public class MovieDatabase extends SQLiteOpenHelper {
    //Database version
    public static final int DATABASE_VERSION = 2;
    //Database name
    public static final String DATABASE_NAME = "movie.db";

    //Values passing to super constructor
    public MovieDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     final String CREATE_TABLE = "CREATE TABLE " + FavoriteMovies.TABLE_NAME + "("
             + FavoriteMovies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
             + FavoriteMovies.COLUMN_ID + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_TITLE + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_POSTER + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_BACK_POSTER + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_SUMMARY + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_DATE + " TEXT NOT NULL,"
             + FavoriteMovies.COLUMN_RATING + " REAL NOT NULL,"
             + FavoriteMovies.COLUMN_VOTES + " INTEGER NOT NULL,"
             + FavoriteMovies.COLUMN_FAVORITES + " INTEGER NOT NULL" + ")";
        //Creating table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //Deleting tables if exists
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovies.TABLE_NAME);
    }
}
