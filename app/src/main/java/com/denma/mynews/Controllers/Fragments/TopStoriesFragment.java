package com.denma.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denma.mynews.Controllers.Activities.ArticleShowActivity;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesArticles;
import com.denma.mynews.R;
import com.denma.mynews.Utils.ItemClickSupport;
import com.denma.mynews.Utils.NYTStream;
import com.denma.mynews.Views.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_top_stories_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_top_stories_swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    //FOR DATA
    private Disposable disposable;

    private List<TopStoriesArticles> topStoriesArticles;
    private TopStoriesAdapter topStoriesAdapter;

    public TopStoriesFragment() { }

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // - Reset list
        this.topStoriesArticles = new ArrayList<>();
        // - Create adapter passing the list of topStoriesArticles
        this.topStoriesAdapter = new TopStoriesAdapter(this.topStoriesArticles, Glide.with(this));
        // - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.topStoriesAdapter);
        // - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_recycle_item)
            .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    // 1 - Get user from adapter
                    TopStoriesArticles article = topStoriesAdapter.getArticle(position);
                    // 2 - Do something
                    Intent intent = new Intent(getActivity(), ArticleShowActivity.class);
                    intent.putExtra("url", article.getUrl());
                    intent.putExtra("parent", "MainActivity");
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    //- Execute our Stream
    private void executeHttpRequestWithRetrofit(){
        // - Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStream.streamFetchTopStories().subscribeWith(new DisposableObserver<TopStoriesResponse>() {
            @Override
            public void onNext(TopStoriesResponse response) {
                //Log.e("TAG","On Next");
                // - Update UI with response
                updateUI(response);
            }

            @Override
            public void onError(Throwable e) {
                //Log.e("TAG","On Error "+ e.getMessage());
                // - Signal that there is probably no internet connection
                Toast.makeText(getContext(), R.string.on_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onComplete() {
                //Log.e("TAG","On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(TopStoriesResponse response){
        // - Stop refreshing and clear actual list of topStoriesArticles
        swipeRefreshLayout.setRefreshing(false);
        topStoriesArticles.clear();
        topStoriesArticles.addAll(response.getResults());
        topStoriesAdapter.notifyDataSetChanged();
    }
}