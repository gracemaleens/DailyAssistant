package com.zes.dailyassistant.core;

import com.zes.dailyassistant.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class SingleFragmentActivity extends FragmentActivity {
	protected abstract Fragment createFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_fragment);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment containerFragment = fm.findFragmentById(R.id.fragment_container);
		if(containerFragment == null){
			containerFragment = createFragment();
			fm.beginTransaction()
			.add(R.id.fragment_container, containerFragment)
			.commit();
		}
	}
}
