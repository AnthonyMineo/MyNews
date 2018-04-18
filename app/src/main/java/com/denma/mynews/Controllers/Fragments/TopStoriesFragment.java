package com.denma.mynews.Controllers.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesArticles;
import com.denma.mynews.R;
import com.denma.mynews.Utils.NYTStream;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_top_stories_textview)
    TextView textView;

    //FOR DATA
    private Disposable disposable;

    public TopStoriesFragment() { }

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_stories, container, false);
        ButterKnife.bind(this, view);
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit(){
        // 1.1 - Update UI
        this.updateUIWhenStartingHTTPRequest();
        // 1.2 - Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStream.streamFetchTopStories().subscribeWith(new DisposableObserver<TopStoriesResponse>() {
            @Override
            public void onNext(TopStoriesResponse response) {
                Log.e("TAG","On Next");
                // 1.3 - Update UI with list of users
                updateUIwithResponse(response);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }

    private void updateUIwithResponse(TopStoriesResponse response){
        List<TopStoriesArticles> articles = response.getResults();
        StringBuilder stringBuilder = new StringBuilder();
        for(TopStoriesArticles article : articles)
            stringBuilder.append("-"+article.getTitle()+"\n");
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}