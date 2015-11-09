package com.checkpoint4.wecking.standingstillapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by wecking on 11/4/15.
 */
public class TimerSettings {
    private static int DEFAULT_TIME = 300;
    private SharedPreferences sharedPref;
    private Context activity;

    public TimerSettings(Context activity) {
        this.activity = activity;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public void saveTimeSetting(int time) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("time", time);
        editor.apply();
    }

    public int getTimeSetting() {
        return sharedPref.getInt("time", DEFAULT_TIME);
    }
}
