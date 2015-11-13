package com.checkpoint4.wecking.standingstillapp.ApplicationComponent;

import android.app.Application;

/**
 * Created by andela on 11/12/15.
 */
public class GeoTrackerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new ContextProvider(this);
    }


}
