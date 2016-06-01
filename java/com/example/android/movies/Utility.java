package com.example.android.movies;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.view.animation.AlphaAnimation;

public class Utility {
    //Uri to get popular posters
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?";
    //Uri to get top rated posters
    public static final String RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?";
    //Uri to get trailer keys
    public static final String TRAILER_URL = "http://api.themoviedb.org/3/movie";
    //Youtube uri
    public static final String YOUTUBE_URL = "http://www.youtube.com/watch";
    //Trailer poster url
    public static final String TRAILER_POSTER_URL = "http://img.youtube.com/vi/";
    //Image constant to sdd to url
    public static final String IMAGE_CONSTANT = "http://image.tmdb.org/t/p/w185/";
    //Intent constant
    public static final String INTENT_CONSTANT = "intent";
    //Bundle constant
    public static final String BUNDLE_CONSTANT = "bundle";

    //To set tab layout opcaity accroding to position
    public static void setTabOpacity(TabLayout tabLayout, int position){
        switch (position) {
            case 0:
                tabLayout.getTabAt(0).getCustomView().startAnimation(getAnimationOpacity(false));
                tabLayout.getTabAt(1).getCustomView().startAnimation(getAnimationOpacity(true));
                tabLayout.getTabAt(2).getCustomView().startAnimation(getAnimationOpacity(true));
                break;
            case 1:
                tabLayout.getTabAt(1).getCustomView().startAnimation(getAnimationOpacity(false));
                tabLayout.getTabAt(0).getCustomView().startAnimation(getAnimationOpacity(true));
                tabLayout.getTabAt(2).getCustomView().startAnimation(getAnimationOpacity(true));
                break;
            case 2:
                tabLayout.getTabAt(2).getCustomView().startAnimation(getAnimationOpacity(false));
                tabLayout.getTabAt(0).getCustomView().startAnimation(getAnimationOpacity(true));
                tabLayout.getTabAt(1).getCustomView().startAnimation(getAnimationOpacity(true));
                break;
        }
    }

    //Set initial animation
    public static void setInitialAnimation(TabLayout tabLayout){
        tabLayout.getTabAt(1).getCustomView().startAnimation(getAnimationOpacity(true));
        tabLayout.getTabAt(2).getCustomView().startAnimation(getAnimationOpacity(true));
    }

    //To get animation opacity
    public static AlphaAnimation getAnimationOpacity(Boolean state){
        AlphaAnimation animation;
        if(state){
            animation = new AlphaAnimation(0.5F, 0.5F);
        }else {
            animation = new AlphaAnimation(1F, 1F);
        }
        animation.setDuration(0);
        animation.setFillAfter(true);
        return animation;
    }

    //Check network available
    public static boolean checkNetwork(Context context){
            ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        return false;
        }
    }

