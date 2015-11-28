
package com.checkpoint4.wecking.standingstillapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.checkpoint4.wecking.standingstillapp.ApplicationComponent.CircleTimerView;
import com.checkpoint4.wecking.standingstillapp.LocationServices.Constants;
import com.checkpoint4.wecking.standingstillapp.LocationServices.StandingService;
import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ActivityHolder;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.fragment.ByDate;

public class TimerView extends Fragment implements View.OnClickListener{

	private DrawerLayout mDrawerLayout;
	private TextView stracking_status;
	private LinearLayout Start_tracking;
	private CircleTimerView circularTimerView;
	private ImageView start_icon;

	private ViewGroup root;

	public static Fragment newInstance(Context context) {
		TimerView f = new TimerView();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.activity_maps, null);
		initialize();
		setOnClickListenner();
		return root;
	}

	private void initialize(){
		mDrawerLayout = (DrawerLayout) root.findViewById(R.id.drawer_layout);
		circularTimerView = (CircleTimerView) root.findViewById(R.id.circularTimerView);
		Start_tracking = (LinearLayout) root.findViewById(R.id.Start_tracking);
		stracking_status = (TextView) root.findViewById(R.id.stracking_status);
		start_icon = (ImageView) root.findViewById(R.id.start_icon);
	}

	private void setOnClickListenner(){
		circularTimerView.setOnClickListener(this);
		Constants.circularTimerView = circularTimerView;
		Start_tracking.setOnClickListener(this);
		stracking_status.setOnClickListener(this);
		start_icon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.Start_tracking:
			case R.id.start_icon:
			case R.id.stracking_status:
				startTracking();
				break;
			case R.id.view_headline:
				mDrawerLayout.openDrawer(GravityCompat.START);
				break;
		}
	}

	private void startTracking() {
		try {
			if (StandingService.isRunning) {
				pause();
			} else {
				start();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void pause(){
		ActivityHolder.getActivity().stopService(new Intent(ActivityHolder.getActivity(), StandingService.class));
		stracking_status.setText("Start Tracking");
		Start_tracking.setBackground(getResources().getDrawable(R.drawable.start_button_background));
		circularTimerView.pauseTimer();
	}

	private void start(){
		ActivityHolder.getActivity().startService(new Intent(ActivityHolder.getActivity(), StandingService.class));
		stracking_status.setText("Stop Tracking");
		Start_tracking.setBackground(getResources().getDrawable(R.drawable.stop_button_background));
		circularTimerView.startTimer();
	}

}
