package com.checkpoint4.wecking.standingstillapp.ApplicationComponent;

import android.content.Context;

/**
 * Created by andela on 11/12/15.
 */
public class ContextProvider {
    private static Context context;

    public ContextProvider(Context context1) {
        context = context1;
    }

    public static Context getContext(){
        return context;
    }

}
