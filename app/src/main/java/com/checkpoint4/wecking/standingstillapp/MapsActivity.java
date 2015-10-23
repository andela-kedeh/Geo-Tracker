package com.checkpoint4.wecking.standingstillapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.checkpoint4.wecking.standingstillapp.Location.Constants;
import com.checkpoint4.wecking.standingstillapp.Location.StandingService;
import com.checkpoint4.wecking.standingstillapp.model.Location;
import com.checkpoint4.wecking.standingstillapp.util.CustomTimePickerDialog;
import com.checkpoint4.wecking.standingstillapp.util.Formater;
import com.checkpoint4.wecking.standingstillapp.util.ListLocation;
import com.checkpoint4.wecking.standingstillapp.util.NetworkUtil;
import com.checkpoint4.wecking.standingstillapp.util.SelectDateFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private String TAG = "MapsActivity";
    private boolean timerHasStarted = false;
    private ImageView playAndStop;
    private ImageView clock;
    private TextView dateSet;
    private DrawerLayout mDrawerLayout;
    private ImageView headline;
    private Location location;
    private String viewMapDate = Formater.formatDate(new Date().getTime());
    private SupportMapFragment mapFragment;
    private ListView locationList;
    private ListLocation listLocation;
    private Cursor cursor;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVersionLayout();
        initialize();
    }


    public void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        playAndStop = (ImageView) findViewById(R.id.play);
        playAndStop.setOnClickListener(this);
        clock = (ImageView) findViewById(R.id.clock);
        clock.setOnClickListener(this);
        ImageView datePicker = (ImageView) findViewById(R.id.date_picker);
        datePicker.setOnClickListener(this);
        dateSet = (TextView) findViewById(R.id.date);
        dateSet.setText(Formater.formatDate(new Date().getTime()).toString());
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        headline = (ImageView) findViewById(R.id.view_headline);
        headline.setOnClickListener(this);
        location = new Location(this);
        navigationView = (NavigationView) findViewById(R.id.nav_items);
        setupDrawerContent(navigationView);
        locationList = (ListView) findViewById(R.id.listView);
        loadListItem(true);
    }

    private void loadListItem(boolean isList) {
        Cursor locationByDate = location.getLocationDataByDate(viewMapDate);
        listLocation = new ListLocation(this, locationByDate, isList);
        locationList.setAdapter(listLocation);
    }

    private void setVersionLayout(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)toolbar.getLayoutParams();
            params.setMargins(0, 70, 0, 0);
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or TriggerListener the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadMap();
      /*  // Add a marker in Sydney and TriggerListener the camera
        LatLng sydney = new LatLng(6.5023094, 3.377748);
        LatLng sydney1 = new LatLng(6.5022912, 3.377748);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marking places you visit"));
        mMap.addMarker(new MarkerOptions().position(sydney1).title("Marking places you visit"));
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(sydney, 16);
        mMap.moveCamera(updateFactory);
        CameraUpdate updateFactory1 = CameraUpdateFactory.newLatLngZoom(sydney1, 16);
        mMap.moveCamera(updateFactory1);*/
//        mMap.animateCamera(updateFactory);
    }

    private void loadMap(){
        Cursor cursor = location.getLocationDataByDate(viewMapDate);
        // Cycle through and display every row of data
        CameraUpdate cameraUpdate;
        if(cursor.moveToFirst()){
            do{
                LatLng sydney = new LatLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_lat"))),
                        Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_long"))));
                String address = cursor.getString(cursor.getColumnIndex("street")) +
                        " " + cursor.getString(cursor.getColumnIndex("state")) +
                        " " +cursor.getString(cursor.getColumnIndex("country")) +
                        " Spent " + cursor.getString(cursor.getColumnIndex("standing_time")) + "min " +
                        " From " + cursor.getString(cursor.getColumnIndex("start_time")) +
                        " To " + cursor.getString(cursor.getColumnIndex("stop_time")) +
                        " Set Time " + cursor.getString(cursor.getColumnIndex("set_record_time"));
                mMap.addMarker(new MarkerOptions().position(sydney).title(address));
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 8);
                mMap.moveCamera(cameraUpdate);
                Log.v(TAG, address);
            }while (cursor.moveToNext());
            mMap.animateCamera(cameraUpdate);

        }
    }

    private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.v(TAG, String.format("%d : %d", hourOfDay, minute));
            Constants.interval = hourOfDay;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                startTracking();
                break;
            case R.id.clock:
                setTrackingTime();
                break;
            case R.id.date_picker:
                selectDate();
                break;
            case R.id.view_headline:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    private void selectDate() {
        DialogFragment newFragment = new SelectDateFragment(new SelectDateFragment.CallBack() {
            @Override
            public void onFinished(int dd, int mm, int yy) {
                String date = String.format("%d/%d/%d", dd, mm+1, yy);
                Log.v(TAG, Formater.formatDate(new Date().getTime()));
                viewMapDate = date;
                loadMap();
                dateSet.setText(date);
                Log.v(TAG, date);
            }
        });
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    private void setTrackingTime() {
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(MapsActivity.this, timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR),
                CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) +
                        CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
        timePickerDialog.setTitle("Set Minutes and Seconds");
        timePickerDialog.show();
    }

    private void startTracking() {
        if (timerHasStarted && StandingService.isRunning) {
            stopService(new Intent(getBaseContext(), StandingService.class));
            timerHasStarted = false;
            playAndStop.setImageResource(R.drawable.play);
        } else {
            if(NetworkUtil.getConnectivityStatus(this)) {
                startService(new Intent(getBaseContext(), StandingService.class));
                timerHasStarted = true;
                playAndStop.setImageResource(R.drawable.stop);
            }else{
                Toast.makeText(this, "No Internet Connection Error", Toast.LENGTH_SHORT).show();
            }
        }
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
            case R.id.nav_home:
                loadListItem(true);
                break;
            case R.id.nav_group:
                loadListItem(false);
                break;
        }

    }

}

