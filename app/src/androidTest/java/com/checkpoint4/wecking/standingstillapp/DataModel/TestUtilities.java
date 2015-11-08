package com.checkpoint4.wecking.standingstillapp.DataModel;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;


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

    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
