package com.checkpoint4.wecking.standingstillapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.checkpoint4.wecking.standingstillapp.LocationServices.Constants;
import com.checkpoint4.wecking.standingstillapp.LocationServices.StandingService;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CircleTimerView;

import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private ImageView headline;
    private TextView stracking_status;
    private LinearLayout Start_tracking;
    private NavigationView navigationView;
    private CircleTimerView circularTimerView;
    private TextView how_to_use;
    private ImageView start_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setOnClickListenner();
    }

    private void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        headline = (ImageView) findViewById(R.id.view_headline);
        navigationView = (NavigationView) findViewById(R.id.nav_items);
        setupDrawerContent(navigationView);
        circularTimerView = (CircleTimerView) findViewById(R.id.circularTimerView);
        Start_tracking = (LinearLayout) findViewById(R.id.Start_tracking);
        stracking_status = (TextView) findViewById(R.id.stracking_status);
        how_to_use = (TextView) findViewById(R.id.how_to_use);
        start_icon = (ImageView) findViewById(R.id.start_icon);
    }

    private void setOnClickListenner(){
        headline.setOnClickListener(this);
        circularTimerView.setOnClickListener(this);
        Constants.circularTimerView = circularTimerView;
        Start_tracking.setOnClickListener(this);
        stracking_status.setOnClickListener(this);
        how_to_use.setOnClickListener(this);
        start_icon.setOnClickListener(this);
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
            case R.id.how_to_use:
                showHelpToUse();
                break;
            case R.id.start_icon:
                startTracking();
                break;
            case R.id.stracking_status:
                startTracking();
                break;
        }
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
                Intent i = new Intent(MainActivity.this, ListLocation.class);
                i.putExtra("isDate", true);
                startActivity(i);
                break;
            case R.id.byLocation:
                Intent intent = new Intent(MainActivity.this, ListLocation.class);
                intent.putExtra("isDate", false);
                startActivity(intent);
                break;
            case R.id.help:
                mDrawerLayout.closeDrawers();
                showHelpToUse();
                break;
        }

    }

    private void showHelpToUse() {
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
