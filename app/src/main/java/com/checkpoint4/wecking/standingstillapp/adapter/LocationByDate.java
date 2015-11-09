package com.checkpoint4.wecking.standingstillapp.adapter;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andela on 10/18/15.
 */
public class LocationByDate implements ParentObject {
    public String date;
    private ArrayList childList;
    private ArrayList timeSpent;
    private ArrayList interval;
    public String time = null;

    public LocationByDate() {
        childList = new ArrayList();
        timeSpent = new ArrayList<ArrayList<String>>();
        interval = new ArrayList();
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
        timeSpent.add(list);
    }

    public List<Object> getTimeSpentObjectList(){
        return timeSpent;
    }

    public void setIntervalObjectList(List<Object> list){
        interval.add(list);
    }

    public List<Object> getIntervalObjectList(){
        return interval;
    }
}
