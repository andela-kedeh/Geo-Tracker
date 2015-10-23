package com.checkpoint4.wecking.standingstillapp.util;

import android.os.CountDownTimer;

/**
 * Created by andela on 10/16/15.
 */
public class MyCountDownTimer extends CountDownTimer {

    public MyCountDownTimer(long startTime, long interval) {
        super(startTime, interval);
    }
    @Override
    public void onFinish() {
    }
    @Override
    public void onTick(long millisUntilFinished) {

    }
}