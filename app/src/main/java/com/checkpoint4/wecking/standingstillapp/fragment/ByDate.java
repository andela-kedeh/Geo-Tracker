package com.checkpoint4.wecking.standingstillapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.checkpoint4.wecking.standingstillapp.R;


public class ByDate extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_list_location_by_date, container, false);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
