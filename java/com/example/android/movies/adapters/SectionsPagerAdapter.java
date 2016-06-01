package com.example.android.movies.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.FavoritesFragment;
import com.example.android.movies.MoviesFragment;
import com.example.android.movies.R;
import com.example.android.movies.Utility;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return MoviesFragment.newInstance(buildUrl(Utility.POPULAR_URL));
            case 1: return MoviesFragment.newInstance(buildUrl(Utility.RATED_URL));
            case 2: return FavoritesFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Setting custom view for tab
    public View getTabView(Context context, int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/alex.ttf"));
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_image);
        imageView.setColorFilter(context.getResources().getColor(R.color.backButton));
        switch (position){
            case 0:
                imageView.setImageResource(R.drawable.ic_group_black_24dp);
                textView.setText("Popular");
                break;
            case 1:
                imageView.setImageResource(R.drawable.ic_thumb_up_black_24dp);
                textView.setText("Rated");
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_favorite_black_24dp);
                textView.setText("Favorites");
                break;
        }
        return view;
    }

    //Building url
    private String buildUrl(String baseUri){
        //Building url
        String api_key = "api_key";
        return Uri.parse(baseUri).buildUpon()
                .appendQueryParameter(api_key, mContext.getString(R.string.API_KEY))
                .build().toString();
    }

}
