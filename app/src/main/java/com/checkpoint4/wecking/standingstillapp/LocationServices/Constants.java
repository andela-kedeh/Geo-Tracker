package com.checkpoint4.wecking.standingstillapp.LocationServices;

/**
 * Created by andela on 10/11/15.
 */

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CircleTimerView;

import java.util.List;
import java.util.Locale;

/**
 * Constant values reused in this sample.
 */
public final class Constants {
    public static final int SUCCESS_RESULT = 0;

    public static final String PACKAGE_NAME =
            "com.checkpoint4.wecking.myapplication.Location";

    public static final String RECEIVER = PACKAGE_NAME + ".StandingService";

    public static CircleTimerView circularTimerView;

    public static String getLocationAddress(Double longitude, Double latitude, int addressId, Context context){
        Geocoder gcd = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addresses.get(0).getAddressLine(addressId).toString();
    }



}