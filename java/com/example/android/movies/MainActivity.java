package com.example.android.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.movies.adapters.SectionsPagerAdapter;
import com.example.android.movies.data.Movie;

public class MainActivity extends AppCompatActivity implements MoviesFragment.MovieClicked,FavoritesFragment.FavoriteClicked {

    private Boolean mTwoPane;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Checking layout if tab mode or not
        if(findViewById(R.id.detail_container)!=null) {
            //Setting action bar if tablet
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            mTwoPane = true;
        }else {
            mTwoPane = false;
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(0);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setTabView(tabLayout);
    }

    //Setting tab custom view
    private void setTabView(@NonNull final TabLayout tabLayout){
        for(int i=0;i<3;i++) {
            tabLayout.getTabAt(i).setCustomView(mSectionsPagerAdapter.getTabView(this, i));
        }
        //Setting initial tab animation
        Utility.setInitialAnimation(tabLayout);
        //Setting page listener
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Utility.setTabOpacity(tabLayout, position);
                mSectionsPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //called when movie clicked
    private void onItemClicked(Movie movie){
        if(mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putParcelable(Utility.BUNDLE_CONSTANT, movie);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        if(!mTwoPane) {
            //Starting detail activity
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Utility.INTENT_CONSTANT, movie);
            startActivity(intent);
        }
    }

    @Override
    public void onMovieClicked(Movie movie) {
       onItemClicked(movie);
    }

    @Override
    public void onFavClicked(Movie movie) {
        onItemClicked(movie);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return mTwoPane;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}