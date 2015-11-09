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
    public String time = null;

    public LocationByDate() {
        childList = new ArrayList();
    }

    @Override
    public List<Object> getChildObjectList() {
        return childList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childList.add(list);
    }

}
