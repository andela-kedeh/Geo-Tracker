package com.checkpoint4.wecking.standingstillapp.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.checkpoint4.wecking.standingstillapp.R;

/**
 * Created by andela on 11/7/15.
 */
public class LocationDateChildViewHolder extends ChildViewHolder {
    ImageView locationIcon;
    TextView timeSpent;
    TextView interval;
    TextView lonLat;
    public LocationDateChildViewHolder(View itemView) {
        super(itemView);
        locationIcon = (ImageView) itemView.findViewById(R.id.location_icon);
        timeSpent = (TextView) itemView.findViewById(R.id.time_spent);
        interval = (TextView) itemView.findViewById(R.id.interval);
        lonLat = (TextView) itemView.findViewById(R.id.longLat);
    }
}
