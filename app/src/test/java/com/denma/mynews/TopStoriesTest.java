package com.denma.mynews;

import com.denma.mynews.Models.TopStoriesAPI.TopStoriesArticles;
import com.denma.mynews.Models.TopStoriesAPI.TopStoriesResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TopStoriesTest {

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
    public void TopStoriesTest() throws IOException{
        MockitoAnnotations.initMocks(this);
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

        mockWebServer.enqueue(new MockResponse().setBody(loadJSONFromResources("TopStories.json")));

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


