package com.zes.dailyassistant.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FloatWindowService extends Service {
	private static final String TAG = "FloatWindowService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		FloatWindowManager.getInstance().init(getApplicationContext());
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		FloatWindowManager.getInstance().onDestroy();
	}

}
