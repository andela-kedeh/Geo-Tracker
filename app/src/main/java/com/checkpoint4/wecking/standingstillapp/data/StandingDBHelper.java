package com.checkpoint4.wecking.standingstillapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andela on 10/6/15.
 */
public class StandingDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;

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
                StandingContract.StandingEntry.COLUMN_STANDING_TIME + " REAL NOT NULL," +
                StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME + " REAL NOT NULL," +

                StandingContract.StandingEntry.COLUMN_STREET_NAME + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_STATE_NAME + " TEXT NOT NULL," +
                StandingContract.StandingEntry.COLUMN_COUNTRY_NAME + " TEXT NOT NULL," +
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
}
