package com.denma.mynews.Utils;

import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.Models.MostPopularAPI.MostPopularResponse;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStream {

    // - Create a stream that will get top stories article from NYT Top Stories API
    public static Observable<TopStoriesResponse> streamFetchTopStories(){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.getTSArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // - Create a stream that will get most popular article from NYT Most Popular API
    public static Observable<List<MostPopularResponse>> streamFetchMostPopular(){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.getMPArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // - Create a stream that will get top stories article from NYT Top Stories API
    public static Observable<List<ArticleSearchResponse>> streamFetchArticleSearch(String keyWords, String category, String beginDate, String endDate){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.getSArticles(keyWords, category, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
