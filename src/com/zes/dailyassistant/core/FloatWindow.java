package com.zes.dailyassistant.core;

import java.util.Timer;
import java.util.TimerTask;

import com.zes.dailyassistant.tools.ITools;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

enum FloatWindowState {
	STATE_OPAQUE, STATE_OPAQUE_TO_TRANSLUCENT, STATE_OPAQUE_TO_HIDE, STATE_TRANSLUCENT, STATE_TRANSLUCENT_TO_OPAQUE, STATE_TRANSLUCENT_TO_HIDE, /* STATE_TOUCH, */STATE_HIDE, STATE_TO_HIDE, STATE_HIDE_TO_OPAQUE, STATE_SHOW, STATE_TO_SHOW
};

@SuppressLint("ClickableViewAccessibility")
public class FloatWindow extends LinearLayout implements View.OnTouchListener, View.OnClickListener {
	private static final String TAG = FloatWindow.class.getName();

	private Context mContext;
	private ImageView mDotIV;
	private boolean isTouch = false;
	private FloatWindowState mCurrentState = FloatWindowState.STATE_OPAQUE;

	private Timer mRefreshTimer;
	private TimerTask mUpdateTask;

	private float mLastX, mLastY;
	private long mBeginClickTime;
	private float mBeginClickX, mBeginClickY;

	public FloatWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;

		setWillNotDraw(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (mDotIV == null) {
			mDotIV = (ImageView) findViewById(ITools.getResourceId(mContext, "id", "imageView_white_dot"));
			mDotIV.setOnTouchListener(this);
			mDotIV.setAlpha(0.0f);
		}
		updateView();
	}

	@TargetApi(17)
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// // TODO Auto-generated method stub
		if (v.getId() == ITools.getResourceId(mContext, "id", "imageView_white_dot")) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mBeginClickTime = System.currentTimeMillis();
				mBeginClickX = event.getRawX();
				mBeginClickY = event.getRawY();

				opaque();

				return true;
			case MotionEvent.ACTION_MOVE:
				if (Math.abs(event.getRawX() - mBeginClickX) > 10 || Math.abs(event.getRawY() - mBeginClickY) > 10) {
					mBeginClickTime = 0;
				}

				return true;
			case MotionEvent.ACTION_UP:
				if (mBeginClickTime != 0 && (System.currentTimeMillis() - mBeginClickTime) < 500) {
					onClick(mDotIV);
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(mContext, WelcomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).dispatchTouchEvent(ev);
		}

		onTouchEvent(ev);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		WindowManager.LayoutParams windowParams = (WindowManager.LayoutParams) getLayoutParams();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "ACTION_DOWN");

			isTouch = true;

			mLastX = event.getRawX();
			mLastY = event.getRawY();

			return true;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "ACTION_MOVE");

			float moveX = event.getRawX() - mLastX;
			float moveY = event.getRawY() - mLastY;
			if (Math.abs(moveX) > 10 || Math.abs(moveY) > 10) {
				windowParams.x += moveX;
				windowParams.y += moveY;
				FloatWindowManager.getInstance().getWindowManager(mContext).updateViewLayout(this, windowParams);

				mLastX = event.getRawX();
				mLastY = event.getRawY();
			}

			return true;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "ACTION_UP");
			isTouch = false;
			return true;
		default:
			return super.onTouchEvent(event);
		}
	}

	public void updateView() {
		if (mRefreshTimer == null) {
			mRefreshTimer = new Timer();
		}
		if (mUpdateTask == null) {
			mUpdateTask = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mDotIV.getAlpha() <= 0.0f && mCurrentState != FloatWindowState.STATE_HIDE) {
						mCurrentState = FloatWindowState.STATE_HIDE;
					}

					if (mCurrentState == FloatWindowState.STATE_OPAQUE && !isTouch) {
						translucent();
					}
				}
			};
			mRefreshTimer.schedule(mUpdateTask, 0, 10);
		}
	}

	public void opaque() {
		if (mRefreshTimer == null) {
			mRefreshTimer = new Timer();
		}
		mRefreshTimer.schedule(new FloatWindowTimerTask(FloatWindowState.STATE_TRANSLUCENT_TO_OPAQUE), 0, 10);
	}

	public void translucent() {
		if (mRefreshTimer == null) {
			mRefreshTimer = new Timer();
		}
		mRefreshTimer.schedule(new FloatWindowTimerTask(FloatWindowState.STATE_OPAQUE_TO_TRANSLUCENT), 1000, 10);
	}

	public void hide() {
		if (mRefreshTimer == null) {
			mRefreshTimer = new Timer();
		}
		mRefreshTimer.schedule(new FloatWindowTimerTask(FloatWindowState.STATE_TO_HIDE), 0, 10);
	}

	public void show() {
		if (mRefreshTimer == null) {
			mRefreshTimer = new Timer();
		}
		mRefreshTimer.schedule(new FloatWindowTimerTask(FloatWindowState.STATE_TO_SHOW), 0, 10);
	}

	public boolean isShowing() {
		if (mDotIV != null) {
			return !(mDotIV.getAlpha() <= 0.0f);
		}
		return false;
	}

	public void onDestroy() {
		if (mRefreshTimer != null) {
			mRefreshTimer.cancel();
			mRefreshTimer = null;
		}
		mUpdateTask = null;
	}

	private class FloatWindowTimerTask extends TimerTask {
		private FloatWindowState mTab;

		public FloatWindowTimerTask(FloatWindowState tab) {
			// TODO Auto-generated constructor stub
			mTab = tab;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mDotIV == null)
				return;

			new Handler(Looper.getMainLooper()).post(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mTab == FloatWindowState.STATE_OPAQUE_TO_TRANSLUCENT) {
						synchronized (FloatWindowTimerTask.class) {
							if (mCurrentState == FloatWindowState.STATE_HIDE || isTouch) {
								cancel();
							}
							if (mDotIV.getAlpha() <= 0.5f) {
								cancel();
								mCurrentState = FloatWindowState.STATE_TRANSLUCENT;
							} else {
								mDotIV.setAlpha(mDotIV.getAlpha() - 0.0001f);
							}
						}
					} else if (mTab == FloatWindowState.STATE_TRANSLUCENT_TO_OPAQUE
							|| mTab == FloatWindowState.STATE_HIDE_TO_OPAQUE
							|| mTab == FloatWindowState.STATE_TO_SHOW) {
						synchronized (FloatWindowTimerTask.class) {
							if(mTab == FloatWindowState.STATE_TO_SHOW){
								mCurrentState = FloatWindowState.STATE_TO_SHOW;
							}
							if(mCurrentState == FloatWindowState.STATE_TO_HIDE){
								cancel();
							}
							if (mDotIV.getAlpha() >= 1.0f) {
								cancel();
								mCurrentState = FloatWindowState.STATE_OPAQUE;
							} else {
								mDotIV.setAlpha(mDotIV.getAlpha() + 0.01f);
							}
						}
					} else if (mTab == FloatWindowState.STATE_TO_HIDE) {
						synchronized (FloatWindowTimerTask.class) {
							if(mCurrentState == FloatWindowState.STATE_TO_SHOW){
								cancel();
								return;
							}
							mCurrentState = FloatWindowState.STATE_TO_HIDE;
							if (mDotIV.getAlpha() <= 0.0f) {
								cancel();
								mCurrentState = FloatWindowState.STATE_HIDE;
							} else {
								mDotIV.setAlpha(mDotIV.getAlpha() - 0.01f);
							}
						}
					}
				}
			});
		}
	}

}
