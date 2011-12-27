package com.utopia.lijiang;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmListener;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.LocationAlarm;
import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.LocationUtil;

import android.app.ListActivity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.content.Intent;

public class LijiangActivity extends ListActivity  {
	
	private static LijiangActivity instance = null;
	private TextView taskInfo = null;
	private Button	networkButton = null;
	private Button	gpsButton = null;
	
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
	
    	AppInitializer.doWork(this);    	
    	initialVariables();
    	bindToAlarmManager();
    	bindList();
    }    
    
    @Override
    public void onStart(){
    	super.onStart();
    	showLocation();
    }
    
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	unbindFromAlarmManager();
    	super.onDestroy();
    }     
    
    public void setText(String text){
    	 	taskInfo.setText(text);
    }  
    
    public void btnMapClickHandler(View target){
    	this.startActivity(new Intent(this,LijiangMapActivity.class));
    }
    
    //Have no relative with UI
    
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
	 
    private void initialVariables(){
    	instance = this;
    	taskInfo = (TextView)findViewById(R.id.taskInfo);
    	networkButton = (Button)findViewById(R.id.networkButton);
    	gpsButton = (Button)findViewById(R.id.gpsButton);
    	
    	Location testLoaction = LocationUtil.createLijingLocation(38, -112, 0, 0);
    	for(int i =0; i < 20; i++){
    	AlarmManager.getInstance().addAlarm(new LocationAlarm("Test Alarm",testLoaction));
    	}
    	
    }
    
	private void unbindFromAlarmManager(){
	    	AlarmManager.getInstance().removeAlarm(alarmListener);
	    	alarmListener = null;
	}
	
	private void showLocation(){
    	Location loc = Status.getCurrentStatus().getLocation();	
    	if(loc!=null){
    		setText(loc.toString());
    	}
	}
	
	private void bindList(){ 
       AlarmAdapter adapter = 
    		   new AlarmAdapter(this,AlarmManager.getInstance().getAllAlarm());
       setListAdapter(adapter);
	}
	
}