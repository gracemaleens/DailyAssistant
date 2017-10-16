package com.zes.dailyassistant.tools;

import android.util.Log;

public class ILog {
	private static boolean isDebug = false;
	
	public static void isDebug(boolean debug) {
		isDebug = debug;
	}
	
	public static void v(String tag, String msg) {
		if(isDebug) {
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, String msg, Throwable tr) {
		if(isDebug) {
			Log.v(tag, msg, tr);
		}
	}
	
	public static void d(String tag, String msg) {
		if(isDebug) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, String msg, Throwable tr) {
		if(isDebug) {
			Log.d(tag, msg, tr);
		}
	}
	
	public static void i(String tag, String msg) {
		if(isDebug) {
			Log.i(tag, msg);
		}
	}
	
	public static void i(String tag, String msg, Throwable tr) {
		if(isDebug) {
			Log.i(tag, msg, tr);
		}
	}
	
	public static void w(String tag, String msg) {
		if(isDebug) {
			Log.w(tag, msg);
		}
	}
	
	public static void w(String tag, String msg, Throwable tr) {
		if(isDebug) {
			Log.w(tag, msg, tr);
		}
	}
	
	public static void e(String tag, String msg) {
		if(isDebug) {
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, Throwable tr) {
		if(isDebug) {
			Log.e(tag, msg, tr);
		}
	}
	
}