package com.checkpoint4.wecking.standingstillapp.DataModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by wecking on 10/6/15.
 */
public class StandingDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;

    static final String DATABASE_NAME = "Location.db";

    public StandingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_STANDING_TABLE = "CREATE TABLE " + StandingContract.StandingEntry.TABLE_NAME + " (" +
                StandingContract.StandingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                StandingContract.StandingEntry.COLUMN_DATE + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_START_TIME + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_STOP_TIME + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_ADDRESS + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_STANDING_TIME + " REAL NOT NULL," +
                StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME + " REAL NOT NULL," +

                StandingContract.StandingEntry.COLUMN_COORD_LAT + " REAL NOT NULL," +
                StandingContract.StandingEntry.COLUMN_COORD_LONG + " REAL NOT NULL" +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_STANDING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StandingContract.StandingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public ArrayList<String> getUniqueDates() {
        ArrayList dates = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(true, StandingContract.StandingEntry.TABLE_NAME, new String[]{StandingContract.StandingEntry.COLUMN_DATE}, null, null, StandingContract.StandingEntry.COLUMN_DATE, null, null, null);
            while (cursor.moveToNext()) {
                dates.add(cursor.getString(cursor.getColumnIndex(StandingContract.StandingEntry.COLUMN_DATE)));
                Log.v("TAG", cursor.getString(cursor.getColumnIndex(StandingContract.StandingEntry.COLUMN_DATE)));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public ArrayList<String> getUniquelocation() {
        ArrayList locations = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor cursor = db.query(true, StandingContract.StandingEntry.TABLE_NAME, new String[]{StandingContract.StandingEntry.COLUMN_ADDRESS}, null, null, StandingContract.StandingEntry.COLUMN_ADDRESS, null, null, null);
            while (cursor.moveToNext()) {
                locations.add(cursor.getString(cursor.getColumnIndex(StandingContract.StandingEntry.COLUMN_ADDRESS)));
                Log.v("TAG", cursor.getString(cursor.getColumnIndex(StandingContract.StandingEntry.COLUMN_ADDRESS)));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

}
