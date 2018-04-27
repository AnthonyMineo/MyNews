package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.denma.mynews.Controllers.Fragments.SearchResultFragment;
import com.denma.mynews.R;

public class SearchResultActivity extends AppCompatActivity {

    private SearchResultFragment searchResultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
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
        searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.activity_search_result_frame_layout);
        if (searchResultFragment == null) {
            // B - Create new main fragment
            this.searchResultFragment = SearchResultFragment.newInstance();
            searchResultFragment.setArguments(getIntent().getExtras());
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_search_result_frame_layout, searchResultFragment)
                    .commit();
        }
    }
}
