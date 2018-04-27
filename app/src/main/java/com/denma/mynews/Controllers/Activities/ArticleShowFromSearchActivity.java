package com.denma.mynews.Controllers.Activities;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.denma.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleShowFromSearchActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.article_web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_show_from_search);
        ButterKnife.bind(this);
        // - Configure each component
        this.configureToolbar();
        this.configureWebView();
    }

    private void configureToolbar(){
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureWebView(){
        String url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
