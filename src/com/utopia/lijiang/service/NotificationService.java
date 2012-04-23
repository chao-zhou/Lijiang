package com.utopia.lijiang.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.utopia.lijiang.MainActivity;
import com.utopia.lijiang.R;
import com.utopia.lijiang.util.NotificationUtil;

/** A service contain method for send notification
 * */
public class NotificationService extends Service {

	final static int id = R.string.foreground;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateNotification(String msg){
		Log.d(getString(R.string.debug_tag),"Update Notification Message:"+msg);
		
		if(msg == null){
			NotificationUtil.cancelNotification(this, id);
			return;
		}
		
		Notification notification = createNotification(msg);
		NotificationUtil.updateNotification(this, id, notification);
	}
	
	private PendingIntent getContentIntent(){
		 Intent i = new Intent(this,MainActivity.class); 
	     PendingIntent contentIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_CANCEL_CURRENT);
	     return contentIntent;
	}
	
	private Notification createNotification(String msg){
		PendingIntent contentIntent = getContentIntent();	
		return	NotificationUtil.createNotification(this,getText(R.string.foreground),msg,contentIntent);
	}
	
	
	protected void toForegroud(String msg){
		Notification notification = createNotification(msg);
		startForeground(R.string.foreground,notification);
	}
	
	protected void toBackground(){
		this.stopForeground(true);
	}
}
