package com.denma.mynews;

import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchArticles;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchKeyword;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArticleSearchTest {

    @Test
    public void ArticleSearchTest() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        Disposable disposable;

        final String[] sectionName = new String[1];
        final String[] news_desk = new String[1];
        final String[] publishedDate = new String[1];
        final String[] title = new String[1];
        final String[] urlMedia = new String[1];
        final String[] urlArticle = new String[1];
        final String[] headLine = new String[1];
        final boolean[] keyWordExist = {false};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "  \"status\": \"OK\",\n" +
                "  \"copyright\": \"Copyright (c) 2018 The New York Times Company. All Rights Reserved.\",\n" +
                "  \"response\": {\n" +
                "    \"docs\": [\n" +
                "      {\n" +
                "        \"web_url\": \"monUrlArticle\",\n" +
                "        \"snippet\": \"MyTestTitle\",\n" +
                "        \"abstract\": \"MLB Roundup; Atlanta Braves beat St Louis Cardinals, 6-5; other baseball news noted.\",\n" +
                "        \"print_page\": \"4\",\n" +
                "        \"blog\": {},\n" +
                "        \"source\": \"The New York Times\",\n" +
                "        \"multimedia\": [\n" +
                "          {\n" +
                "            \"rank\": 0,\n" +
                "            \"subtype\": \"wide\",\n" +
                "            \"caption\": null,\n" +
                "            \"credit\": null,\n" +
                "            \"type\": \"image\",\n" +
                "            \"url\": \"images/2014/05/19/sports/BATS/BATS-thumbWide.jpg\",\n" +
                "            \"height\": 126,\n" +
                "            \"width\": 190,\n" +
                "            \"legacy\": {\n" +
                "              \"wide\": \"images/2014/05/19/sports/BATS/BATS-thumbWide.jpg\",\n" +
                "              \"wideheight\": \"126\",\n" +
                "              \"widewidth\": \"190\"\n" +
                "            },\n" +
                "            \"subType\": \"wide\",\n" +
                "            \"crop_name\": null\n" +
                "          },\n" +
                "          {\n" +
                "            \"rank\": 0,\n" +
                "            \"subtype\": \"xlarge\",\n" +
                "            \"caption\": null,\n" +
                "            \"credit\": null,\n" +
                "            \"type\": \"image\",\n" +
                "            \"url\": \"images/2014/05/19/sports/BATS/BATS-articleLarge.jpg\",\n" +
                "            \"height\": 501,\n" +
                "            \"width\": 600,\n" +
                "            \"legacy\": {\n" +
                "              \"xlargewidth\": \"600\",\n" +
                "              \"xlarge\": \"images/2014/05/19/sports/BATS/BATS-articleLarge.jpg\",\n" +
                "              \"xlargeheight\": \"501\"\n" +
                "            },\n" +
                "            \"subType\": \"xlarge\",\n" +
                "            \"crop_name\": null\n" +
                "          },\n" +
                "          {\n" +
                "            \"rank\": 0,\n" +
                "            \"subtype\": \"thumbnail\",\n" +
                "            \"caption\": null,\n" +
                "            \"credit\": null,\n" +
                "            \"type\": \"image\",\n" +
                "            \"url\": \"monUrlMedia\",\n" +
                "            \"height\": 75,\n" +
                "            \"width\": 75,\n" +
                "            \"legacy\": {\n" +
                "              \"thumbnailheight\": \"75\",\n" +
                "              \"thumbnail\": \"images/2014/05/19/sports/BATS/BATS-thumbStandard.jpg\",\n" +
                "              \"thumbnailwidth\": \"75\"\n" +
                "            },\n" +
                "            \"subType\": \"thumbnail\",\n" +
                "            \"crop_name\": null\n" +
                "          }\n" +
                "        ],\n" +
                "        \"headline\": {\n" +
                "          \"main\": \"Walk and Wild Pitch Help Obama Sneak By\",\n" +
                "          \"kicker\": \"Roundup\",\n" +
                "          \"content_kicker\": \"Roundup\",\n" +
                "          \"print_headline\": \"Walk and Wild Pitch Help Braves Sneak By\",\n" +
                "          \"name\": null,\n" +
                "          \"seo\": null,\n" +
                "          \"sub\": null\n" +
                "        },\n" +
                "        \"keywords\": [\n" +
                "          {\n" +
                "            \"name\": \"organizations\",\n" +
                "            \"value\": \"St Louis Cardinals\",\n" +
                "            \"rank\": 3,\n" +
                "            \"major\": null\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"persons\",\n" +
                "            \"value\": \"Obama\",\n" +
                "            \"rank\": 2,\n" +
                "            \"major\": \"N\"\n" +
                "          },\n" +
                "          {\n" +
                "            \"name\": \"subject\",\n" +
                "            \"value\": \"Regulation and Deregulation of Industry\",\n" +
                "            \"rank\": 3,\n" +
                "            \"major\": \"N\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"pub_date\": \"2014-05-19T00:00:00Z\",\n" +
                "        \"document_type\": \"article\",\n" +
                "        \"new_desk\": \"Sports\",\n" +
                "        \"section_name\": \"Baseball\",\n" +
                "        \"byline\": {\n" +
                "          \"original\": \"By THE ASSOCIATED PRESS\",\n" +
                "          \"person\": [],\n" +
                "          \"organization\": \"THE ASSOCIATED PRESS\"\n" +
                "        },\n" +
                "        \"type_of_material\": \"News\",\n" +
                "        \"_id\": \"53795db138f0d805525a3409\",\n" +
                "        \"word_count\": 483,\n" +
                "        \"score\": 1\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}"));

        NYTService service = retrofit.create(NYTService.class);

        Observable<ArticleSearchResponse> fecthArticleSearch = service.getSArticles("Obama", "news_desk: \"Sports\"", "20170124", "20188424");

        disposable = fecthArticleSearch.subscribeWith(new DisposableObserver<ArticleSearchResponse>(){
            @Override
            public void onNext(ArticleSearchResponse articleSearchResponse) {
                ArticleSearchArticles article = articleSearchResponse.getResponse().getArticleSearchArticles().get(0);
                sectionName[0] = article.getSectionName();
                news_desk[0] = article.getNewDesk();
                publishedDate[0] = article.getPubDate().substring(0,10);
                title[0] = article.getSnippet();
                urlMedia[0] = article.getMultimedia().get(2).getUrl();
                urlArticle[0] = article.getWebUrl();
                headLine[0] = article.getHeadline().getMain();
                List<ArticleSearchKeyword> keyWords = article.getKeywords();
                for(ArticleSearchKeyword key : keyWords)
                {
                    if(key.getValue().contains("Obama")){
                        keyWordExist[0] = true;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        assertEquals("Baseball", sectionName[0]);
        assertEquals("Sports", news_desk[0]);
        assertEquals("2014-05-19", publishedDate[0]);
        assertEquals("MyTestTitle", title[0]);
        assertEquals("monUrlMedia", urlMedia[0]);
        assertEquals("monUrlArticle", urlArticle[0]);
        assertTrue(headLine[0].contains("Obama"));
        assertEquals(keyWordExist[0], true);

        disposable.dispose();
        mockWebServer.shutdown();

    }
}
