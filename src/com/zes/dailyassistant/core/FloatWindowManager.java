package com.zes.dailyassistant.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.zes.dailyassistant.tools.ITools;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class FloatWindowManager {
	private static FloatWindowManager sInstance;

	private static final String TAG = "FloatWindowManager";

	private Context mContext;

	private FloatWindow mFloatWindow;
	private LayoutParams mFloatWindowParams;
	private WindowManager mWindowManager;
	private Timer mUpdateTimer;
	private FloatWindowManageHandler mHandler;

	private boolean isFloatWindowShowing = false;

	private FloatWindowManager() {
		// TODO Auto-generated constructor stub

	}

	public static FloatWindowManager getInstance() {
		if (sInstance == null) {
			synchronized (FloatWindowManager.class) {
				sInstance = new FloatWindowManager();
			}
		}
		return sInstance;
	}

	public void init(Context context) {
		mContext = context;
		mHandler = new FloatWindowManageHandler(Looper.getMainLooper());

		mFloatWindow = (FloatWindow) LayoutInflater.from(context)
				.inflate(ITools.getResourceId(context, "layout", "float_window"), null);
		initWindowParams(context);

		update(context);
	}

	private void initWindowParams(Context context) {
		if (mFloatWindow == null) {
			mFloatWindow = (FloatWindow) LayoutInflater.from(context)
					.inflate(ITools.getResourceId(context, "layout", "float_window"), null);
		}

		int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
		int screenHeight = context.getResources().getDisplayMetrics().heightPixels;

		android.view.ViewGroup.LayoutParams IVParams = ((ImageView) mFloatWindow
				.findViewById(ITools.getResourceId(context, "id", "imageView_white_dot"))).getLayoutParams();
		int IVWidth = IVParams.width;
		int IVHeight = IVParams.height;

		mFloatWindowParams = new LayoutParams();
		mFloatWindowParams.type = LayoutParams.TYPE_PHONE;
		mFloatWindowParams.format = PixelFormat.RGBA_8888;
		mFloatWindowParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;
		mFloatWindowParams.gravity = Gravity.TOP | Gravity.START;
		mFloatWindowParams.width = IVWidth;
		mFloatWindowParams.height = IVHeight;
		mFloatWindowParams.x = screenWidth - mFloatWindowParams.width;
		mFloatWindowParams.y = screenHeight / 2;
	}

	public void update(final Context context) {
		if (mUpdateTimer == null) {
			mUpdateTimer = new Timer();
			mUpdateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mFloatWindow != null) {
						if (!isFloatWindowShowing && isHome(context)) {
							createFloatWindow();
						} else if (isFloatWindowShowing && !isHome(context)) {
							removeFloatWindow();
						}
					}
				}
			}, 0, 500);
		}
	}

	public void createFloatWindow() {
		Message msg = mHandler.obtainMessage(FloatWindowManageHandler.CREATE_FLOAT_WINDOW);
		msg.sendToTarget();
	}

	public void removeFloatWindow() {
		Message msg = mHandler.obtainMessage(FloatWindowManageHandler.REMOVE_FLOAT_WINDOW);
		msg.sendToTarget();
	}
	
	public void hideFloatWindow(){
		Message msg = mHandler.obtainMessage(FloatWindowManageHandler.HIDE_FLOAT_WINDOW);
		msg.sendToTarget();
	}
	
	public void showFloatWindow(){
		Message msg = mHandler.obtainMessage(FloatWindowManageHandler.SHOW_FLOAT_WINDOW);
		msg.sendToTarget();
	}

	public WindowManager getWindowManager(Context context) {
		if (mWindowManager == null) {
			mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return mWindowManager;
	}

	public List<String> getHomeApps(Context context) {
		List<String> names = new ArrayList<String>();
		PackageManager packageManager = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

		for (ResolveInfo ri : resolveInfo) {
			names.add(ri.activityInfo.packageName);
		}

		return names;
	}

	public boolean isHome(Context context) {
		String topPackageName = ITools.getLauncherTopApp(context);
		if (topPackageName != null && !topPackageName.equals("")) {
			for (String pkgName : getHomeApps(context)) {
				if (topPackageName.equals(pkgName)) {
//					Log.d(TAG, "当前页面是桌面， 栈顶的应用程序：" + topPackageName);
					return true;
				}
			}
		}
//		Log.d(TAG, "当前页面不是桌面， 栈顶的应用程序：" + topPackageName);
		return false;
	}

	public void onDestroy() {
		mFloatWindow.onDestroy();
		if (mUpdateTimer != null) {
			mUpdateTimer.cancel();
			mUpdateTimer = null;
		}
	}

	private class FloatWindowManageHandler extends Handler {
		public static final int CREATE_FLOAT_WINDOW = 0;
		public static final int REMOVE_FLOAT_WINDOW = 1;
		public static final int HIDE_FLOAT_WINDOW = 2;
		public static final int SHOW_FLOAT_WINDOW = 3;

		public FloatWindowManageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case CREATE_FLOAT_WINDOW:
				if (mFloatWindowParams == null || mFloatWindow == null) {
					initWindowParams(mContext);
				}
				if(!isFloatWindowShowing){
					getWindowManager(mContext).addView(mFloatWindow, mFloatWindowParams);
					isFloatWindowShowing = true;
					if(!mFloatWindow.isShowing()){
						mFloatWindow.show();
					}
				}
				break;
			case REMOVE_FLOAT_WINDOW:
				if (mFloatWindow != null) {
					mFloatWindow.hide();
					mUpdateTimer.schedule(new TimerTask() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							if(!mFloatWindow.isShowing()){
								mFloatWindow.onDestroy();
								new Handler(Looper.getMainLooper()).post(new Runnable() {
									
									@Override
									public void run() {
										// TODO Auto-generated method stub
										synchronized (FloatWindowManager.class) {
											if(isFloatWindowShowing){
												getWindowManager(mContext).removeView(mFloatWindow);
												isFloatWindowShowing = false;
											}
										}
									}
								});
								cancel();
							}
						}
					}, 500, 100);
				}
				break;
			case HIDE_FLOAT_WINDOW:
				if(mFloatWindow != null){
					mFloatWindow.hide();
				}
				break;
			case SHOW_FLOAT_WINDOW:
				if(mFloatWindow != null){
					mFloatWindow.show();
				}
				break;
			default:
				break;
			}
		}
	}

}
