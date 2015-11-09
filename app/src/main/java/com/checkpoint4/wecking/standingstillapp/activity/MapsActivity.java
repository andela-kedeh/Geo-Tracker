package com.checkpoint4.wecking.standingstillapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.checkpoint4.wecking.standingstillapp.LocationServices.Constants;
import com.checkpoint4.wecking.standingstillapp.LocationServices.StandingService;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CircleTimerView;
import com.checkpoint4.wecking.standingstillapp.adapter.LocationByDateAdapter;
import com.checkpoint4.wecking.standingstillapp.DataModel.Location;

import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements  View.OnClickListener, CircleTimerView.CircleTimerListener{

    private String TAG = "MapsActivity";
    private DrawerLayout mDrawerLayout;
    private ImageView headline;
    private TextView stracking_status;
    private Location location;
    private LinearLayout Start_tracking;
    private NavigationView navigationView;
    private CircleTimerView circularTimerView;
    private RecyclerView recyclerView;
    private TextView how_to_use;
    private ImageView start_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVersionLayout();
        initialize();
        setOnClickListenner();
        loadLocationList(false);
    }


    private void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        headline = (ImageView) findViewById(R.id.view_headline);
        navigationView = (NavigationView) findViewById(R.id.nav_items);
        setupDrawerContent(navigationView);
        circularTimerView = (CircleTimerView) findViewById(R.id.circularTimerView);
        recyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        Start_tracking = (LinearLayout) findViewById(R.id.Start_tracking);
        stracking_status = (TextView) findViewById(R.id.stracking_status);
        how_to_use = (TextView) findViewById(R.id.how_to_use);
        start_icon = (ImageView) findViewById(R.id.start_icon);
    }

    private void setOnClickListenner(){
        headline.setOnClickListener(this);
        location = new Location(this);
        circularTimerView.setOnClickListener(this);
        Constants.circularTimerView = circularTimerView;
        Start_tracking.setOnClickListener(this);
        stracking_status.setOnClickListener(this);
        how_to_use.setOnClickListener(this);
        start_icon.setOnClickListener(this);
    }

    private void loadLocationList(boolean isDate) {
        List locationData = null;
        if(isDate)
            locationData = location.getLocationDataByDate();
        else {
            locationData = location.getLocationData();
        }
        LocationByDateAdapter adapter = new LocationByDateAdapter(MapsActivity.this, locationData);
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setVersionLayout(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)toolbar.getLayoutParams();
            params.setMargins(0, 70, 0, 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Start_tracking:
                startTracking();
                break;
            case R.id.view_headline:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                showMenu(v);
                break;
            case R.id.how_to_use:
                showHowToUse();
                break;
            case R.id.start_icon:
                startTracking();
                break;
            case R.id.stracking_status:
                startTracking();
                break;
        }
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.settings);
        // Force icons to show
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }
        popup.show();
    }


    private void startTracking() {
        try {
            if (StandingService.isRunning) {
                pause();
            } else {
                start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void pause(){
        stopService(new Intent(getBaseContext(), StandingService.class));
        stracking_status.setText("Start Tracking");
        Start_tracking.setBackground(getResources().getDrawable(R.drawable.start_button_background));
        circularTimerView.pauseTimer();
    }

    public void start(){
        startService(new Intent(getBaseContext(), StandingService.class));
        stracking_status.setText("Stop Tracking");
        Start_tracking.setBackground(getResources().getDrawable(R.drawable.stop_button_background));
        circularTimerView.startTimer();
    }

    @Override
    public void onBackPressed() {
        mDrawerLayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        switch (menuItem.getItemId()) {
            case R.id.byDay:
                loadLocationList(true);
                break;
            case R.id.byLocation:
                loadLocationList(false);
                break;
        }

    }

    @Override
    public void onTimerStop() {
    }

    @Override
    public void onTimerStart(int time) {

    }

    @Override
    public void onTimerPause(int time) {

    }

    private void showHowToUse() {
        final LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.how_to_use, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).setTitle("HOW IT WORKS");

        builder.show();
    }

}
