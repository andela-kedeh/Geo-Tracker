package com.checkpoint4.wecking.standingstillapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
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
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.model.Location;
import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CallBack;
import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CustomTimePickerDialog;
import com.checkpoint4.wecking.standingstillapp.util.Formater;
import com.checkpoint4.wecking.standingstillapp.adapter.ListLocationAdapter;
import com.checkpoint4.wecking.standingstillapp.util.NetworkUtil;
import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.SelectDateFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private GoogleMap mMap;
    private String TAG = "MapsActivity";
    private boolean timerHasStarted = false;
    private ImageView playAndStop;
    private ImageView clock;
    private TextView dateSet;
    private DrawerLayout mDrawerLayout;
    private ImageView headline, settings;
    private Location location;
    private String viewMapDate = Formater.formatDate(new Date().getTime());
    private SupportMapFragment mapFragment;
    private ListView locationList;
    private ListLocationAdapter listLocation;
    private Cursor cursor;
    private NavigationView navigationView;
    private ImageView datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVersionLayout();
        initialize();
        setOnClickListenner();
        loadListItem(true);
        setTrackingTime();
    }


    private void initialize(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        playAndStop = (ImageView) findViewById(R.id.play);
        clock = (ImageView) findViewById(R.id.clock);
        datePicker = (ImageView) findViewById(R.id.date_picker);
        dateSet = (TextView) findViewById(R.id.date);
        dateSet.setText(Formater.formatDate(new Date().getTime()).toString());
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        headline = (ImageView) findViewById(R.id.view_headline);
        navigationView = (NavigationView) findViewById(R.id.nav_items);
        setupDrawerContent(navigationView);
        locationList = (ListView) findViewById(R.id.listView);
        settings = (ImageView) findViewById(R.id.settings);
    }

    private void setOnClickListenner(){
        playAndStop.setOnClickListener(this);
        clock.setOnClickListener(this);
        datePicker.setOnClickListener(this);
        mapFragment.getMapAsync(this);
        headline.setOnClickListener(this);
        location = new Location(this);
        settings.setOnClickListener(this);
    }

    private void loadListItem(boolean isList) {
        Cursor locationByDate = location.getLocationDataByDate(viewMapDate);
        listLocation = new ListLocationAdapter(this, locationByDate, isList);
        locationList.setAdapter(listLocation);
    }

    private void setVersionLayout(){
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
            AppBarLayout toolbar = (AppBarLayout) findViewById(R.id.appbar);
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)toolbar.getLayoutParams();
            params.setMargins(0, 70, 0, 0);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadMap();
    }

    private void loadMap(){
        Cursor cursor = location.getLocationDataByDate(viewMapDate);
        // Cycle through and display every row of data
        CameraUpdate cameraUpdate;
        mMap.clear();
        if(cursor.moveToFirst()){
            do{
                Double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_lat")));
                Double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_long")));
                LatLng sydney = new LatLng(longitude, latitude);
                String address = " ";
                try{
                    address = Constants.getLocationAddress(longitude, latitude, 0, this) +
                            " " + Constants.getLocationAddress(longitude, latitude, 1, this) +
                            " " +Constants.getLocationAddress(longitude, latitude, 2, this);
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(this, "Connect to the Internet to See Street Name", Toast.LENGTH_LONG);
                }
                address = address + " Spent " + cursor.getString(cursor.getColumnIndex("standing_time")) + "min " +
                        " From " + cursor.getString(cursor.getColumnIndex("start_time")) +
                        " To " + cursor.getString(cursor.getColumnIndex("stop_time")) +
                        " Set Time " + cursor.getString(cursor.getColumnIndex("set_record_time"));
                MarkerOptions marker = new MarkerOptions().position(sydney).title(address);
                mMap.addMarker(marker);
                cameraUpdate = CameraUpdateFactory.newLatLngZoom(sydney, 8);
                mMap.moveCamera(cameraUpdate);
            }while (cursor.moveToNext());
            mMap.animateCamera(cameraUpdate);
        }else{
            Toast.makeText(this, "For " + viewMapDate + " there is no location history", Toast.LENGTH_SHORT).show();
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
            case R.id.settings:
                showMenu(v);
                break;
        }
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.settings);
        popup.setOnMenuItemClickListener(this);

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

    private void selectDate() {
        DialogFragment newFragment = new SelectDateFragment(new CallBack() {
            @Override
            public void onFinished(int dd, int mm, int yy) {
                String date = String.format("%d/%d/%d", dd, mm+1, yy);
                viewMapDate = date;
                loadMap();
                dateSet.setText(date);
                loadListItem(true);
            }
        });
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    private void setTrackingTime() {
        CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(MapsActivity.this, timeSetListener,
                Calendar.getInstance().get(Calendar.HOUR),
                CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) +
                        CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
        timePickerDialog.setTitle("Set Time To Record Location");
        timePickerDialog.show();
    }

    private void startTracking() {
        if (timerHasStarted && StandingService.isRunning) {
            stopService(new Intent(getBaseContext(), StandingService.class));
            timerHasStarted = false;
            playAndStop.setImageResource(R.drawable.play);
        } else {
            startService(new Intent(getBaseContext(), StandingService.class));
            timerHasStarted = true;
            playAndStop.setImageResource(R.drawable.stop);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_time:
                setTrackingTime();
                return true;
            case R.id.pick_date:
                selectDate();
                return true;
            default:
                return false;
        }
    }

}

