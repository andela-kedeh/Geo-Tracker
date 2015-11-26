package com.checkpoint4.wecking.standingstillapp.ApplicationComponent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ObjectHolder;
import com.checkpoint4.wecking.standingstillapp.R;

/**
 * Created by andela on 11/12/15.
 */
public class HelpDialog implements ObjectHolder{
    private Activity activity;

    public HelpDialog(Activity activity) {
        this.activity = activity;
    }

    public void showHelpToUse() {
        final LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.how_to_use, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        }).setTitle("HOW IT WORKS");

        builder.show();
    }
}
