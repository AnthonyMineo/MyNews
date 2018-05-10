package com.denma.mynews.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.denma.mynews.Controllers.Activities.MainActivity;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchArticles;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.R;

import java.util.Calendar;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AlarmReceiver extends BroadcastReceiver {

    private String beginDate;
    private String queryTerm;
    private String newsDesk;
    private int todayNewsSize;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private Disposable disposable;
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.queryTerm = intent.getStringExtra("queryTerm");
        this.newsDesk = intent.getStringExtra("newsDesk2");

        this.calendar = Calendar.getInstance();
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);

        String sDay = String.valueOf(day);
        String sMonth = String.valueOf(month+1);
        String sYear = String.valueOf(year);

        if (day < 10)
            sDay = "0" + sDay;
        if (month < 10)
            sMonth = "0" + sMonth;

        this.beginDate = sYear + sMonth + sDay;
        executeHttpRequestWithRetrofit();
    }

    // - Execute our Stream
    private void executeHttpRequestWithRetrofit() {
        // - Execute the stream subscribing to Observable defined inside NYTStream
        this.disposable = NYTStream.streamFetchArticleSearch(this.queryTerm, "news_desk: (" + this.newsDesk + ")", this.beginDate, null).subscribeWith(new DisposableObserver<ArticleSearchResponse>() {
            @Override
            public void onNext(ArticleSearchResponse response) {
                Log.e("TAG", "On Next");
                // - testing if there is news since yesterday
                testingNews(response);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On Complete !!");
            }
        });
    }

    private void testingNews(ArticleSearchResponse response){
        List<ArticleSearchArticles> articles = response.getResult().getArticleSearchArticles();
        this.todayNewsSize = articles.size();
        if(todayNewsSize >= 1){
            sendNotification(1);
            Log.e("Notif", "Send !");
        }
        else {
            sendNotification(2);
            Log.e("Notif", "Send !");
        }
        this.disposable.dispose();
    }

    // Send simple notification withouy any action on click, just informative
    private void sendNotification(int answer) {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        String NOTIFICATION_ID = "channel_id_01";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        if (answer == 1) {
            builder.setContentTitle(context.getResources().getString(R.string.title_notif_1))
                    .setContentText(context.getResources().getString(R.string.text_notif_1))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
        }
        else {
            builder.setContentTitle(context.getResources().getString(R.string.title_notif_2))
                    .setContentText(context.getResources().getString(R.string.text_notif_2))
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
        }

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(1, builder.build());
    }
}
