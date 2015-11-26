package com.checkpoint4.wecking.standingstillapp.ObjectFactory;

import android.app.Activity;
import android.content.Context;

/**
 * Created by andela on 11/26/15.
 */
public class ActivityHolder {
    private static Activity activity;

    public static Context getActivity(){
        return activity;
    }

    public static void setActivity(Activity activity) {
        ActivityHolder.activity = activity;
    }
}