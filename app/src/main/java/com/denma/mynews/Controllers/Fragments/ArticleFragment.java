package com.denma.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.denma.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleFragment extends Fragment {

    @BindView(R.id.article_web_view)
    WebView webView;

    public ArticleFragment() { }

    public static ArticleFragment newInstance() {
        return (new ArticleFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.bind(this, view);
        String url = getArguments().getString("url");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
        return view;

    }

}
