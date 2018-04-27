package com.denma.mynews.Controllers.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denma.mynews.Controllers.Activities.ArticleShowFromSearchActivity;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchArticles;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.R;
import com.denma.mynews.Utils.ItemClickSupport;
import com.denma.mynews.Utils.NYTStream;
import com.denma.mynews.Views.ArticleSearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_search_result_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_search_result_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;
    private List<ArticleSearchArticles> searchResultArticles;
    private List<ArticleSearchArticles> prefArticles;
    private ArticleSearchAdapter searchResultAdapter;

    private String queryTerm;
    private String newsDesk;
    private String beginDate;
    private String endDate;

    private SharedPreferences mPreferences;

    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance() {
        return (new SearchResultFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
        // Init SharedPreferences using Default wich make it easily recoverable throught activity/fragment
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.configureDataFromPref();
        this.updateUIwithPref(this.prefArticles);
        return view;
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView() {
        // - Reset list
        this.searchResultArticles = new ArrayList<>();
        // - Create adapter passing the list of sportArticles
        this.searchResultAdapter = new ArticleSearchAdapter(this.searchResultArticles, Glide.with(this));
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.searchResultAdapter);
        // - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_recycle_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        // 1 - Get user from adapter
                        ArticleSearchArticles article = searchResultAdapter.getArticle(position);
                        // 2 - Do something
                        Intent intent = new Intent(getActivity(), ArticleShowFromSearchActivity.class);
                        intent.putExtra("url", article.getWebUrl());
                        startActivity(intent);
                    }
                });
    }

    private void configureDataFromPref(){
        this.queryTerm = mPreferences.getString("query", null);
        this.newsDesk = mPreferences.getString("newsDesk", null);
        this.beginDate = mPreferences.getString("beginDate", null);
        this.endDate = mPreferences.getString("endDate", null);

        String listArticlesSerializedToJson = mPreferences.getString("listArticles", null);
        Type listType = new TypeToken<ArrayList<ArticleSearchArticles>>() {}.getType();
        this.prefArticles = new Gson().fromJson(listArticlesSerializedToJson, listType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // - Execute our Stream
    private void executeHttpRequestWithRetrofit() {
        // - Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStream.streamFetchArticleSearch(this.queryTerm, this.newsDesk, this.beginDate, this.endDate).subscribeWith(new DisposableObserver<ArticleSearchResponse>() {
            @Override
            public void onNext(ArticleSearchResponse response) {
                Log.e("TAG", "On Next");
                // - Update UI with response
                updateUIwithRetrofit(response);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error " + e.getMessage());
                // - Signal that there is probably no internet connection
                Toast.makeText(getContext(), "Please make sure you have access to internet !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUIwithRetrofit(ArticleSearchResponse response) {
        // - Stop refreshing and clear actual list of sportArcticles
        swipeRefreshLayout.setRefreshing(false);
        searchResultArticles.clear();
        searchResultArticles.addAll(response.getResult().getArticleSearchArticles());
        searchResultAdapter.notifyDataSetChanged();
    }

    private void updateUIwithPref(List<ArticleSearchArticles> articles) {
        // - Stop refreshing and clear actual list of sportArcticles
        swipeRefreshLayout.setRefreshing(false);
        searchResultArticles.clear();
        searchResultArticles.addAll(articles);
        searchResultAdapter.notifyDataSetChanged();
    }

}

