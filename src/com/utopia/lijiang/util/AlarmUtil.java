package com.utopia.lijiang.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmUtil {

	static int piIndex = 1;
		
	public static void AttachMessage(Intent intent,String message){
		String key = "message";
		intent.putExtra(key, message);
	}
		
	public static void SendAlarmOnce(Context ctx,Calendar cal, PendingIntent pi){
		AlarmManager alarmManager = (AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
	}
	
}
