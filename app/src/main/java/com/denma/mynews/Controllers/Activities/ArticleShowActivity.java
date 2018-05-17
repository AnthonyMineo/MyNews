package com.denma.mynews.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.denma.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleShowActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.article_web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_show_from_main);
        ButterKnife.bind(this);
        this.configureToolbar();
        this.configureWebView();
    }

    private void configureToolbar(){
        // - Set the toolbar
        setSupportActionBar(toolbar);
        // - Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // - Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // - Allow us to use just one Activity, changing UP button effect considering the parent Activity
    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent i = null;
        // - Test from which Activity this one is launch
        if (getIntent().getExtras().getString("parent") == "MainActivity") {
            i = new Intent(this, MainActivity.class);
            // - Bringing the previous Activity to the top without re-creating a new instance
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            i = new Intent(this, SearchResultActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        return i;
    }

    private void configureWebView(){
        // - Configure WebView
        String url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());
    }
}
