package com.checkpoint4.wecking.standingstillapp.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.HelpDialog;
import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ActivityHolder;
import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ObjectClass;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.slider.ViewPagerAdapter;

public class MainActivity extends FragmentActivity implements  View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private DrawerLayout mDrawerLayout;
    private ImageView headline;
    private NavigationView navigationView;

    private ViewPager _mViewPager;
    private ViewPagerAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setUpView();
    }

    private void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        headline = (ImageView) findViewById(R.id.view_headline);
        navigationView = (NavigationView) findViewById(R.id.nav_items);
        navigationView.setNavigationItemSelectedListener(this);
        headline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_headline:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawers();
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void startLocationListing(boolean isDate){
        Intent intent = new Intent(MainActivity.this, ListLocation.class);
        intent.putExtra("isDate", isDate);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        ActivityHolder.setActivity(null);
        super.onPause();
    }

    @Override
    protected void onResume() {
        ActivityHolder.setActivity(this);
        super.onResume();
    }

    private void setUpView(){
        _mViewPager = (ViewPager) findViewById(R.id.viewPager);
        _adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
        _mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.byDay:
                startLocationListing(true);
                break;
            case R.id.byLocation:
                startLocationListing(false);
                break;
            case R.id.help:
                mDrawerLayout.closeDrawers();
                HelpDialog object = (HelpDialog) new ObjectClass().getObject("HelpDialog");
                object.showHelpToUse();
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        switch(position){
            case 0:
                findViewById(R.id.first_tab).setVisibility(View.VISIBLE);
                findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
                break;

            case 1:
                findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
                findViewById(R.id.second_tab).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
