package com.checkpoint4.wecking.standingstillapp.util;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.checkpoint4.wecking.standingstillapp.R;

import java.util.ArrayList;

/**
 * Created by andela on 10/22/15.
 */
public class ListLocation extends CursorAdapter{

    private Context context;
    private Cursor locationData;
    private boolean isList;

    public ListLocation(Context context, Cursor cursor, boolean isList) {
        super(context, cursor, 0);
        this.context = context;
        this.locationData = cursor;
        this.isList = isList;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_location, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tAddress = (TextView) view.findViewById(R.id.address);
        TextView tLocation = (TextView) view.findViewById(R.id.longLat);
        TextView timeSpent = (TextView) view.findViewById(R.id.time_spent);
        TextView interval = (TextView) view.findViewById(R.id.interval);

        // Extract properties from cursor
        if(isList){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            tAddress.setText(date);
        }else {
            String address = cursor.getString(cursor.getColumnIndex("street")) +
                    " " + cursor.getString(cursor.getColumnIndex("state")) +
                    " " + cursor.getString(cursor.getColumnIndex("country"));
            tAddress.setText(address);
        }

        String time = " Spent " + cursor.getString(cursor.getColumnIndex("standing_time")) + "min " +
                " Set Time " + cursor.getString(cursor.getColumnIndex("set_record_time"));

        String intervalValue = " From " + cursor.getString(cursor.getColumnIndex("start_time")) +
                " To " + cursor.getString(cursor.getColumnIndex("stop_time"));

        String location = "Lat " + Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_lat"))) + " Long " +
                Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_long")));

        // Populate fields with extracted properties
        tLocation.setText(location);
        timeSpent.setText(time);
        interval.setText(intervalValue);
    }
}
