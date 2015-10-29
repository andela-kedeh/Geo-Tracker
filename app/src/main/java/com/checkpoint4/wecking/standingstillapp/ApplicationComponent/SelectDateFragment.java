package com.checkpoint4.wecking.standingstillapp.ApplicationComponent;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by andela on 10/14/15.
 */
public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private CallBack mCallBack;
    private int yy;
    private int mm;
    private int dd;

    public SelectDateFragment(final CallBack callbacks) {
        mCallBack = callbacks;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        yy = calendar.get(Calendar.YEAR);
        mm = calendar.get(Calendar.MONTH);
        dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
        if (mCallBack != null)
        mCallBack.onFinished(dd, mm, yy);
    }

}
