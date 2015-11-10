package com.checkpoint4.wecking.standingstillapp.DataModel;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.checkpoint4.wecking.standingstillapp.adapter.LocationChildData;
import com.checkpoint4.wecking.standingstillapp.util.Formater;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wecking on 10/13/15.
 */
public class Location{

    private Context mContext;
    private Uri uri;
    private ContentResolver resolver;


    public Location(Context context) {
        mContext = context;
        resolver = context.getContentResolver();
    }

    public void insertLocation(long timeSpentInMinute, long startTime, long endTime, int timeSet, Double latitude, Double longitude, String address){
        ContentValues values = new ContentValues();
        long time = new Date().getTime();

        values.put(StandingContract.StandingEntry.COLUMN_DATE, Formater.formatDate(time));
        values.put(StandingContract.StandingEntry.COLUMN_START_TIME, new SimpleDateFormat("K:mm aa").format(startTime));
        values.put(StandingContract.StandingEntry.COLUMN_STOP_TIME, Formater.timeFormater(endTime));
        values.put(StandingContract.StandingEntry.COLUMN_STANDING_TIME, timeSpentInMinute);
        values.put(StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME, timeSet);

        values.put(StandingContract.StandingEntry.COLUMN_COORD_LAT, latitude);
        values.put(StandingContract.StandingEntry.COLUMN_COORD_LONG, longitude);
        values.put(StandingContract.StandingEntry.COLUMN_ADDRESS, address);
        uri = mContext.getContentResolver().insert(StandingContract.StandingEntry.CONTENT_URI, values);
        Toast.makeText(mContext, "New Location Recorded", Toast.LENGTH_LONG).show();
    }

    public List getLocationDataByDate(){
        StandingDBHelper locationDb = new StandingDBHelper(mContext);
        List<ParentObject> parentObjects = new ArrayList<>();
        ArrayList<String> dates = locationDb.getUniqueDates();
        for (String date : dates) {
            String[] projection = new String[]{"coord_lat", "coord_long", "date",
                    "start_time", "stop_time", "standing_time", "set_record_time", "_id", "address"};
            String[] selectionDate = {date};
            Cursor cursor1 = resolver.query(StandingContract.StandingEntry.CONTENT_URI, projection, "date = ? ", selectionDate, null);
            LocationByDate dateCount = new LocationByDate();
            dateCount.date = date;
            if(cursor1.moveToFirst()) {
                do {
                    dateCount.setChildObjectList(getOneLocationData(cursor1, true));
                } while (cursor1.moveToNext());
            }
            parentObjects.add(dateCount);
        }
        return parentObjects;
    }

    public List getLocationData(){
        StandingDBHelper locationDb = new StandingDBHelper(mContext);
        List<ParentObject> parentObjects = new ArrayList<>();
        ArrayList<String> locations = locationDb.getUniquelocation();
        for (String location : locations) {
            String[] projection = new String[]{"coord_lat", "coord_long", "date",
                    "start_time", "stop_time", "standing_time", "set_record_time", "_id", "address"};
            String[] selectionLocations = {location};
            Cursor cursor1 = resolver.query(StandingContract.StandingEntry.CONTENT_URI, projection, "address = ? ", selectionLocations, null);
            LocationByDate dateCount = new LocationByDate();
            dateCount.date = location;
            if(cursor1.moveToFirst()) {
                do {
                    dateCount.setChildObjectList(getOneLocationData(cursor1, false));
                } while (cursor1.moveToNext());
            }
            parentObjects.add(dateCount);
        }
        return parentObjects;
    }

    private ArrayList getOneLocationData(Cursor cursor1, boolean isDate){
        ArrayList longLat = new ArrayList<String>();
        LocationChildData locationChildData = new LocationChildData();
        if(isDate) {
            locationChildData.address = cursor1.getString(cursor1.getColumnIndex("address"));
        }else {
            locationChildData.address = cursor1.getString(cursor1.getColumnIndex("date"));
        }
        locationChildData.longLat = ("Latitude " + cursor1.getString(cursor1.getColumnIndex("coord_lat")) + " Longitude " + cursor1.getString(cursor1.getColumnIndex("coord_long")));
        locationChildData.timeSpent = (getTimeInMunitesAndSeconds(Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("standing_time"))), true));

        locationChildData.interval = ("By " + cursor1.getString(cursor1.getColumnIndex("start_time")));
        locationChildData.setTime = getTimeInMunitesAndSeconds(Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("set_record_time"))), false);
        longLat.add(locationChildData);
        return longLat;
    }

    public Cursor getLocation(){
        String[] projection = new String[]{"DISTINCT date", "coord_lat", "coord_long",
                "start_time", "stop_time", "standing_time", "set_record_time", "_id"};
        return resolver.query(StandingContract.StandingEntry.CONTENT_URI, projection, null, null, null);
    }

    private String getTimeInMunitesAndSeconds(int timeSpent, boolean isSpent){
        int timeInMunites = timeSpent/60;
        int timeInSeconds = timeSpent%60;
        if(isSpent)
            return "Spent " + timeInMunites + " munites : " + timeInSeconds + " second";
        else
            return "Set Time " + timeInMunites + " munites : " + timeInSeconds + " second";
    }

}
