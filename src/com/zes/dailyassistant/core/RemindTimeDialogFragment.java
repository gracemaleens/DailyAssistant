package com.zes.dailyassistant.core;

import com.zes.dailyassistant.base.SimpleListDialogFragment;

public class RemindTimeDialogFragment extends SimpleListDialogFragment {
	public static final String TAG = RemindTimeDialogFragment.class.getName();

	@Override
	public int getAdapterCount() {
		// TODO Auto-generated method stub
		return Remind.getRemindTimesOfSize();
	}

	@Override
	public Object getAdapterItem(int position) {
		// TODO Auto-generated method stub
		return Remind.getRemindTimesOfItem(position);
	}

	@Override
	public String getItemText(int position) {
		// TODO Auto-generated method stub
		return Remind.getRemindTimesOfItem(position);
	}
}
