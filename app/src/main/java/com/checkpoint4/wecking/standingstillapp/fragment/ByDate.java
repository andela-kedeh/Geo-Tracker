
package com.checkpoint4.wecking.standingstillapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class ByDate extends Fragment {

	private Location location;
	private RecyclerView recyclerView;
	private Toolbar toolbar;
	private ImageView backToMainActivity;
	private TextView title, status;
	private static Context context;
	private ViewGroup root;
	private static boolean isDate;

	public static Fragment newInstance(Context contex, boolean isDat) {
		ByDate f = new ByDate();
		context = contex;
        isDate = isDat;
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
		List locationData;
		if(isDate)
			locationData = location.getLocationDataByDate();
		else {
			locationData = location.getLocationDataLocation();
		}
        if(locationData.size() == 0)
            status.setVisibility(View.VISIBLE);
        LocationAdapter adapter = new LocationAdapter(ActivityHolder.getActivity(), locationData);
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
        status = (TextView) root.findViewById(R.id.record_status);
		location = new Location(ActivityHolder.getActivity());
		title = (TextView)  root.findViewById(R.id.title);
	}
	
}
