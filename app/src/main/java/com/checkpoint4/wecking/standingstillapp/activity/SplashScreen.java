package com.checkpoint4.wecking.standingstillapp.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.checkpoint4.wecking.standingstillapp.R;

public class SplashScreen extends AppCompatActivity{
    private String TAG = "SplashScreen";
	private LocationManager locationManager;

	private String provider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
        initialise();
        loading();
    }

    private void initialise() {
        ImageView rotate_image =(ImageView) findViewById(R.id.imgLogo);
        RotateAnimation rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2500);
        rotate_image.startAnimation(rotate);
    }

    private void loading() {
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(SplashScreen.this, MapsActivity.class);
                    startActivity(i);
                }
            }
        };
        timerThread.start();
    }
}

