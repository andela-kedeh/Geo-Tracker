package com.checkpoint4.wecking.standingstillapp.adapter;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 10/18/15.
 */
public class LocationByDate implements ParentObject {
    public String date;
    private ArrayList childList;
    private ArrayList longLat;
    private ArrayList interval;

    public LocationByDate() {
        childList = new ArrayList<String>();
        longLat = new ArrayList<String>();
        interval = new ArrayList<String>();
    }

    @Override
    public List<Object> getChildObjectList() {
        return childList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childList.add(list);
    }

    public void setTimeSpentObjectList(List<Object> list){
        longLat.add(list);
    }

    public List<Object> getTimeSpentObjectList(){
        return longLat;
    }

    public void setIntervalObjectList(List<Object> list){
        interval.add(list);
    }

    public List<Object> getIntervalObjectList(){
        return interval;
    }
}
