package com.utopia.lijiang.service;

import com.utopia.lijiang.LijiangActivity;
import com.utopia.lijiang.R;
import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmListener;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.location.LocationListener;
import com.utopia.lijiang.util.NotificationUtil;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

	static LocationService instance = null;
	static int minTime = 15*1000;
	static int minMeters = 200;
	
	LocationManager locationManager = null;
	LocationListener gpsListener = null;
	LocationListener networkListener = null;
	AlarmListener alarmListener = null;
	
	public static LocationService getLatestInstance(){
		return instance;
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d(getString(R.string.debug_tag),"Create LocationService");
		initialVariables();
		bindListener();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(getString(R.string.debug_tag),"Start LocationService");
	    return START_STICKY;
	}
	
	@Override
	public void onDestroy(){
		locationManager.removeUpdates(gpsListener);
		locationManager.removeUpdates(networkListener);
		AlarmManager.getInstance().removeAlarmListener(alarmListener);
	}
	
	public void updateNotification(String msg){
		Log.d(getString(R.string.debug_tag),"Update Notification Message:"+msg);
		
		PendingIntent contentIntent = getContentIntent();
		Notification notification = 
				NotificationUtil.createNotification(this,getText(R.string.foreground),msg,contentIntent);
		NotificationUtil.updateNotification(this, R.string.foreground, notification);
	}
	
	private PendingIntent getContentIntent(){
		 Intent i = new Intent(this,LijiangActivity.class); 
	     PendingIntent contentIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
	     return contentIntent;
	}
	
	private void initialVariables(){
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		gpsListener = new LocationListener();
		networkListener = new LocationListener();
		instance = this;
		
	}
	
	private void bindListener(){
		alarmListener = new AlarmListener(){
			@Override
			public void onAlarm(Alarm alarm){
				if(alarm.isSound()){
					
				}
				
				if(alarm.isSound()){
					
				}
				
				if(alarm.isNotification()){
					updateNotification(alarm.getMessage());
				}
			}
		};
		
		AlarmManager.getInstance().addAlarmListener(alarmListener);	
	    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minMeters,gpsListener);
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minMeters,networkListener);
	    
	}
	 
}
