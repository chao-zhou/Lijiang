package com.utopia.lijiang;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmListener;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.SimpleLocationAlarm;
import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.LocationUtil;
import com.utopia.lijiang.service.LocationService;
import com.utopia.lijiang.util.NotificationUtil;


import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;

public class LijiangActivity extends Activity {
	
	private static LijiangActivity instance = null;
	
	private AlarmListener alarmListener = null;
	
	public static LijiangActivity getLatestInstance(){
		return instance;
	}
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(getString(R.string.debug_tag),"Create LijiangActivity ");
    	
    	super.onCreate(savedInstanceState);     	
    	setContentView(R.layout.main);
    	
    	initial();
    	bindToAlarmManager();
    	getLastKnownLocation();
        startLocationService();
    }    
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	unbindFromAlarmManager();
    	super.onDestroy();
    }     
    public void setText(String text){
    	TextView txtView = (TextView)findViewById(R.id.helloTxt);
    	txtView.setText(text);
    }  
    public void btnMapClickHandler(View target){
    	this.startActivity(new Intent(this,LijiangMapActivity.class));
    }
    
    private void initial(){
    	instance = this;
    	
    	Location testLoaction = LocationUtil.createLijingLocation(38, -112, 0, 0);
    	AlarmManager.getInstance().addAlarm(new SimpleLocationAlarm(testLoaction));
    	
    	NotificationUtil.setIcon(R.drawable.ic_launcher);
    	NotificationUtil.setTickerText(getString(R.string.app_name));
    }
    private void bindToAlarmManager(){
    	alarmListener = new AlarmListener(){
			@Override
			public void onAlarm(Alarm alarm) {
				String msg = alarm.getMessage();
				setText(msg);
			}
    	};
    	
    	AlarmManager.getInstance().addAlarmListener(alarmListener);	
    } 
   
    private void unbindFromAlarmManager(){
    	AlarmManager.getInstance().removeAlarm(alarmListener);
    	alarmListener = null;
    }

    private void getLastKnownLocation(){
    	Location loc = LocationUtil.getBestLastKnowLocation(this);	
    	if(loc!=null){
    		Status.getCurrentStatus().setLocation(loc);
    		setText(loc.toString());
    	}
    }
    private void startLocationService(){
    	Intent intent = new Intent(this,LocationService.class);
        startService(intent);  
    }
}