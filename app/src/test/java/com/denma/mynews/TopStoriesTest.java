package com.denma.mynews;

import com.denma.mynews.Models.TopStoriesAPI.TopStoriesArticles;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopStoriesTest {

    @Test
    public void TopStoriesTest() throws IOException{
        MockWebServer mockWebServer = new MockWebServer();
        Disposable disposable;

        final String[] section = new String[1];
        final String[] subSection = new String[1];
        final String[] updateDate = new String[1];
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
                "  \"copyright\": \"Copyright (c) 2018 The New York Times Company. All Rights Reserved.\",\n" +
                "  \"section\": \"world\",\n" +
                "  \"last_updated\": \"2018-04-23T10:07:09-04:00\",\n" +
                "  \"num_results\": 39,\n" +
                "  \"results\": [\n" +
                "    {\n" +
                "      \"section\": \"World\",\n" +
                "      \"subsection\": \"France\",\n" +
                "      \"title\": \"UnitTestTopStories\",\n" +
                "      \"abstract\": \"Europe is outsourcing border management to distant countries, hoping to stop migrants before they cross the Mediterranean. But there may be a high moral cost.\",\n" +
                "      \"url\": \"monUrlArticle\",\n" +
                "      \"byline\": \"By PATRICK KINGSLEY\",\n" +
                "      \"item_type\": \"Article\",\n" +
                "      \"updated_date\": \"2018-04-23T23:45:09-04:00\",\n" +
                "      \"created_date\": \"2018-04-22T23:45:09-04:00\",\n" +
                "      \"published_date\": \"2018-04-22T23:45:09-04:00\",\n" +
                "      \"material_type_facet\": \"\",\n" +
                "      \"kicker\": \"\",\n" +
                "      \"des_facet\": [\n" +
                "        \"Refugees and Displaced Persons\",\n" +
                "        \"Middle East and Africa Migrant Crisis\",\n" +
                "        \"Immigration and Emigration\",\n" +
                "        \"War Crimes, Genocide and Crimes Against Humanity\",\n" +
                "        \"Human Rights and Human Rights Violations\"\n" +
                "      ],\n" +
                "      \"org_facet\": [\n" +
                "        \"European Union\",\n" +
                "        \"Janjaweed\"\n" +
                "      ],\n" +
                "      \"per_facet\": [],\n" +
                "      \"geo_facet\": [\n" +
                "        \"Sudan\",\n" +
                "        \"Darfur (Sudan)\",\n" +
                "        \"Libya\"\n" +
                "      ],\n" +
                "      \"multimedia\": [\n" +
                "        {\n" +
                "          \"url\": \"monUrlMedia\",\n" +
                "          \"format\": \"Standard Thumbnail\",\n" +
                "          \"height\": 75,\n" +
                "          \"width\": 75,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"Undocumented immigrants arrested last year by Sudan’s Rapid Support Forces. The European Union has made the country a nerve center for an effort to counter human smuggling, but many migration advocates say the moral cost is high.\",\n" +
                "          \"copyright\": \"Mohamed Nureldin Abdallah/Reuters\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"short_url\": \"https://nyti.ms/2F7TuQk\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"));

        NYTService service = retrofit.create(NYTService.class);

        Observable<TopStoriesResponse> fecthTopStories = service.getTSArticles();

        disposable = fecthTopStories.subscribeWith(new DisposableObserver<TopStoriesResponse>(){
            @Override
            public void onNext(TopStoriesResponse topStoriesResponse) {
                TopStoriesArticles article = topStoriesResponse.getResults().get(0);
                section[0] = article.getSection();
                subSection[0] = article.getSubsection();
                updateDate[0] = article.getUpdatedDate().substring(0, 10);
                title[0] = article.getTitle();
                urlMedia[0] = article.getMultimedia().get(0).getUrl();
                urlArticle[0] = article.getUrl();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        assertEquals("World", section[0]);
        assertEquals("France", subSection[0]);
        assertEquals("2018-04-23", updateDate[0]);
        assertEquals("UnitTestTopStories", title[0]);
        assertEquals("monUrlMedia", urlMedia[0]);
        assertEquals("monUrlArticle", urlArticle[0]);

        disposable.dispose();
        mockWebServer.shutdown();
    }
}


