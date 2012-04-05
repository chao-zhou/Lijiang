package com.utopia.lijiang.service;

import java.util.List;

import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.util.Log;

import com.utopia.lijiang.R;
import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmListener;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.location.LocationListener;

/**Foreground service for listening location's modification.
 * Location update's work is done by LocatinoListener
 * The works is done by itself is:
 * 	1. bind and unbind notification listener.
 *  2. send notifications when one alarm alarm.
 * */
public class LocationService extends NotificationService {

	static LocationService instance = null;
	static int minTime = 15*1000;
	static int minMeters = 200;
	
	LocationManager locationManager = null;
	LocationListener gpsListener = null;
	LocationListener networkListener = null;
	AlarmManager alarmManager = null;
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
		
		unbindNetLocationListener();
		unbindGPSLocationListener();
		
		AlarmManager.getInstance().removeAlarmListener(alarmListener);
		toBackground();	
		Log.d(getString(R.string.debug_tag),"Destroy LocationService");
	}

	public void bindNetLocationListener(){
		 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,minTime,minMeters,networkListener);
	}
	
	public void bindGPSLocationListener(){
		 locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,minTime,minMeters,gpsListener);
	}
	
	public void unbindNetLocationListener(){
		locationManager.removeUpdates(networkListener);
	}
	
	public void  unbindGPSLocationListener(){
		locationManager.removeUpdates(gpsListener);
	}
	
	public void refreshAlarmNotification(){
		List<Alarm> alarms = alarmManager.getAlarmingAlarms();
		updateAlarmNotification(alarms);
	}
	
	private void initialVariables(){
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		gpsListener = new LocationListener();
		networkListener = new LocationListener();
		alarmManager = AlarmManager.getInstance();
		instance = this;
		
	}
	
	private void bindListener(){
		alarmListener = new AlarmListener(){
			@Override
			public void onAlarm(Alarm[] alarms){
				updateAlarmNotification(alarms);
			}
		};
		
		alarmManager.addAlarmListener(alarmListener);
		
		//Read preferences and to decide which locating listener to bind
		bindNetLocationListener();
		bindGPSLocationListener();
	}
	
	private void updateAlarmNotification(Alarm[] alarms){
		String msg = createNotificationMessage(alarms);
		updateNotification(msg);
	}
	
	private void updateAlarmNotification(List<Alarm> alarms){
		updateAlarmNotification(alarms.toArray(new Alarm[alarms.size()]));
	}
	
	private String createNotificationMessage(Alarm[] alarms){
		String format  = null;	
		if(alarms.length == 0){
			return null;
		}
		
		if(alarms.length == 1){
			format = this.getString(R.string.locatinNearFormat);
			return String.format(format, alarms[0].getTitle());
		}
		
		format = this.getString(R.string.multiLocationNearFormat);
		return String.format(format, alarms.length);
		
	}
}
