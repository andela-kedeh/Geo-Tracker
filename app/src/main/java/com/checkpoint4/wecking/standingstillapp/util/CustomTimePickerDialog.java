package com.checkpoint4.wecking.standingstillapp.util;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TimePicker;

/**
 * Created by andela on 10/15/15.
 */
public class CustomTimePickerDialog extends TimePickerDialog {

    public static final int TIME_PICKER_INTERVAL = 15;
    private boolean mIgnoreEvent = false;
    private int hour;
    private int min;

    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
        super(context, callBack, hourOfDay, minute, is24HourView);
    }

    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        super.onTimeChanged(timePicker, hourOfDay, minute);
        if (!mIgnoreEvent) {
            minute = getRoundedMinute(minute);
            mIgnoreEvent = true;
            timePicker.setCurrentMinute(minute);
            mIgnoreEvent = false;
        }
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setTitle("Minute " + hourOfDay + " : Seconds " + minute);
                hour = hourOfDay;
                min = minute;
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which);
    }

    public static int getRoundedMinute(int minute) {
        if (minute % TIME_PICKER_INTERVAL != 0) {
            int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
            minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
            if (minute == 60) minute = 0;
        }

        return minute;
    }
}
