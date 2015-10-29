package com.checkpoint4.wecking.standingstillapp.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.checkpoint4.wecking.standingstillapp.Location.Constants;
import com.checkpoint4.wecking.standingstillapp.data.StandingContract;
import com.checkpoint4.wecking.standingstillapp.util.Formater;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wecking on 10/13/15.
 */
public class Location{

    private Context mContext;
    private ContentValues values;
    private Uri uri;
    private ContentResolver resolver;


    public Location(Context context) {
        mContext = context;
        resolver = context.getContentResolver();
    }



    public void insertLocation(long timeSpentInMinute, long startTime, long endTime, int timeSet, Double latitude, Double longitude){
        ContentValues values = new ContentValues();
        long time = new Date().getTime();

        values.put(StandingContract.StandingEntry.COLUMN_DATE, Formater.formatDate(time));
        values.put(StandingContract.StandingEntry.COLUMN_START_TIME, Formater.timeFormater(startTime));
        values.put(StandingContract.StandingEntry.COLUMN_STOP_TIME, Formater.timeFormater(endTime));
        values.put(StandingContract.StandingEntry.COLUMN_STANDING_TIME, timeSpentInMinute);
        values.put(StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME, timeSet);

        values.put(StandingContract.StandingEntry.COLUMN_COORD_LAT, latitude);
        values.put(StandingContract.StandingEntry.COLUMN_COORD_LONG, longitude);
        uri = mContext.getContentResolver().insert(StandingContract.StandingEntry.CONTENT_URI, values);
        Log.v("Location", "Saved to db done");
        Toast.makeText(mContext, "New Location Recorded", Toast.LENGTH_LONG);
    }

    public Cursor getLocationDataByDate(String date){
        // Projection contains the columns we want
        String[] projection = new String[]{"coord_lat", "coord_long", "date",
                "start_time", "stop_time", "standing_time", "set_record_time", "_id"};
        String[] selectionDate = {date};
        // Pass the URL, projection and I'll cover the other options below
        return resolver.query(StandingContract.StandingEntry.CONTENT_URI, projection, "date = ? ", selectionDate, null);
    }

}
