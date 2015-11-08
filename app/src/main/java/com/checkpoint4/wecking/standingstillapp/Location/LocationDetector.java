package com.checkpoint4.wecking.standingstillapp.Location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by wecking on 10/9/15.
 */
public class LocationDetector extends Service {
    private static final String TAG = "Location";
    private String locationProvider = LocationManager.NETWORK_PROVIDER;
    private String locationProvider2 = LocationManager.GPS_PROVIDER;

    LocationManager mlocManager;
    LocationListener mlocListener;

    protected ResultReceiver mReceiver;

    private final IBinder locationBinder = new MyLocationBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return locationBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Location Dectetor started");
        try {
            mReceiver = intent.getParcelableExtra(Constants.RECEIVER);
        }catch(NullPointerException ne){
            ne.printStackTrace();
        }
            mlocManager.requestLocationUpdates(locationProvider, 0, 0, mlocListener);
            mlocManager.requestLocationUpdates(locationProvider2, 0, 0, mlocListener);
            Log.e(TAG, "requesting location updates");

        return super.onStartCommand(intent, flags, startId);
    }

    private void deliverResultToReceiver(int resultCode, Location location) {
        Bundle bundle = new Bundle();
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("latitude", latitude);
        // Check if receiver was properly registered.
        if (mReceiver != null ){
            mReceiver.send(resultCode, bundle);
        }else {
            Log.wtf(TAG, "No receiver received. There is nowhere to send the results.");
        }
    }

    private class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
                deliverResultToReceiver(Constants.SUCCESS_RESULT, location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

    }

    public class MyLocationBinder extends Binder {
        LocationDetector getService(){
            return LocationDetector.this;
        }
    }

}
