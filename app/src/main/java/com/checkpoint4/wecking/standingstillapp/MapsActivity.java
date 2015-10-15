package com.checkpoint4.wecking.standingstillapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.checkpoint4.wecking.standingstillapp.Location.StandingService;
import com.checkpoint4.wecking.standingstillapp.util.CustomTimePickerDialog;
import com.checkpoint4.wecking.standingstillapp.util.SelectDateFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private String TAG = "MapsActivity";
    private boolean timerHasStarted = false;
    private ImageView playAndStop;
    private ImageView clock;
    private TextView dateSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initialize();
    }

    public void initialize(){
        playAndStop = (ImageView) findViewById(R.id.play);
        playAndStop.setOnClickListener(this);
        clock = (ImageView) findViewById(R.id.clock);
        clock.setOnClickListener(this);
        ImageView datePicker = (ImageView) findViewById(R.id.date_picker);
        datePicker.setOnClickListener(this);
        dateSet = (TextView) findViewById(R.id.date);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and TriggerListener the camera
        LatLng sydney = new LatLng(6.5023094, 3.377748);
        LatLng sydney1 = new LatLng(6.5022912, 3.377748);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marking places you visit"));
        mMap.addMarker(new MarkerOptions().position(sydney1).title("Marking places you visit"));
        CameraUpdate updateFactory = CameraUpdateFactory.newLatLngZoom(sydney, 16);
        mMap.moveCamera(updateFactory);
        CameraUpdate updateFactory1 = CameraUpdateFactory.newLatLngZoom(sydney1, 16);
//        mMap.moveCamera(updateFactory1);
        mMap.animateCamera(updateFactory);


    }

    private CustomTimePickerDialog.OnTimeSetListener timeSetListener = new CustomTimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.v(TAG, String.format("%d : %d", hourOfDay, minute));
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if (!timerHasStarted) {
                    startService(new Intent(getBaseContext(), StandingService.class));
                    timerHasStarted = true;
                    playAndStop.setImageResource(R.drawable.stop);
                } else {
                    stopService(new Intent(getBaseContext(), StandingService.class));
                    timerHasStarted = false;
                    playAndStop.setImageResource(R.drawable.play);
                }
                break;
            case R.id.clock:
                CustomTimePickerDialog timePickerDialog = new CustomTimePickerDialog(MapsActivity.this, timeSetListener,
                        Calendar.getInstance().get(Calendar.HOUR),
                        CustomTimePickerDialog.getRoundedMinute(Calendar.getInstance().get(Calendar.MINUTE) +
                                CustomTimePickerDialog.TIME_PICKER_INTERVAL), true);
                timePickerDialog.setTitle("Set hours and minutes");
                timePickerDialog.show();
                break;
            case R.id.date_picker:
                DialogFragment newFragment = new SelectDateFragment(new SelectDateFragment.CallBack() {
                    @Override
                    public void onFinished(int dd, int mm, int yy) {
                        String date = String.format("%d/%d/%d", dd, mm, yy);
                        dateSet.setText(date);
                        Log.v(TAG, date);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "DatePicker");
                break;
        }
    }
}

