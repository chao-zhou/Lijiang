package com.utopia.lijiang;

import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.LocationUtil;
import com.utopia.lijiang.service.LocationService;
import com.utopia.lijiang.util.NotificationUtil;

public class AppInitializer {

	private Context ctx = null;
	
	public static void doWork(Context context){
		AppInitializer instance = new AppInitializer(context);
		instance.doWork();
	}
	
	public AppInitializer(Context context){
		ctx = context;
	}
	
 	private void doWork(){
		configNotification();
		configLastKnownLocation();
		startLocationService();
	}
	  
	private void configNotification(){
	    	NotificationUtil.setIcon(R.drawable.ic_launcher);
	    	NotificationUtil.setTickerText(ctx.getString(R.string.app_name));
	}
	  
	private Location configLastKnownLocation(){
	    	Location loc = LocationUtil.getBestLastKnowLocation(ctx);	
	    	Status.getCurrentStatus().setLocation(loc);	
	    	return loc;
	}
	    
	private void startLocationService(){
	    	Intent intent = new Intent(ctx,LocationService.class);
	    	ctx.startService(intent);  
	}
}
