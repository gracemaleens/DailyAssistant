package com.zes.dailyassistant.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, FloatWindowService.class);
		startService(intent);
		finish();
	}
}
