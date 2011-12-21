package com.utopia.lijiang.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class IntentUtil {
	
	static int piId = 0;
	
	public static int getPendingIntentId() {
		return piId;
	}

	public static void setPendingIntentId(int piId) {
		IntentUtil.piId = piId;
	}

	public static int getNextPendingIntentId(){
		piId++;
		return piId;
	}
	
	public static PendingIntent getDistinctPendingIntent(Context context, Intent intent){	
		return getDistinctPendingIntent(context,intent,0);
	};
	
	public static PendingIntent getDistinctPendingIntent(Context context, Intent intent,int flag){
		int id = getNextPendingIntentId();
		PendingIntent pi = PendingIntent.getBroadcast(
				context, 
				id, 
				intent, 
				flag);
		return pi;
	}
}
