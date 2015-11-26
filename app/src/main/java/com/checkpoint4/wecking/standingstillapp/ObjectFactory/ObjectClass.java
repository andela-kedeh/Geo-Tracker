package com.checkpoint4.wecking.standingstillapp.ObjectFactory;

import android.app.Activity;

import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.HelpDialog;
import com.checkpoint4.wecking.standingstillapp.DataModel.LocationData;
import com.checkpoint4.wecking.standingstillapp.DataModel.StandingDBHelper;
import com.checkpoint4.wecking.standingstillapp.DataModel.StandingProvider;
import com.checkpoint4.wecking.standingstillapp.adapter.LocationChildData;
import com.checkpoint4.wecking.standingstillapp.util.Formater;

/**
 * Created by andela on 11/26/15.
 */

public class ObjectClass implements ObjectHolder{

    public ObjectHolder getObject(String objectType){
        if(objectType == null){
            return null;
        }
        if(objectType.equalsIgnoreCase("LocationData")){
            return new LocationData();

        } else if(objectType.equalsIgnoreCase("Formater")) {
            return new Formater();
        }

        else if(objectType.equalsIgnoreCase("HelpDialog")) {
            return new HelpDialog((Activity) ActivityHolder.getActivity());
        }

        else if(objectType.equalsIgnoreCase("StandingDBHelper")) {
            return new StandingDBHelper(ActivityHolder.getActivity());
        }

        else if(objectType.equalsIgnoreCase("LocationChildData")) {
            return new LocationChildData();
        }

        else if(objectType.equalsIgnoreCase("StandingProvider")) {
            return new StandingProvider();
        }

        else if(objectType.equalsIgnoreCase("LocationData")) {
            return new LocationData();
        }

        return null;
    }
}
