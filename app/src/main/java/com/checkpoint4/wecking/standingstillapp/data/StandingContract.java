package com.checkpoint4.wecking.standingstillapp.data;


import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by andela on 10/6/15.
 */
public class StandingContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_WEATHER = "weather";
    public static final String PATH_LOCATION = "location";

    public static long normalizeDate(long startDate){
        Time time = new Time();
        time.setToNow();

        int julianDay = Time.getJulianDay(startDate, time.gmtoff);

        return time.setJulianDay(julianDay);
    }

    public static final class LocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "location";



        public static final String COLUMN_CITY_NAME = "city_name";

        public static final String COLUMN_COORD_LAT = "coord_lat";

        public static final String COLUMN_COORD_LONG = "coord_long";
    }

    public static final class StandingEntry implements BaseColumns {

        public static final String TABLE_NAME = "standing";

        public static  final String COLUMN_LOC_KEY = "location_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SHORT_DESC = "short_desc";
        public static final String COLUMN_STANDING_ID = "standing_id";

        public static final String COLUMN_START_TIME = "start_time";
        public static final String COLUMN_STOP_TIME = "stop_time";
        public static final String COLUMN_STANDING_TIME = "standing_time";
        public static final String COLUMN_SET_RECORD_TIME = "set_record_time";

    }
}
