package com.denma.mynews.Utils;

import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.Models.MostPopularAPI.MostPopularArticles;
import com.denma.mynews.Models.MostPopularAPI.MostPopularResponse;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NYTService {

    @GET("topstories/v2/home.json?api-key=fc87d275a9374ceb9dfaca225dc7380d")
    Observable<TopStoriesResponse> getTSArticles();

    @GET("mostpopular/v2/mostviewed/all-sections/30.json?api-key=fc87d275a9374ceb9dfaca225dc7380d")
    Observable<MostPopularResponse> getMPArticles();

    //will probably change, just want to do some test before
    @GET("search/v2/articlesearch.json?api-key=fc87d275a9374ceb9dfaca225dc7380d")
    Observable<ArticleSearchResponse> getSArticles(@Query("q") String keyWords, @Query("fq") String category, @Query("begin_date") String beginDate, @Query("end_date") String endDate);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(MostPopularArticles.class, new MostPopularArticles.myDeserializer())
            .create();

    public static final Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
