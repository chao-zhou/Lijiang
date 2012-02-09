/**
 * 
 */
package com.utopia.lijiang;

import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.SimpleAlarm;
import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.LocationUtil;
import com.utopia.lijiang.service.LocationService;
import com.utopia.lijiang.util.NotificationUtil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

/**Initial global variables in this application. 
 * Like starting services, reading data from SQLite, configuring settings
 * @author chao_zhou
 * @version 1.0.0.0
 */
public class LijiangApp extends Application {

	private Context ctx = null;
	
	/**
	 * 
	 */
	public LijiangApp() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void onCreate() {
	
		ctx = getApplicationContext();
		
		configNotification();
		getAlarmsFromDB();
		getLastKnownLocation();	
		startLocationService();
		
		super.onCreate();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

 	/**Reload Alarm Information from SQLite */
 	private void getAlarmsFromDB(){
 		AlarmManager.getInstance().reset();
 		AlarmManager.getInstance().load4DB(ctx, SimpleAlarm.class);
 		Status.getCurrentStatus().addObserver(AlarmManager.getInstance());
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
