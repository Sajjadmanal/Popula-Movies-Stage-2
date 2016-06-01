package com.example.android.movies.data;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.android.movies.data.MovieContract.FavoriteMovies;

public class MovieProvider extends ContentProvider{
    //constants for uri matcher
    private static final int TABLE = 1;
    private static final int TABLE_ID = 2;
    private static final UriMatcher mUriMatcher;
    private SQLiteOpenHelper mOpenHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(MovieContract.AUTHORITY, FavoriteMovies.TABLE_NAME, TABLE );
        mUriMatcher.addURI(MovieContract.AUTHORITY, FavoriteMovies.TABLE_NAME + "/#", TABLE_ID);

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor;
        switch (mUriMatcher.match(uri)){
            case TABLE:{
                cursor = db.query(FavoriteMovies.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported Uri" + uri);
        }
        if(getContext()!=null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)){
            case TABLE:
                return FavoriteMovies.CONTENT_TYPE;
            case TABLE_ID:
                return FavoriteMovies.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported Uri" + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri uri1=null;
        switch (mUriMatcher.match(uri)){
            case TABLE:{
                long id = db.insert(FavoriteMovies.TABLE_NAME, null, values);
                if(id!=-1){
                    uri1 = MovieContract.getUriForId(id);
                }
                break;
            }
            default:{
                throw new IllegalArgumentException("Unsupported Uri For Insertion " + uri);
            }
        }
        if(getContext()!=null)
            getContext().getContentResolver().notifyChange(uri, null);
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int delete;
        switch (mUriMatcher.match(uri)){
            case TABLE_ID:{
                delete = db.delete(FavoriteMovies.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case TABLE:{
                delete = db.delete(FavoriteMovies.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported Uri For Deletion " + uri);
        }
        if(getContext()!=null&&delete!=0)
            getContext().getContentResolver().notifyChange(uri, null);
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int update;
        switch (mUriMatcher.match(uri)){
            case TABLE_ID:{
                update = db.update(FavoriteMovies.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported Uri For Updating " + uri);
        }
        if(getContext()!=null)
            getContext().getContentResolver().notifyChange(uri, null);
        return update;
    }
}
