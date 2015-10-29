package com.checkpoint4.wecking.standingstillapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.checkpoint4.wecking.standingstillapp.Location.Constants;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.util.NetworkUtil;

import java.util.ArrayList;

/**
 * Created by wecking on 10/22/15.
 */
public class ListLocationAdapter extends CursorAdapter{

    private Context context;
    private Cursor locationData;
    private boolean isList;
    private TextView tAddress;
    private TextView tLocation;
    private TextView timeSpent;
    private TextView interval;
    private Double latitude;
    private Double longitude;

    public ListLocationAdapter(Context context, Cursor cursor, boolean isList) {
        super(context, cursor, 0);
        this.context = context;
        this.locationData = cursor;
        this.isList = isList;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_location, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        initialize(view);
        latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_lat")));
        longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex("coord_long")));
        setAddressView(cursor);

        String time = " Spent " + cursor.getString(cursor.getColumnIndex("standing_time")) + "min " +
                " Set Time " + cursor.getString(cursor.getColumnIndex("set_record_time"));
        String intervalValue = " From " + cursor.getString(cursor.getColumnIndex("start_time")) +
                " To " + cursor.getString(cursor.getColumnIndex("stop_time"));
        String location = "Lat " + latitude + " Long " + longitude;

        tLocation.setText(location);
        timeSpent.setText(time);
        interval.setText(intervalValue);
    }

    private void setAddressView(Cursor cursor) {
        if(isList){
            String date = cursor.getString(cursor.getColumnIndex("date"));
            tAddress.setText(date);
        }else {
            String address = " ";
            if(NetworkUtil.getConnectivityStatus(context)) {
                try {
                    address = Constants.getLocationAddress(longitude, latitude, 0, context) +
                            " " + Constants.getLocationAddress(longitude, latitude, 1, context) +
                            " " + Constants.getLocationAddress(longitude, latitude, 2, context);
                }catch(Exception e){
                    e.printStackTrace();
                    address = "Connect to see full address";
                }
            } else {
                Toast.makeText(context, "Connect to the Internet to See Street Name", Toast.LENGTH_LONG);
                address = "Connect to see full address";
            }
            tAddress.setText(address);
        }
    }

    private void initialize(View view) {
        tAddress = (TextView) view.findViewById(R.id.address);
        tLocation = (TextView) view.findViewById(R.id.longLat);
        timeSpent = (TextView) view.findViewById(R.id.time_spent);
        interval = (TextView) view.findViewById(R.id.interval);
    }
}
