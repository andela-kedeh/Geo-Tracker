
package com.checkpoint4.wecking.standingstillapp.slider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.checkpoint4.wecking.standingstillapp.DataModel.Location;
import com.checkpoint4.wecking.standingstillapp.ObjectFactory.ActivityHolder;
import com.checkpoint4.wecking.standingstillapp.R;
import com.checkpoint4.wecking.standingstillapp.adapter.LocationAdapter;

import java.util.List;

public class DateLayout extends Fragment {

	private Location location;
	private RecyclerView recyclerView;
	private Toolbar toolbar;
	private ImageView backToMainActivity;
	private TextView title;
	private static Context context;
	private ViewGroup root;

	public static Fragment newInstance(Context contex) {
		TimerView f = new TimerView();
		context = contex;
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		root = (ViewGroup) inflater.inflate(R.layout.content_list_location_by_date, null);
		initialise();
		loadAdater();
		return root;
	}


	private void loadAdater() {
		List locationData = location.getLocationDataByDate();
		LocationAdapter adapter = new LocationAdapter(context, locationData);
		adapter.setParentClickableViewAnimationDefaultDuration();
		adapter.setParentAndIconExpandOnClick(true);
		adapter.setCustomParentAnimationViewId(R.id.parent_list_item_expand_arrow);
		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		recyclerView.setAdapter(adapter);
	}

	private void initialise(){
		toolbar = (Toolbar) root.findViewById(R.id.toolbar);
		recyclerView = (RecyclerView) root.findViewById(R.id.myRecyclerView);
		backToMainActivity = (ImageView) root.findViewById(R.id.to_main_activity);
		location = new Location(ActivityHolder.getActivity());
		title = (TextView)  root.findViewById(R.id.title);
	}
	
}
