package com.zes.dailyassistant.core;

import java.util.UUID;

import com.zes.dailyassistant.R;
import com.zes.dailyassistant.base.BulkEditListFragment;
import com.zes.dailyassistant.base.GeneralTitleBar;
import com.zes.dailyassistant.tools.ILog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RemindFragment extends BulkEditListFragment {
	private static final String TAG = RemindFragment.class.getName();
	
	public static final int REQUEST_CODE_REMIND = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = super.onCreateView(inflater, container, savedInstanceState);
		TextView emptyTextView = (TextView)v.findViewById(android.R.id.empty);
		emptyTextView.setText("当前无日程提醒.");
		
		GeneralTitleBar titleBar = (GeneralTitleBar)v.findViewById(R.id.bulk_edit_generalTitleBar);
		titleBar.setTitle(R.string.remind_fragment_title);
		
		return v;
	}


	@Override
	protected int getAdapterCount() {
		// TODO Auto-generated method stub
		return RemindManager.getInstance().getReminds().size();
	}

	@Override
	protected Object getAdapterItem(int position) {
		// TODO Auto-generated method stub
		return RemindManager.getInstance().getReminds().get(position);
	}

	@Override
	protected String getAdapterTextOfLargeTextView(int position) {
		// TODO Auto-generated method stub
		return RemindManager.getInstance().getReminds().get(position).getTitle();
	}

	@Override
	protected String getAdapterTextOfSmallTextView(int position) {
		// TODO Auto-generated method stub
		return RemindManager.getInstance().getReminds().get(position).getSimpleDateFormat();
	}


	@Override
	protected void onListItemClick(int position) {
		// TODO Auto-generated method stub
		startRemindEditorActivity(position);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch(requestCode) {
		case REQUEST_CODE_REMIND:
			ILog.d(TAG, "resultCode=" + resultCode);
			if(resultCode == Activity.RESULT_OK) {
				notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void createNewItem() {
		// TODO Auto-generated method stub
		startRemindEditorActivity(-1);
	}
	
	private void startRemindEditorActivity(int position) {
		Intent i = new Intent(getContext(), RemindEditorActivity.class);
		if(position > -1) {
			UUID id = RemindManager.getInstance().getRemind(position).getId();
			i.putExtra(RemindEditorFragment.KEY_ID, id);
		}
		startActivityForResult(i, REQUEST_CODE_REMIND);
	}
	
	@Override
	protected void removeItem(int index){
		RemindManager.getInstance().removeRemind(getContext(), index);
	}
}
