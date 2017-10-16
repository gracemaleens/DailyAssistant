package com.zes.dailyassistant.core;

import java.util.Calendar;

import com.zes.dailyassistant.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimePickerDialogFragment extends DialogFragment {
	public static final String TAG = DateTimePickerDialogFragment.class.getName();
	
	private DatePicker mDatePicker;
	private TimePicker mTimePicker;
	
	private Calendar mCalendar;
	
	public static final String EXTRA_DATETIME = "datetime";
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mCalendar = Calendar.getInstance();
		int year = mCalendar.get(Calendar.YEAR);
		int month = mCalendar.get(Calendar.MONTH);
		int day = mCalendar.get(Calendar.DAY_OF_MONTH);
		
		View v = (View)LayoutInflater.from(getContext()).inflate(R.layout.dialog_fragment_date_time, null);
		
		mDatePicker = (DatePicker)v.findViewById(R.id.date_time_dialog_fragment_datePicker);
		mDatePicker.init(year, month, day, new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				mCalendar.set(year, monthOfYear, dayOfMonth);
			}
		});
		
		mTimePicker = (TimePicker)v.findViewById(R.id.date_time_dialog_fragment_timePicker);
		mTimePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute);
			}
		});
				
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
		alertDialog.setView(v);
		alertDialog.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				setResult();
			}
		});
		alertDialog.setNegativeButton("取消", null);
		
		return alertDialog.create();
	}
	
//	@Override
//	public void onDestroyView() {
//		// TODO Auto-generated method stub
//		super.onDestroyView();
//		setResult();
//	}
	
	private void setResult(){
		if(getTargetFragment() == null){
			return;
		}
		
		Intent data = new Intent();
		data.putExtra(EXTRA_DATETIME, mCalendar.getTime());
		getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, data);
	}
}
