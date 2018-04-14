package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denma.mynews.Controllers.Fragments.MostPopularFragment;
import com.denma.mynews.Controllers.Fragments.TopStoriesFragment;
import com.denma.mynews.R;

public class MainActivity extends AppCompatActivity {

    private TopStoriesFragment topStoriesFragment;
    private MostPopularFragment mostPopularFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.configureAndShowTopStoriesFragment();
        this.configureAndShowMostPopularFragment();
    }

    /*private void configureAndShowTopStoriesFragment(){
        topStoriesFragment = (TopStoriesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if(topStoriesFragment == null)
        {
            topStoriesFragment = new TopStoriesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_frame_layout, topStoriesFragment).commit();
        }
    }*/

    private void configureAndShowMostPopularFragment() {
        mostPopularFragment = (MostPopularFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (mostPopularFragment == null) {
            mostPopularFragment = new MostPopularFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_frame_layout, mostPopularFragment).commit();
        }
    }
}
