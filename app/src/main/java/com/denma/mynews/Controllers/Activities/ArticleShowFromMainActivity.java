package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.denma.mynews.Controllers.Fragments.ArticleFragment;
import com.denma.mynews.R;

public class ArticleShowFromMainActivity extends AppCompatActivity {

    private ArticleFragment articleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article_from_main);

        // - Configure each component
        this.configureToolbar();
        this.configureAndShowArticleFragment();
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

    private void configureAndShowArticleFragment(){
        articleFragment = (ArticleFragment) getSupportFragmentManager().findFragmentById(R.id.activity_show_from_main_article_frame_layout);
        if (articleFragment == null) {
            // B - Create new main fragment
            this.articleFragment = ArticleFragment.newInstance();
            articleFragment.setArguments(getIntent().getExtras());
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_show_from_main_article_frame_layout, articleFragment)
                    .commit();
        }
    }
}
