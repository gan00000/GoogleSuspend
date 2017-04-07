package com.zero.service;

import java.util.List;

import com.zero.defenders.UnityPlayerNativeActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShowNotificationReceiver extends  BroadcastReceiver{
	public static String packgename="com.zero.defenders";
	Context text;
	@Override
	public void onReceive(Context context, Intent intent) {
		text=context;
        if(isRunningApp())
		{
        	Log.e("unity", "点击时开启。。。。");
    		Intent inten=new Intent();
    		inten.setClass(text, UnityPlayerNativeActivity.class);
    		inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    		text.getApplicationContext().startActivity(inten);
		}
		else if(!isRunningApp())
		{
			Log.e("unity", "点击是关闭、。。。");
			Intent launchIntent=context.getPackageManager().getLaunchIntentForPackage(packgename);
			launchIntent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			context.startActivity(launchIntent);
		}
	}
	
	 boolean isRunningApp()
    {
    	boolean isAppRunning=false;
    	ActivityManager am = (ActivityManager)text.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(1000);
        for(RunningTaskInfo info:list)
        {
        	if(info.topActivity.getPackageName().equals(ShowNotificationReceiver.packgename))
        	{
        		isAppRunning=true;
        	    break;
        	}
        }
        return isAppRunning;
    }

}
