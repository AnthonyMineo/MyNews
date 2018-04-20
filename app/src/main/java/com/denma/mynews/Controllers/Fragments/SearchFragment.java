package com.denma.mynews.Controllers.Fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.denma.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.search_button)
    Button searchButton;

    private int day;
    private int month;
    private int year;
    private Calendar calendar;

    public SearchFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        this.configureDatePicker();

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

}
