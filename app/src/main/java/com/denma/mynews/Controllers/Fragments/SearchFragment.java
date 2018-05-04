package com.denma.mynews.Controllers.Fragments;


import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.denma.mynews.Controllers.Activities.SearchResultActivity;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchArticles;
import com.denma.mynews.Models.ArticleSearchAPI.ArticleSearchResponse;
import com.denma.mynews.R;
import com.denma.mynews.Utils.NYTStream;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class SearchFragment extends Fragment {

    @BindView(R.id.search_query_term_text_input_edit_text)
    EditText searchQueryTerm;
    @BindView(R.id.begin_date_user_choice_text_view)
    TextView beginDateUserChoice;
    @BindView(R.id.end_date_user_choice_text_view)
    TextView endDateUserChoice;
    @BindView(R.id.begin_date_button)
    ImageView beginDateButton;
    @BindView(R.id.end_date_button)
    ImageView endDateButton;
    @BindView(R.id.check_box_arts)
    CheckBox checkBoxArts;
    @BindView(R.id.check_box_politics)
    CheckBox checkBoxPolitics;
    @BindView(R.id.check_box_business)
    CheckBox checkBoxBusiness;
    @BindView(R.id.check_box_sports)
    CheckBox checkBoxSports;
    @BindView(R.id.check_box_culture)
    CheckBox checkBoxCulture;
    @BindView(R.id.check_box_travel)
    CheckBox checkBoxTravel;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;
    private Disposable disposable;

    private String queryTerm = "";
    private String newsDesk = "";
    private String arts = "";
    private String politics = "";
    private String business = "";
    private String sports = "";
    private String culture = "";
    private String travel = "";
    private String beginDate = null;
    private String endDate = null;

    private SharedPreferences mPreferences;
    
    public SearchFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        this.configureDatePicker();
        this.configureListener();
        // Init SharedPreferences using Default wich make it easily recoverable throught activity/fragment
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return view;
    }

    private void configureDatePicker(){
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);

        beginDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });

        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog(view);
            }
        });
    }

    private void DateDialog(final View view){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String sDay = String.valueOf(day);
                String sMonth = String.valueOf(month+1);
                String sYear = String.valueOf(year);

                if (day < 10)
                    sDay = "0" + sDay;
                if (month < 10)
                    sMonth = "0" + sMonth;

                if (view.equals(endDateButton))
                    endDateUserChoice.setText(sDay + "/" + sMonth + "/" + sYear);
                if (view.equals(beginDateButton))
                    beginDateUserChoice.setText(sDay + "/" + sMonth + "/" + sYear);
            }
        };
        DatePickerDialog dpDialog = new DatePickerDialog(getActivity(),R.style.DatePickerTheme, listener, year, month, day);
        dpDialog.show();
    }

    private void configureListener(){
        searchQueryTerm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                queryTerm = searchQueryTerm.getText().toString();
            }
        });

        checkBoxArts.setOnClickListener(new checkBoxListener());
        checkBoxPolitics.setOnClickListener(new checkBoxListener());
        checkBoxBusiness.setOnClickListener(new checkBoxListener());
        checkBoxSports.setOnClickListener(new checkBoxListener());
        checkBoxCulture.setOnClickListener(new checkBoxListener());
        checkBoxTravel.setOnClickListener(new checkBoxListener());
    }

    @OnClick(R.id.search_button)
    public void submit(){
        executeHttpRequestWithRetrofit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // - Execute our Stream
    private void executeHttpRequestWithRetrofit(){
        // - Execute the stream subscribing to Observable defined inside NYTStream
        newsDesk = arts + politics + business + sports + culture + travel;
        if(queryTerm.isEmpty()){
            Toast.makeText(getContext(), "Please choose a query term", Toast.LENGTH_SHORT).show();
        }
        else if(newsDesk == "")
            Toast.makeText(getContext(), "Please choose at least one category", Toast.LENGTH_SHORT).show();
        else {
            if(beginDateUserChoice.getText().toString() != "")
                beginDate = adjustDateForRequest(beginDateUserChoice.getText().toString());
            if(endDateUserChoice.getText().toString() != "")
                endDate =  adjustDateForRequest(endDateUserChoice.getText().toString());

            this.disposable = NYTStream.streamFetchArticleSearch(queryTerm,"news_desk: (" + newsDesk + ")", beginDate , endDate).subscribeWith(new DisposableObserver<ArticleSearchResponse>() {
                @Override
                public void onNext(ArticleSearchResponse response) {
                    Log.e("TAG","On Next");
                    // - Update UI with response
                    testingResponse(response);
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("TAG","On Error "+ e.getMessage());
                    // - Signal that there is probably no internet connection
                    Toast.makeText(getContext(), "Please make sure you have access to internet !", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onComplete() {
                    Log.e("TAG","On Complete !!");
                }
            });
        }
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void testingResponse(ArticleSearchResponse resp)
    {
        if (resp.getResult().getArticleSearchArticles().isEmpty()){
            // - Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.DialogTheme);
            // - Add the buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            // - Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title);

            // - Get the AlertDialog from create() and display it
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else {

            mPreferences.edit().putString("query", queryTerm).apply();
            mPreferences.edit().putString("newsDesk", "news_desk: (" + newsDesk + ")").apply();
            mPreferences.edit().putString("beginDate", beginDate).apply();
            mPreferences.edit().putString("endDate", endDate).apply();

            List<ArticleSearchArticles> articles = resp.getResult().getArticleSearchArticles();
            String listArticlesSerializedToJson = new Gson().toJson(articles);
            mPreferences.edit().putString("listArticles", listArticlesSerializedToJson).apply();

            Intent resultIntent = new Intent(getActivity(), SearchResultActivity.class);
            startActivity(resultIntent);
        }
    }

    private String adjustDateForRequest(String date){
        String rDay = date.substring(0, 2);
        String rMonth = date.substring(3, 5);
        String rYear = date.substring(6, 10);
        String requestDate = rYear + rMonth + rDay;
        return requestDate;
    }

    class checkBoxListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.check_box_arts:
                    if(checkBoxArts.isChecked())
                        arts = "\"Arts\"";
                    else
                        arts = "";
                    break;
                case R.id.check_box_politics:
                    if(checkBoxPolitics.isChecked())
                        politics = "\"Politics\"";
                    else
                        politics = "";
                    break;
                case R.id.check_box_business:
                    if(checkBoxBusiness.isChecked())
                        business = "\"Business\"";
                    else
                        business = "";
                    break;
                case R.id.check_box_sports:
                    if(checkBoxSports.isChecked())
                        sports = "\"Sports\"";
                    else
                        sports = "";
                    break;
                case R.id.check_box_culture:
                    if(checkBoxCulture.isChecked())
                        culture = "\"Culture\"";
                    else
                        culture = "";
                    break;
                case R.id.check_box_travel:
                    if(checkBoxTravel.isChecked())
                        travel = "\"Travel\"";
                    else
                        travel = "";
                    break;
            }
        }
    }
}
