package com.denma.mynews.Utils;

import com.denma.mynews.Models.NYTArticleSearch;
import com.denma.mynews.Models.NYTMostPopular;
import com.denma.mynews.Models.NYTTopStories;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYTService {

    @GET("topstories/v2/{section}.json?api-key=xxxxx")
    Observable<List<NYTTopStories>> function1(@Path("section") String section);

    @GET("mostpopular/v2/mostviewed/all-sections/30.json?api-key=xxxxx")
    Observable<List<NYTMostPopular>> function2();

    //will probably change, just want to do some test before
    @GET("search/v2/articlesearch.json?q={key-words}&fq=news_desk:(\"{category}\")&begin_date{begin}&end_date={end}&api-key=xxxxx")
    Observable<List<NYTArticleSearch>> function3(@Path("key-words") String keyWords, @Path("category") String category, @Path("begin") String beginDate, @Path("end") String endDate);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
