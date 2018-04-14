package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.denma.mynews.Controllers.Fragments.TopStoriesFragment;
import com.denma.mynews.R;

public class MainActivity extends AppCompatActivity {

    private TopStoriesFragment topStoriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMainFragment();
    }

    private void configureAndShowMainFragment(){
        topStoriesFragment = (TopStoriesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if(topStoriesFragment == null)
        {
            topStoriesFragment = new TopStoriesFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_main_frame_layout, topStoriesFragment).commit();
        }


    }
}
