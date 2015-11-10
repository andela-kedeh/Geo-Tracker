package com.checkpoint4.wecking.standingstillapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.checkpoint4.wecking.standingstillapp.R;

/**
 * Created by andela on 11/7/15.
 */
public class LocationChildViewHolder extends ChildViewHolder {
    ImageView locationIcon;
    TextView timeSpent;
    TextView address;
    TextView setTime;
    TextView interval;
    TextView lonLat;
    public LocationChildViewHolder(View itemView) {
        super(itemView);
        locationIcon = (ImageView) itemView.findViewById(R.id.location_icon);
        address = (TextView) itemView.findViewById(R.id.address);
        setTime = (TextView) itemView.findViewById(R.id.set_time);
        timeSpent = (TextView) itemView.findViewById(R.id.time_spent);
        interval = (TextView) itemView.findViewById(R.id.interval);
        lonLat = (TextView) itemView.findViewById(R.id.longLat);
    }
}
