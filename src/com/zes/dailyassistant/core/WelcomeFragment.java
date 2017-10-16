package com.zes.dailyassistant.core;

import com.zes.dailyassistant.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeFragment extends Fragment {
	private Button mOpenMainActivity;
	private Button mOpenOrCloseFloatWindow;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_welcome, container, false);
		
		mOpenMainActivity = (Button)v.findViewById(R.id.button_open_main_activity);
		mOpenMainActivity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "click OpenMainActivity", Toast.LENGTH_SHORT).show();
			}
		});
		
		mOpenOrCloseFloatWindow = (Button)v.findViewById(R.id.button_open_or_close_float_window);
		mOpenOrCloseFloatWindow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "click OpenOrCloseFloatWindow", Toast.LENGTH_SHORT).show();
				mOpenOrCloseFloatWindow.setText(R.string.text_close_float_window);
			}
		});
		
		return v;
	}
}
