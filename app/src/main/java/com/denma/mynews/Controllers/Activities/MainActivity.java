package com.denma.mynews.Controllers.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.denma.mynews.Views.PageAdapter;
import com.denma.mynews.Controllers.Fragments.MostPopularFragment;
import com.denma.mynews.Controllers.Fragments.SportFragment;
import com.denma.mynews.Controllers.Fragments.TopStoriesFragment;
import com.denma.mynews.R;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TabLayout tabs;

    //FOR FRAGMENTS
    // - Declare fragment handled by Navigation Drawer
    private TopStoriesFragment topStoriesFragment;
    private MostPopularFragment mostPopularFragment;
    private SportFragment sportFragment;

    private ViewPager pager;

    //FOR DATAS
    // - Identify each fragment with a number
    private static final int FRAGMENT_TOPSTORIES = 0;
    private static final int FRAGMENT_MOSTPOPULAR = 1;
    private static final int FRAGMENT_SPORTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // - Configure each component
        this.configureToolBar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureViewPagerAndTabs();
        this.showFirstFragment();

    }

    // --------------
    // CONFIGURATION
    // --------------

    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // 4 - Configure ViewPager and TabLayout
    private void configureViewPagerAndTabs() {
        // - Get ViewPager from layout
        pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        // - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));
        // - Get TabLayout from layout
        tabs = (TabLayout)findViewById(R.id.activity_main_tabs);
        // - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeNavigationViewItemSelected(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 5 - Show first fragment
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_viewpager);
        if (visibleFragment == null){
            // - Show News Fragment
            this.showFragment(FRAGMENT_TOPSTORIES);
            // - Mark as selected the menu item corresponding to NewsFragment
            navigationView.setCheckedItem(R.id.activity_main_drawer_top_stories);
        }
    }

    // --------------
    // MENU
    // --------------

    public boolean onCreateOptionsMenu(Menu menu) {
        // - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.activity_main_menu_tools, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_search:
                // - Launch SearchActivity
                launchSearchActivity();
                return true;
            case R.id.activity_main_more_notifications:
                // - Launch NotificationsActivity
                launchNotificationActivity();
                return true;
            case R.id.activity_main_more_help:
                // - Launch help
                launchHelp();
                return true;
            case R.id.activity_main_more_about:
                // - Launch About AlertDialog
                launchAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        // - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_top_stories :
                this.showFragment(FRAGMENT_TOPSTORIES);
                break;
            case R.id.activity_main_drawer_most_popular:
                this.showFragment(FRAGMENT_MOSTPOPULAR);
                break;
            case R.id.activity_main_drawer_sports:
                this.showFragment(FRAGMENT_SPORTS);
                break;
            case R.id.activity_main_drawer_search:
                // - Launch SearchActivity
                this.launchSearchActivity();
                break;
            case R.id.activity_main_drawer_notifications:
                // - Launch NotificationsActivity
                this.launchNotificationActivity();
                break;
            case R.id.activity_main_drawer_help:
                // - Launch Help
                this.launchHelp();
                break;
            case R.id.activity_main_drawer_about:
                // - Launch About AlertDialog
                this.launchAbout();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // - Handle NavigationView checked item update, bind with TabLayout
    private void changeNavigationViewItemSelected(int i){
        int id = i;
        switch (i){
            case 0 :
                navigationView.setCheckedItem(R.id.activity_main_drawer_top_stories);
                break;
            case 1 :
                navigationView.setCheckedItem(R.id.activity_main_drawer_most_popular);
                break;
            case 2:
                navigationView.setCheckedItem(R.id.activity_main_drawer_sports);
                break;
            default:
                break;
        }
    }

    // --------------
    // FRAGMENT
    // --------------

    // - Show fragment according to an Identifier
    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_TOPSTORIES :
                this.showTopStoriesFragment();
                break;
            case FRAGMENT_MOSTPOPULAR:
                this.showMostPopularFragment();
                break;
            case FRAGMENT_SPORTS:
                this.showSportsFragment();
                break;
            default:
                break;
        }
    }

    private void showTopStoriesFragment(){
        if (this.topStoriesFragment == null) this.topStoriesFragment = TopStoriesFragment.newInstance();
        pager.setCurrentItem(FRAGMENT_TOPSTORIES);
    }

    private void showMostPopularFragment(){
        if (this.mostPopularFragment == null) this.mostPopularFragment = MostPopularFragment.newInstance();
        pager.setCurrentItem(FRAGMENT_MOSTPOPULAR);
    }

    private void showSportsFragment(){
        if (this.sportFragment == null) this.sportFragment = SportFragment.newInstance();
        pager.setCurrentItem(FRAGMENT_SPORTS);
    }

    // --------------
    // Activity
    // --------------

    private void launchSearchActivity(){
        Intent searchActivityIntent = new Intent(MainActivity.this, SearchActivity.class);
        this.startActivity(searchActivityIntent);
    }

    private void launchNotificationActivity(){
        Intent notifActivityIntent = new Intent(MainActivity.this, NotificationActivity.class);
        this.startActivity(notifActivityIntent);
    }

    // --------------
    // Others
    // --------------


    private void launchHelp(){
        // - Create ShowcaseView from com.github.amlcurran.showcaseview:library:5.4.3 library

        final ShowcaseView.Builder showBuilder1 = new ShowcaseView.Builder(this);
        final ShowcaseView.Builder showBuilder2 = new ShowcaseView.Builder(this);

        showBuilder1.setTarget(new ViewTarget(R.id.activity_main_tabs, this))
                    .setContentText(R.string.category_help)
                    .setStyle(R.style.CustomShowcaseTheme1)
                    .build()
                    .show();

        showBuilder2.setTarget(new ViewTarget(R.id.menu_activity_main_search, this))
                .setContentText(R.string.tools_help)
                .setStyle(R.style.CustomShowcaseTheme2);

        showBuilder1.setShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                showBuilder2.build().show();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {}

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) { }

            @Override
            public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) { }
        });

        showBuilder2.setShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                changeNavigationViewItemSelected(tabs.getSelectedTabPosition());
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) { }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) { }

            @Override
            public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {}
        });
    }

    private void launchAbout(){
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.poweredby_nytimes_200b);

        // - Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogTheme);


        // - Add the buttons
        builder.setPositiveButton(R.string.button_2_help, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                changeNavigationViewItemSelected(tabs.getSelectedTabPosition());
            }
        });

        // - Chain together various setter methods to set the dialog characteristics
        builder.setView(image)
                .setMessage(R.string.about_message)
                .setTitle(R.string.about_title);

        // - Get the AlertDialog from create() and display it
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}