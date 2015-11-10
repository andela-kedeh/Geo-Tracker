package com.checkpoint4.wecking.standingstillapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.checkpoint4.wecking.standingstillapp.DataModel.LocationData;
import com.checkpoint4.wecking.standingstillapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 11/7/15.
 */
public class LocationAdapter extends ExpandableRecyclerAdapter<LocationViewHolder, LocationChildViewHolder> {
    private LayoutInflater inflater;
    List<ParentObject> data;
    public LocationAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
        data = parentItemList;

    }

    @Override
    public LocationViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.location_by_day_parent, viewGroup, false);
        LocationViewHolder holder = new LocationViewHolder(view);
        return holder;
    }

    @Override
    public LocationChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.location_by_day_child, viewGroup, false);
        LocationChildViewHolder holder = new LocationChildViewHolder(view);
        return holder;
    }

    @Override
    public void onBindParentViewHolder(LocationViewHolder locationViewHolder, int i, Object parentObject) {
        LocationData location = (LocationData) parentObject;
        locationViewHolder.date.setText(location.date);
    }

    @Override
    public void onBindChildViewHolder(LocationChildViewHolder locationChildViewHolder, int i, Object childObject) {
        ArrayList child = (ArrayList) childObject;
        LocationChildData locationChildData = (LocationChildData) child.get(0);
        locationChildViewHolder.lonLat.setText(locationChildData.longLat);
        locationChildViewHolder.timeSpent.setText(locationChildData.timeSpent);
        locationChildViewHolder.interval.setText(locationChildData.interval);
        locationChildViewHolder.address.setText(locationChildData.address);
        locationChildViewHolder.setTime.setText(locationChildData.setTime);
    }
}
