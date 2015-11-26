package com.checkpoint4.wecking.standingstillapp.util;

import android.util.Log;

import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ObjectHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wecking on 10/17/15.
 */
public class Formater implements ObjectHolder{

    public static String formatDate(long longDate){
        Date date=new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = dateFormat.format(date);

        return dateText;
    }
}
