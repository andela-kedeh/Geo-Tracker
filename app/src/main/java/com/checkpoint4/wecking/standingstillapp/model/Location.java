package com.checkpoint4.wecking.standingstillapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.checkpoint4.wecking.standingstillapp.data.StandingContract;

import java.util.Date;

/**
 * Created by andela on 10/13/15.
 */
public class Location{

    private Context mContext;
    private ContentValues values;
    private Uri uri;


    public Location(Context context) {
        mContext = context;
    }

    public void insertLocation(long timeSpentInMinute, long startTime, long endTime, int timeSet, String street, String state, String country, Double latitude, Double longitude){
        ContentValues values = new ContentValues();
        long time = new Date().getTime();

        values.put(StandingContract.StandingEntry.COLUMN_DATE, time);
        values.put(StandingContract.StandingEntry.COLUMN_START_TIME, startTime);
        values.put(StandingContract.StandingEntry.COLUMN_STOP_TIME, endTime);
        values.put(StandingContract.StandingEntry.COLUMN_STANDING_TIME, timeSpentInMinute);
        values.put(StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME, timeSet);

        values.put(StandingContract.StandingEntry.COLUMN_STREET_NAME, street);
        values.put(StandingContract.StandingEntry.COLUMN_STATE_NAME, state);
        values.put(StandingContract.StandingEntry.COLUMN_COUNTRY_NAME, country);
        values.put(StandingContract.StandingEntry.COLUMN_COORD_LAT, latitude);
        values.put(StandingContract.StandingEntry.COLUMN_COORD_LONG, longitude);
        uri = mContext.getContentResolver().insert(StandingContract.StandingEntry.CONTENT_URI, values);
        Log.v("Location", "Saved to db done");
        Toast.makeText(mContext, "New Location Added", Toast.LENGTH_LONG);

    }
}
