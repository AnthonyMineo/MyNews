package com.denma.mynews;

import com.denma.mynews.Models.MostPopularAPI.MostPopularArticles;
import com.denma.mynews.Models.MostPopularAPI.MostPopularResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;

public class MostPopularTest {

    @Test
    public void MostPopularTest() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        Disposable disposable;

        final String[] section = new String[1];
        final String[] publishedDate = new String[1];
        final String[] title = new String[1];
        final String[] urlMedia = new String[1];
        final String[] urlArticle = new String[1];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "  \"status\": \"OK\",\n" +
                "  \"copyright\": \"Copyright (c) 2018 The New York Times Company.  All Rights Reserved.\",\n" +
                "  \"num_results\": 1936,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"url\": \"monUrlArticle\",\n" +
                "      \"adx_keywords\": \"Cruz, Nikolas;Parkland, Fla, Shooting (2018);Marjory Stoneman Douglas High School (Parkland, Fla);School Shootings and Armed Attacks;Mental Health and Disorders\",\n" +
                "      \"column\": \"Op-Ed Contributor\",\n" +
                "      \"section\": \"Opinion\",\n" +
                "      \"byline\": \"By ISABELLE ROBINSON\",\n" +
                "      \"type\": \"Article\",\n" +
                "      \"title\": \"MyTestTitle\",\n" +
                "      \"abstract\": \"The notion that the Parkland shootings wouldn’t have occurred if students had been kinder is deeply dangerous.\",\n" +
                "      \"published_date\": \"2018-03-27\",\n" +
                "      \"source\": \"The New York Times\",\n" +
                "      \"id\": 100000005820482,\n" +
                "      \"asset_id\": 100000005820482,\n" +
                "      \"views\": 1,\n" +
                "      \"des_facet\": [\n" +
                "        \"PARKLAND, FLA, SHOOTING (2018)\",\n" +
                "        \"SCHOOL SHOOTINGS AND ARMED ATTACKS\",\n" +
                "        \"MENTAL HEALTH AND DISORDERS\"\n" +
                "      ],\n" +
                "      \"org_facet\": [\n" +
                "        \"MARJORY STONEMAN DOUGLAS HIGH SCHOOL (PARKLAND, FLA)\"\n" +
                "      ],\n" +
                "      \"per_facet\": [\n" +
                "        \"CRUZ, NIKOLAS\"\n" +
                "      ],\n" +
                "      \"geo_facet\": \"\",\n" +
                "      \"media\": [\n" +
                "        {\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"Nikolas Cruz on his Instagram account.\",\n" +
                "          \"copyright\": \"Instagram\",\n" +
                "          \"approved_for_syndication\": 0,\n" +
                "          \"media-metadata\": [\n" +
                "            {\n" +
                "              \"url\": \"monUrlMedia\",\n" +
                "              \"format\": \"square320\",\n" +
                "              \"height\": 320,\n" +
                "              \"width\": 320\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n" +
                "    "));

        NYTService service = retrofit.create(NYTService.class);

        Observable<MostPopularResponse> fecthMostPopular = service.getMPArticles();

        disposable = fecthMostPopular.subscribeWith(new DisposableObserver<MostPopularResponse>(){
            @Override
            public void onNext(MostPopularResponse mostPopularResponse) {
                MostPopularArticles article = mostPopularResponse.getResults().get(0);
                section[0] = article.getSection();
                publishedDate[0] = article.getPublishedDate();
                title[0] = article.getTitle();
                urlMedia[0] = article.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                urlArticle[0] = article.getUrl();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        assertEquals("Opinion", section[0]);
        assertEquals("2018-03-27", publishedDate[0]);
        assertEquals("MyTestTitle", title[0]);
        assertEquals("monUrlMedia", urlMedia[0]);
        assertEquals("monUrlArticle", urlArticle[0]);

        disposable.dispose();
        mockWebServer.shutdown();

    }
}
