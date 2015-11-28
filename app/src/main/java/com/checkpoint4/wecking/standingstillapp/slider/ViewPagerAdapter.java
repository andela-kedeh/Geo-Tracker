package com.checkpoint4.wecking.standingstillapp.slider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.checkpoint4.wecking.standingstillapp.fragment.ByDate;
import com.checkpoint4.wecking.standingstillapp.fragment.TimerView;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private Context _context;
	
	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);	
		_context=context;
		
    }
	@Override
	public Fragment getItem(int position) {
		android.support.v4.app.Fragment f = new android.support.v4.app.Fragment();
		switch(position){
		    case 0:
                f= TimerView.newInstance(_context);
                break;
            case 1:
                f= ByDate.newInstance(_context, true);
                break;
            case 2:
                f= ByDate.newInstance(_context, false);
                break;
		}

		return f;
	}
	@Override
	public int getCount() {
		return 3;
	}

}
