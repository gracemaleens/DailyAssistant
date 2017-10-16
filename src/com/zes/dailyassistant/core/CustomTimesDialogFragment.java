package com.zes.dailyassistant.core;

import java.util.ArrayList;
import java.util.EnumSet;

import com.zes.dailyassistant.base.MultiSelectListDialogFragment;
import com.zes.dailyassistant.core.Remind.CustomTime;

import android.R.integer;
import android.os.Bundle;

public class CustomTimesDialogFragment extends MultiSelectListDialogFragment {
	public static final String TAG = CustomTimesDialogFragment.class.getName();
	
	public static final String KEY_CHECKED = "key_checked";
	
	public static CustomTimesDialogFragment newInstance(EnumSet<CustomTime> customTimes) {
		Bundle args = new Bundle();
		args.putSerializable(KEY_CHECKED, customTimes);
		CustomTimesDialogFragment fragment = new CustomTimesDialogFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(getArguments() != null) {
			@SuppressWarnings("unchecked")
			EnumSet<CustomTime> customTimes = (EnumSet<Remind.CustomTime>)getArguments().getSerializable(KEY_CHECKED);
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			for(CustomTime customTime : customTimes) {
				arrayList.add(customTime.ordinal());
			}
			if(!arrayList.isEmpty()) {
				setChecked(arrayList, true);
			}
		}
	}

	@Override
	public String getItemText(int position) {
		// TODO Auto-generated method stub
		return Remind.getCustomTimesOfItem(position);
	}

	@Override
	public int getAdapterCount() {
		// TODO Auto-generated method stub
		return Remind.getCustomTimesOfSize();
	}

	@Override
	public Object getAdapterItem(int position) {
		// TODO Auto-generated method stub
		return Remind.getCustomTimesOfItem(position);
	}

}
