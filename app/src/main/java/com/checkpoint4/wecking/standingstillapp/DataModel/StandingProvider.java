
package com.checkpoint4.wecking.standingstillapp.DataModel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ObjectHolder;

import java.util.HashMap;

public class StandingProvider extends ContentProvider implements ObjectHolder {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private StandingDBHelper mOpenHelper;
    private SQLiteDatabase sqlDB;
    private static HashMap<String, String> values;

    static final int STANDING = 100;
    static final int STANDING_WITH_LOCATION = 101;
    static final int STANDING_WITH_LOCATION_AND_DATE = 102;



    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(StandingContract.CONTENT_AUTHORITY, StandingContract.PATH_STANDING, STANDING);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new StandingDBHelper(getContext());
        sqlDB = mOpenHelper.getWritableDatabase();
        if(mOpenHelper != null) {
            return true;
        }
        else{
            return false;
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (sUriMatcher.match(uri)) {
            case STANDING: {
                queryBuilder.setTables(StandingContract.StandingEntry.TABLE_NAME);
                queryBuilder.setProjectionMap(values);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Cursor cursor = queryBuilder.query(sqlDB, projection, selection, selectionArgs, null,
                null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case STANDING_WITH_LOCATION_AND_DATE:
                return StandingContract.StandingEntry.CONTENT_ITEM_TYPE;
            case STANDING_WITH_LOCATION:
                return StandingContract.StandingEntry.CONTENT_TYPE;
            case STANDING:
                return StandingContract.StandingEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case STANDING: {
                long _id = db.insert(StandingContract.StandingEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = StandingContract.StandingEntry.buildStandingUri(_id);
                else {
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if ( null == selection ) selection = "1";
        switch (match) {
            case STANDING:
                rowsDeleted = db.delete(
                        StandingContract.StandingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case STANDING:
                rowsUpdated = db.update(StandingContract.StandingEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STANDING:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(StandingContract.StandingEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(23)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}