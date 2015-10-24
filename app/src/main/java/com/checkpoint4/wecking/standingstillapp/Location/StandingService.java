package com.checkpoint4.wecking.standingstillapp.Location;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.checkpoint4.wecking.standingstillapp.model.Location;

import java.util.Date;

/**
 * Created by andela on 10/8/15.
 */
public class StandingService extends Service  implements SensorEventListener {

    private SensorManager sensorMan;
    private Sensor accelerometer;

    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private boolean state = true;
    private Date startTime;
    private Date endTime;
    protected static final String TAG = "StandingService";
    private AddressResultReceiver mResultReceiver;
    public static boolean isRunning = false;

    int timeSpentInMinute;

    LocationDetector locationDetector;
    boolean isBound = false;
    Intent intent1;
    private static boolean locationNeeded = false;



    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initialize();
        Toast.makeText(StandingService.this, "Tracking Started", Toast.LENGTH_SHORT).show();
        isRunning = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        sensorMan.unregisterListener(this);
        Toast.makeText(StandingService.this, "Tracking Stoped", Toast.LENGTH_SHORT).show();
        isRunning = false;
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            try {

                if (getGravity(event) > 6){
                    state = true;
                    if(timeSpent() >= Constants.interval && mResultReceiver != null){
                        locationNeeded = true;
                        startIntentService();
                    }
                } else{
                    // do something
                    if(state) {
                        startTime = new Date();
                    }
                    state = false;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Receiver registered with this activity to get the response from FetchAddressIntentService.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

    /**
     * Receiver for data sent from FetchAddressIntentService.
     */
    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT && locationNeeded) {
                Double longitude = resultData.getDouble("longitude");
                Double latitude = resultData.getDouble("latitude");
                String street = resultData.getString("street_name");
                String state = resultData.getString("state");
                String country = resultData.getString("country");
                new Location(getBaseContext().getApplicationContext()).insertLocation(timeSpentInMinute, startTime.getTime(), endTime.getTime(), Constants.interval, street, state, country, latitude, longitude);
                locationNeeded = false;
                locationDetector.stopSelf();
            }
        }
    }

    private ServiceConnection locationConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationDetector.MyLocationBinder myLocationBinder = (LocationDetector.MyLocationBinder) service;
            locationDetector = myLocationBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void initialize() {
        sensorMan = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        sensorMan.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_UI);
        mResultReceiver = new AddressResultReceiver(new Handler());
    }

    private float getGravity(SensorEvent event){
        mGravity = event.values.clone();
        float x = mGravity[0];
        float y = mGravity[1];
        float z = mGravity[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float)Math.sqrt(x * x + y * y + z * z);
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta;

        return mAccel;
    }

    private void startIntentService() {
        final Context appContext = this.getApplicationContext();
        intent1 = new Intent(appContext, LocationDetector.class);
        intent1.putExtra(Constants.RECEIVER, mResultReceiver);
        appContext.bindService(intent1, locationConnection, 0);
        appContext.startService(intent1);
    }

    private int timeSpent() {
        endTime = new Date();
        long result = endTime.getTime() - startTime.getTime();
        timeSpentInMinute = (int)(result/1000)/60;
        return timeSpentInMinute;
    }

}
