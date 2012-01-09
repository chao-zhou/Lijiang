package com.utopia.lijiang;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmListener;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.global.Status;

import android.app.ListActivity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.content.Intent;
/** Main Activity of the application.
 * When this activity is created, the application's global variables will be initialed.
 * And a location service will be started. 
 * But this service will be not closed, when this activity is finished.
 * 
 * This activity has a singleton method. 
 * So you can get last instance of this activity anywhere in the application.
 * For example: <i>LijiangActivity.getLatestInstance().setTaskInfoText();</i>
 * 
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class LijiangActivity extends ListActivity  {
	
	private static LijiangActivity instance = null;
	private TextView taskInfo = null;
	private Button	networkButton = null;
	private Button	gpsButton = null;
	
	private AlarmListener alarmListener = null;
	
	/**
	 * Singleton method, this will be update after this activity is launched
	 * @return The Latest showed LijiangActivity
	 */
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
    	this.initialVariables();
    	bindToAlarmManager();//Just for developing, should be removed in release version
    	
    }    
    
    /**Called when the activity is active*/
    @Override
    public void onStart(){
    	Log.d(getString(R.string.debug_tag),"Start LijiangActivity ");
    	super.onStart();
    	showLocation();
    	bindList();
    }
    
    /**Called when the activity is finished*/
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	unbindFromAlarmManager();
    	super.onDestroy();
    }     
    
    /** Set All Running Task's Information.
     * @param Task's Information
     * */
    public void setTaskInfoText(String text){
    	 	taskInfo.setText(text);
    }  
    
    /** Do work after showAddAlarm button is clicked
     * @param trigger
     * */
    public void showAddAlarmActivity(View target){
    	this.startActivity(new Intent(this,AddSimpleAlarmActivity.class));
    }
    
    /**Fill Data into Task List*/
	private void bindList(){ 
	       AlarmAdapter adapter = 
	    		   new AlarmAdapter(this,AlarmManager.getInstance().getAllAlarm());
	       setListAdapter(adapter);
	}
    
    //Have no relative with UI
	
	/**Initial Variables used in this activity*/
    private void initialVariables(){
    	instance = this;
    	taskInfo = (TextView)findViewById(R.id.taskInfo);
    	networkButton = (Button)findViewById(R.id.networkButton);
    	gpsButton = (Button)findViewById(R.id.gpsButton);
    	
    	//Location testLoaction = LocationUtil.createLijingLocation(38, -112, 0, 0);	
    	//AlarmManager.getInstance().addAlarm(new LocationAlarm("Test Alarm",testLoaction));
    }
	
	
    /**Bind listener to all alarms. 
     * Just for developing, should be removed in release version
     * */
    private void bindToAlarmManager(){
	    alarmListener = new AlarmListener(){
				@Override
		public void onAlarm(Alarm alarm) {
					String msg = alarm.getMessage();
					setTaskInfoText(msg);
				}
	    };
	    	
	   AlarmManager.getInstance().addAlarmListener(alarmListener);	
	} 
	
  
    
    /** Unbind listener to all alarms 
     * Just for developing, should be removed in release version
     * */
	private void unbindFromAlarmManager(){
	    	AlarmManager.getInstance().removeAlarm(alarmListener);
	    	alarmListener = null;
	}
	
	/** Show location information in task info view
	 * Just for developing, should be removed in release version
	 * */
	private void showLocation(){
    	Location loc = Status.getCurrentStatus().getLocation();	
    	if(loc!=null){
    		setTaskInfoText(loc.toString());
    	}
	}
	

	
}