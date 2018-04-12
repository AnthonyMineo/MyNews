package com.denma.mynews.Utils;

import com.denma.mynews.Models.NYTArticleSearch;
import com.denma.mynews.Models.NYTMostPopular;
import com.denma.mynews.Models.NYTTopStories;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NYTStream {

    // - Create a stream that will get top stories article from NYT Top Stories API
    public static Observable<List<NYTTopStories>> streamFetchTopStories(String section){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.function1(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // - Create a stream that will get most popular article from NYT Most Popular API
    public static Observable<List<NYTMostPopular>> streamFetchMostPopular(){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.function2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // - Create a stream that will get top stories article from NYT Top Stories API
    public static Observable<List<NYTArticleSearch>> streamFetchArticleSearch(String keyWords, String category, String beginDate, String endDate){
        NYTService NytService = NYTService.retrofit.create(NYTService.class);
        return NytService.function3(keyWords, category, beginDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
