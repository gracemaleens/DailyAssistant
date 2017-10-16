package com.zes.dailyassistant.core;

import com.zes.dailyassistant.base.SimpleListDialogFragment;

public class RepetitionsDialogFragment extends SimpleListDialogFragment {
	public static final String TAG = RepetitionsDialogFragment.class.getName();

	@Override
	public int getAdapterCount() {
		// TODO Auto-generated method stub
		return Remind.getRepetitionsOfSize();
	}

	@Override
	public Object getAdapterItem(int position) {
		// TODO Auto-generated method stub
		return Remind.getRepetitionsOfItem(position);
	}

	@Override
	public String getItemText(int position) {
		// TODO Auto-generated method stub
		return Remind.getRepetitionsOfItem(position);
	}
	
}
