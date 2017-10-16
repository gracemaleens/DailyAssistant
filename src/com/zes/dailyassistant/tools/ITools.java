package com.zes.dailyassistant.tools;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;

public class ITools {
	private static final String TAG = ITools.class.getName();
	
	/**
	 * 获取应用自身资源ID
	 * @param context
	 * @param type 资源类型
	 * @param name 资源名字
	 * @return 资源ID
	 */
	public static int getResourceId(Context context, String type, String name){
		Resources res = null;
		String pName = context.getPackageName();
		try {
			res = context.getPackageManager().getResourcesForApplication(pName);
			return res.getIdentifier(name, type, pName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取外部应用资源ID
	 * @param context
	 * @param type 资源类型
	 * @param name 资源名字
	 * @param packageName 外部应用包名
	 * @return 资源ID
	 */
	public static int getResourceId(Context context, String type, String name, String packageName){
		Resources res = null;
		try {
			res = context.getPackageManager().getResourcesForApplication(packageName);
			return res.getIdentifier(name, type, packageName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获得栈顶应用程序
	 * @param context
	 * @return 应用程序包名
	 */
	@TargetApi(21)
	public static String getLauncherTopApp(Context context) {  
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {  
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);  
            List<ActivityManager.RunningTaskInfo> appTasks = activityManager.getRunningTasks(1);  
            if (null != appTasks && !appTasks.isEmpty()) {  
                return appTasks.get(0).topActivity.getPackageName();  
            }
            return "";
        } else {  
        	Calendar calendar = Calendar.getInstance();
        	calendar.setTime(new Date());
        	long endTime = calendar.getTimeInMillis();
        	calendar.add(Calendar.DAY_OF_YEAR, -1);
        	long beginTime = calendar.getTimeInMillis();
        	
        	UsageStatsManager uStatsManager = (UsageStatsManager)context.getSystemService("usagestats");
        	List<UsageStats> usageStatsList = uStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY, beginTime, endTime);
        	
        	if(usageStatsList != null && !usageStatsList.isEmpty()){
        		int index = 0;
        		for(int i = 0; i < usageStatsList.size(); i++){
        			if(usageStatsList.get(index).getLastTimeUsed() < usageStatsList.get(i).getLastTimeUsed()){
        				index = i;
        			}
        		}
        		return usageStatsList.get(index).getPackageName();
        	}
        }  
        return "";  
    }
}
