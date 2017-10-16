package com.zes.dailyassistant.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class DailyassistantViewPager extends ViewPager {
	private boolean isAllowScroll = true; 

	public DailyassistantViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public DailyassistantViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void scrollTo(int x, int y) {
		// TODO Auto-generated method stub
		if(isAllowScroll){
			super.scrollTo(x, y);
		}
	}
	
	public void allowScroll(boolean allow){
		isAllowScroll = allow;
	}

}
