package com.denma.mynews;

import com.denma.mynews.Models.MostPopularAPI.MostPopularArticles;
import com.denma.mynews.Models.MostPopularAPI.MostPopularResponse;
import com.denma.mynews.Utils.NYTService;

import org.junit.Test;

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

import static org.junit.Assert.assertEquals;

public class MostPopularTest {

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

        mockWebServer.enqueue(new MockResponse().setBody(loadJSONFromResources("MostPopular.json")));

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
