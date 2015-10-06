package com.checkpoint4.wecking.standingstillapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andela on 10/6/15.
 */
public class StandingDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "weather.db";

    public StandingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + StandingContract.LocationEntry.TABLE_NAME + " (" +
                StandingContract.LocationEntry._ID + " INTEGER PRIMARY KEY," +
                StandingContract.LocationEntry.COLUMN_CITY_NAME + " TEXT NOT NULL," +
                StandingContract.LocationEntry.COLUMN_COORD_LAT + " REAL NOT NULL," +
                StandingContract.LocationEntry.COLUMN_COORD_LONG + " REAL NOT NULL" +
                " );";

        final String SQL_CREATE_STANDING_TABLE = "CREATE TABLE " + StandingContract.StandingEntry.TABLE_NAME + " (" +
                StandingContract.StandingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                StandingContract.StandingEntry.COLUMN_LOC_KEY + " INTEGER NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_DATE + " INTEGER NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_SHORT_DESC + " TEXT NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_STANDING_ID + " INTEGER NOT NULL, " +

                StandingContract.StandingEntry.COLUMN_START_TIME + " INTEGER NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_STOP_TIME + " INTEGER NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_STANDING_TIME + " INTEGER NOT NULL, " +
                StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + StandingContract.StandingEntry.COLUMN_LOC_KEY + ") REFERENCES " +
                StandingContract.LocationEntry.TABLE_NAME + " (" + StandingContract.LocationEntry._ID + "), " +

                " UNIQUE (" + StandingContract.StandingEntry.COLUMN_DATE + ", " +
                StandingContract.StandingEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STANDING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StandingContract.LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StandingContract.StandingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
