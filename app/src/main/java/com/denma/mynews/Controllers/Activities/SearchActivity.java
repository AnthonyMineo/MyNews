package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.denma.mynews.Controllers.Fragments.SearchFragment;
import com.denma.mynews.R;

public class SearchActivity extends AppCompatActivity {

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.configureToolbar();
        this.configureAndShowSearchFragment();
    }

    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowSearchFragment(){
        searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_frame_layout);
        if (searchFragment == null) {
            // B - Create new main fragment
            searchFragment = new SearchFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_frame_layout, searchFragment)
                    .commit();
        }
    }
}
