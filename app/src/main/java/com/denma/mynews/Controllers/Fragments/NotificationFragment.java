package com.denma.mynews.Controllers.Fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.denma.mynews.R;
import com.denma.mynews.Utils.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    @BindView(R.id.search_query_term_text_input_edit_text)
    EditText searchQueryTerm;
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
    @BindView(R.id.notications_switch)
    Switch mySwitch;

    private String queryTerm = "";
    private String newsDesk = "";
    private String arts = "";
    private String politics = "";
    private String business = "";
    private String sports = "";
    private String culture = "";
    private String travel = "";

    private PendingIntent pendingIntent;
    private SharedPreferences mPreferences;

    public NotificationFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.configureDataFromPref();
        this.configureListener();
        this.configureAlarmManager();
        return view;
    }

    private void configureDataFromPref(){
        this.queryTerm = mPreferences.getString("queryTerm", "");
        searchQueryTerm.setText(queryTerm);
        this.newsDesk = mPreferences.getString("newsDesk", "");
        if(newsDesk.contains("Politics")) {
            checkBoxPolitics.setChecked(true);
            politics = "\"Politics\"";
        }
        if(newsDesk.contains("Arts")){
            checkBoxArts.setChecked(true);
            arts = "\"Arts\"";
        }
        if(newsDesk.contains("Business")){
            checkBoxBusiness.setChecked(true);
            business = "\"Business\"";
        }
        if(newsDesk.contains("Sports")){
            checkBoxSports.setChecked(true);
            sports = "\"Sports\"";
        }
        if(newsDesk.contains("Culture")){
            checkBoxCulture.setChecked(true);
            culture = "\"Culture\"";
        }
        if(newsDesk.contains("Travel")){
            checkBoxTravel.setChecked(true);
            travel = "\"Travel\"";
        }
        newsDesk = arts + politics + business + sports + culture + travel;
        if(mPreferences.getBoolean("switchChecked", true))
            mySwitch.setChecked(true);
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
                mPreferences.edit().putString("queryTerm", queryTerm).apply();
                mySwitch.setChecked(false);
            }
        });

        checkBoxArts.setOnClickListener(new NotificationFragment.checkBoxListener());
        checkBoxPolitics.setOnClickListener(new NotificationFragment.checkBoxListener());
        checkBoxBusiness.setOnClickListener(new NotificationFragment.checkBoxListener());
        checkBoxSports.setOnClickListener(new NotificationFragment.checkBoxListener());
        checkBoxCulture.setOnClickListener(new NotificationFragment.checkBoxListener());
        checkBoxTravel.setOnClickListener(new NotificationFragment.checkBoxListener());

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    if(queryTerm.isEmpty()){
                        Toast.makeText(getContext(), "Please choose a query term", Toast.LENGTH_SHORT).show();
                        mySwitch.setChecked(false);
                    }
                    else if(newsDesk == "") {
                        Toast.makeText(getContext(), "Please choose at least one category", Toast.LENGTH_SHORT).show();
                        mySwitch.setChecked(false);
                    }
                    else {
                        startAlarm();
                        mPreferences.edit().putBoolean("switchChecked", true).apply();
                    }
                }
                else {
                    stopAlarm();
                    mPreferences.edit().putBoolean("switchChecked", false).apply();
                }
            }
        });
    }

    // - Configuring the AlarmManager
    private void configureAlarmManager(){
        Intent alarmIntent = new Intent(getActivity(), AlarmReceiver.class);
        alarmIntent.putExtra("queryTerm", this.queryTerm);
        alarmIntent.putExtra("newsDesk", this.newsDesk);
        pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    class checkBoxListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.check_box_arts:
                    if (checkBoxArts.isChecked())
                        arts = "\"Arts\"";
                    else
                        arts = "";
                    break;
                case R.id.check_box_politics:
                    if (checkBoxPolitics.isChecked())
                        politics = "\"Politics\"";
                    else
                        politics = "";
                    break;
                case R.id.check_box_business:
                    if (checkBoxBusiness.isChecked())
                        business = "\"Business\"";
                    else
                        business = "";
                    break;
                case R.id.check_box_sports:
                    if (checkBoxSports.isChecked())
                        sports = "\"Sports\"";
                    else
                        sports = "";
                    break;
                case R.id.check_box_culture:
                    if (checkBoxCulture.isChecked())
                        culture = "\"Culture\"";
                    else
                        culture = "";
                    break;
                case R.id.check_box_travel:
                    if (checkBoxTravel.isChecked())
                        travel = "\"Travel\"";
                    else
                        travel = "";
                    break;
            }
            mySwitch.setChecked(false);
            newsDesk = arts + politics + business + sports + culture + travel;
            mPreferences.edit().putString("newsDesk", newsDesk).apply();
        }
    }

    // --------------
    // SCHEDULE TASK
    // --------------

    // - Start Alarm
    private void startAlarm() {

        // Define when the alarm will be firing
        long alarmUp = 0;
        Calendar midday = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        midday.set(Calendar.HOUR_OF_DAY, 12);
        midday.set(Calendar.MINUTE, 00);
        midday.set(Calendar.SECOND, 00);

        // Allow us to know if alarm time is past or not -> don't start it if it's past
        if(midday.getTimeInMillis() <= now.getTimeInMillis())
            alarmUp = midday.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
        else
            alarmUp = midday.getTimeInMillis();

        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, alarmUp, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(getActivity(), "Notifications Set !", Toast.LENGTH_SHORT).show();
    }

    // - Stop Alarm
    private void stopAlarm() {
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        Toast.makeText(getActivity(), "Notifications Canceled !", Toast.LENGTH_SHORT).show();
    }
}
