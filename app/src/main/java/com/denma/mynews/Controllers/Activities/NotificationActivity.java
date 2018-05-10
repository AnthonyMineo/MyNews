package com.denma.mynews.Controllers.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.denma.mynews.Controllers.Fragments.NotificationFragment;
import com.denma.mynews.R;

public class NotificationActivity extends AppCompatActivity {

    private NotificationFragment notifFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        this.configureToolbar();
        this.configureAndShowNotifFragment();
    }

    private void configureToolbar(){
        //- Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // - Set the toolbar
        setSupportActionBar(toolbar);
        // - Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // - Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureAndShowNotifFragment(){
        notifFragment = (NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.activity_notification_frame_layout);
        if (notifFragment == null) {
            // B - Create new main fragment
            notifFragment = new NotificationFragment();
            // C - Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_notification_frame_layout, notifFragment)
                    .commit();
        }
    }
}
