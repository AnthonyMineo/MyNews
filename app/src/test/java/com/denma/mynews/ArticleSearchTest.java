package com.denma.mynews;

import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchArticles;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchKeyword;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
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

    public String loadJSONFromResources(String fileName) {
        String json = null;
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

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

        mockWebServer.enqueue(new MockResponse().setBody(loadJSONFromResources("ArticleSearch.json")));

        NYTService service = retrofit.create(NYTService.class);

        Observable<ArticleSearchResponse> fecthArticleSearch = service.getSArticles("Obama", "news_desk: \"Sports\"", "20170124", "20188424");

        disposable = fecthArticleSearch.subscribeWith(new DisposableObserver<ArticleSearchResponse>(){
            @Override
            public void onNext(ArticleSearchResponse articleSearchResponse) {
                ArticleSearchArticles article = articleSearchResponse.getResult().getArticleSearchArticles().get(0);
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
