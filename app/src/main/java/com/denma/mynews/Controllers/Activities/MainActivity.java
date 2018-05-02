package com.denma.mynews.Controllers.Activities;

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

import com.denma.mynews.Views.PageAdapter;
import com.denma.mynews.Controllers.Fragments.MostPopularFragment;
import com.denma.mynews.Controllers.Fragments.SportFragment;
import com.denma.mynews.Controllers.Fragments.TopStoriesFragment;
import com.denma.mynews.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //FOR DESIGN
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

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
        // 1 - Get ViewPager from layout
        pager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), this));
        // 1 - Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
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

    // 5 - show first fragment
    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_viewpager);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            this.showFragment(FRAGMENT_TOPSTORIES);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
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
                return true;
            case R.id.activity_main_more_about:
                // - Launch about
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
                launchSearchActivity();
            case R.id.activity_main_drawer_notifications:
                // - Launch NotificationsActivity
                launchNotificationActivity();
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
}