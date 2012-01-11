package com.utopia.lijiang;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.SimpleAlarm;
import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.LocationUtil;
import com.utopia.lijiang.service.LocationService;
import com.utopia.lijiang.util.NotificationUtil;
/** Initial global variables in this application. 
 * Like starting services, reading data from SQLite, configuring settings
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class AppInitializer {

	private Context ctx = null;
	
	/**Run all initial works
	 * @param First loaded context
	 * */
	public static void doWork(Context context){
		AppInitializer instance = new AppInitializer(context);
		instance.doWork();
	}
	
	/**Constructor
	 * @param context First loaded context
	 * */
	private AppInitializer(Context context){
		ctx = context;
	}
	
	/**Run all initial works*/
 	private void doWork(){
		configNotification();
		getLastKnownLocation();
		loadAlarm();
		startLocationService();
	}
	
 	/**Reload Alarm Information from SQLite */
 	private void loadAlarm(){
 		AlarmManager.getInstance().reset();
 		AlarmManager.getInstance().load4DB(ctx, SimpleAlarm.class);
 	}
 	
 	/**Configure Notification's setting*/
	private void configNotification(){
	    	NotificationUtil.setIcon(R.drawable.ic_launcher);
	    	NotificationUtil.setTickerText(ctx.getString(R.string.app_name));
	}
	
	/**Get last known location of device*/
	private Location getLastKnownLocation(){
	    	Location loc = LocationUtil.getBestLastKnowLocation(ctx);	
	    	Status.getCurrentStatus().setLocation(loc);	
	    	return loc;
	}
	
	/**Start Location Service*/
	private void startLocationService(){
	    	Intent intent = new Intent(ctx,LocationService.class);
	    	ctx.startService(intent);  
	}
	
	
}
