package com.denma.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denma.mynews.Models.MostPopularAPI.MostPopularArticles;
import com.denma.mynews.Models.MostPopularAPI.MostPopularMedias;
import com.denma.mynews.Models.MostPopularAPI.MostPopularMediasMetaData;
import com.denma.mynews.Models.MostPopularAPI.MostPopularResponse;
import com.denma.mynews.R;
import com.denma.mynews.Utils.NYTStream;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class MostPopularFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_most_popular_textview)
    TextView textView;

    //FOR DATA
    private Disposable disposable;

    public MostPopularFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_popular, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTIONS
    // -----------------

    @OnClick(R.id.fragment_most_popular_button)
    public void submit(View view) {
        // 2 - Call the stream
        this.executeHttpRequestWithRetrofit();
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    // 1 - Execute our Stream
    private void executeHttpRequestWithRetrofit(){
        // 1.1 - Update UI
        this.updateUIWhenStartingHTTPRequest();
        // 1.2 - Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStream.streamFetchMostPopular().subscribeWith(new DisposableObserver<MostPopularResponse>() {
            @Override
            public void onNext(MostPopularResponse response) {
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

    private void updateUIwithResponse(MostPopularResponse response){
        List<MostPopularArticles> articles = response.getResults();
        StringBuilder stringBuilder = new StringBuilder();

        List<MostPopularMedias> medias = articles.get(0).getMedia();
        List<MostPopularMediasMetaData> mediasMetaData = medias.get(0).getMediaMetadata();

        for(MostPopularArticles article : articles)
            stringBuilder.append("-"+article.getPublishedDate()+"\n\n" + mediasMetaData.get(0).getUrl() + "\n\n");
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}