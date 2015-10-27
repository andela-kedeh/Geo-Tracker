package com.checkpoint4.wecking.standingstillapp.Location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * Created by andela on 10/9/15.
 */
public class LocationDetector extends Service {
    private static final String TAG = "Location";

    public String locationProvider = LocationManager.NETWORK_PROVIDER;
    public String locationProvider2 = LocationManager.GPS_PROVIDER;

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

    /**
     * Sends a resultCode and message to the receiver.
     */
    private void deliverResultToReceiver(int resultCode, Location location) {
        Bundle bundle = new Bundle();
        Double latitude = location.getLatitude();
        Double longitude = location.getLongitude();
        bundle.putDouble("longitude", longitude);
        bundle.putDouble("latitude", latitude);
        try{
            if (getLocationAddress(longitude, latitude, 0) != null) {
                bundle.putString("street_name", getLocationAddress(longitude, latitude, 0));
                bundle.putString("state", getLocationAddress(longitude, latitude, Constants.interval));
                bundle.putString("country", getLocationAddress(longitude, latitude, 2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
            Log.i(TAG, "location is found");
            Log.i(TAG, "location is found" + location.getLatitude() + " " + location.getLongitude());
                deliverResultToReceiver(Constants.SUCCESS_RESULT, location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "location status change");
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "location provider enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "location provider disabled");
        }

    }

    public class MyLocationBinder extends Binder {
        LocationDetector getService(){
            return LocationDetector.this;
        }
    }

    private String getLocationAddress(Double longitude, Double latitude, int addressId){
        Geocoder gcd = new Geocoder(this.getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
            Log.v(TAG, addresses.get(0).getAddressLine(addressId).toString());
        } catch (Exception e) {
            Log.v(TAG, "addresses is null");
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(addressId).toString();
    }


}
