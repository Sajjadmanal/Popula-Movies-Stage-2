package com.example.android.movies;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Adding fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, new DetailFragment())
                .commit();
        //Changing color of back button
        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_keyboard_arrow_left_black_24dp);
        upArrow.setColorFilter(ContextCompat.getColor(this, R.color.backButton), PorterDuff.Mode.SRC_ATOP);
        if(getSupportActionBar()!=null)
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //Disabling auto title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }
}
