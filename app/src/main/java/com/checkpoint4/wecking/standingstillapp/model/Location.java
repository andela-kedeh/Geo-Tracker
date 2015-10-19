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
 * Created by andela on 10/13/15.
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



    public void insertLocation(long timeSpentInMinute, long startTime, long endTime, int timeSet, String street, String state, String country, Double latitude, Double longitude){
        ContentValues values = new ContentValues();
        long time = new Date().getTime();

        values.put(StandingContract.StandingEntry.COLUMN_DATE, Formater.formatDate(time));
        values.put(StandingContract.StandingEntry.COLUMN_START_TIME, Formater.timeFormater(startTime));
        values.put(StandingContract.StandingEntry.COLUMN_STOP_TIME, Formater.timeFormater(endTime));
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

    public Cursor getLocationDataByDate(String date){
        // Projection contains the columns we want
        String[] projection = new String[]{"street", "state", "country", "coord_lat", "coord_long", "date",
                "start_time", "stop_time", "standing_time", "set_record_time"};
        String[] selectionDate = {date};
        // Pass the URL, projection and I'll cover the other options below
        return resolver.query(StandingContract.StandingEntry.CONTENT_URI, projection, "date = ? ", selectionDate, null);
    }

 /*   String date = cursor.getString(cursor.getColumnIndex("date"));
    String start_time = cursor.getString(cursor.getColumnIndex("start_time"));
    String stop_time = cursor.getString(cursor.getColumnIndex("stop_time"));
    String standing_time = cursor.getString(cursor.getColumnIndex("standing_time"));
    String set_record_time = cursor.getString(cursor.getColumnIndex("set_record_time"));


    String id = cursor.getString(cursor.getColumnIndex("street"));
    String state = cursor.getString(cursor.getColumnIndex("state"));
    String coord_lat = cursor.getString(cursor.getColumnIndex("coord_lat"));
    String coord_long = cursor.getString(cursor.getColumnIndex("coord_long"));
    String country = cursor.getString(cursor.getColumnIndex("country"));


    contactList = contactList + id + ", " + state + " " + country + " " + "coord_lat "
            + coord_lat + " coord_long " + coord_long + " Date" + date + " Start time "
            + start_time + " stop_time " + stop_time + " standing_time " + standing_time + " set_record_time " + set_record_time +"\n";
*/
}
