package com.checkpoint4.wecking.standingstillapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;


import com.checkpoint4.wecking.standingstillapp.utils.PollingCheck;

import java.util.Map;
import java.util.Set;


public class TestUtilities extends AndroidTestCase {
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createWeatherValues(long locationRowId) {
        ContentValues weatherValues = new ContentValues();
        weatherValues.put(StandingContract.StandingEntry.COLUMN_LOC_KEY, locationRowId);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_DATE, TEST_DATE);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_START_TIME, TEST_DATE);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_STOP_TIME, TEST_DATE);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_STANDING_TIME, 01);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_SHORT_DESC, "Asteroids");
        weatherValues.put(StandingContract.StandingEntry.COLUMN_SET_RECORD_TIME, 5.5);
        weatherValues.put(StandingContract.StandingEntry.COLUMN_STANDING_ID, 321);

        return weatherValues;
    }

    static ContentValues createNorthPoleLocationValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(StandingContract.LocationEntry.COLUMN_CITY_NAME, "North Pole");
        testValues.put(StandingContract.LocationEntry.COLUMN_COORD_LAT, 64.7488);
        testValues.put(StandingContract.LocationEntry.COLUMN_COORD_LONG, -147.353);

        return testValues;
    }

    static long insertNorthPoleLocationValues(Context context) {
        StandingDBHelper dbHelper = new StandingDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtilities.createNorthPoleLocationValues();

        long locationRowId;
        locationRowId = db.insert(StandingContract.LocationEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);

        return locationRowId;
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
