package com.utopia.lijiang.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

public class NotificationUtil {
	
	private static int icon = 0;
	private static CharSequence tickerText = "lijiang";
	
	public static int getIcon() {
		return icon;
	}

	public static void setIcon(int icon) {
		NotificationUtil.icon = icon;
	}

	public static CharSequence getTickerText() {
		return tickerText;
	}

	public static void setTickerText(CharSequence tickerText) {
		NotificationUtil.tickerText = tickerText;
	}

	
	public static Notification createDefualtNotification(){
		long when = System.currentTimeMillis();
		Notification notification =  new Notification(icon, tickerText, when);
		
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		return notification;
	}
	
	public static Notification createNotification(
			Context context,CharSequence contentTitle,CharSequence contentText,PendingIntent contentIntent){
		
		Notification notification = createDefualtNotification();
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		return notification;
	}
	
	public static void updateNotification(Context context, int id,Notification notification){
		NotificationManager notificationManger = getNotificationManager(context);
		notificationManger.notify(id,notification);
	}
	
	public static void cancelNotification(Context context,int id){
		NotificationManager notificationManger = getNotificationManager(context);
		notificationManger.cancel(id);
	}
	
	public static NotificationManager getNotificationManager(Context context){
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}
}
