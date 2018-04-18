package com.denma.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchDoc;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResult;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.R;
import com.denma.mynews.Utils.NYTStream;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_sport_textview)
    TextView textView;

    //FOR DATA
    private Disposable disposable;

    public SportFragment() { }

    public static SportFragment newInstance() {
        return (new SportFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport, container, false);
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
        this.disposable = NYTStream.streamFetchArticleSearch(null,"news_desk: \"Sports\"", null, null).subscribeWith(new DisposableObserver<ArticleSearchResponse>() {
            @Override
            public void onNext(ArticleSearchResponse response) {
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

    private void updateUIwithResponse(ArticleSearchResponse response){
        ArticleSearchResult result = response.getResponse();
        List<ArticleSearchDoc> articles = result.getDocs();
        StringBuilder stringBuilder = new StringBuilder();
        for(ArticleSearchDoc article : articles)
            stringBuilder.append("-"+article.getSnippet()+"\n");
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}