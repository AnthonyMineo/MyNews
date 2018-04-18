package com.denma.mynews.Views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.denma.mynews.Controllers.Fragments.MostPopularFragment;
import com.denma.mynews.Controllers.Fragments.SportFragment;
import com.denma.mynews.Controllers.Fragments.TopStoriesFragment;
import com.denma.mynews.R;

public class PageAdapter extends FragmentPagerAdapter {

    Context context;
    //Constructor
    public PageAdapter(FragmentManager mgr, Context mContext) {
        super(mgr);
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return(3);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return SportFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: //Page number 1
                return context.getResources().getString(R.string.tab_layout_title_1);
            case 1: //Page number 2
                return context.getResources().getString(R.string.tab_layout_title_2);
            case 2: //Page number 3
                return context.getResources().getString(R.string.tab_layout_title_3);
            default:
                return null;
        }
    }
}
