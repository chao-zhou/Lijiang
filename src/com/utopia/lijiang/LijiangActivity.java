package com.utopia.lijiang;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmManager;
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
    	
    }    
    
    
    /**Called when the activity is active*/
    @Override
    public void onStart(){
    	Log.d(getString(R.string.debug_tag),"Start LijiangActivity ");
    	super.onStart();

    	initialAlarmList();
    }
    
    /**Called when the activity is finished*/
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	super.onDestroy();
    }   
     
    /** Do work after showAddAlarm button is clicked
     * @param target trigger
     * */
    public void showAddAlarmActivity(View target){
   	 //Just test Map 
		 Intent i = new Intent(this,BaiduMapDemoActivity.class);
		 this.startActivity(i);
    	//this.startActivity(new Intent(this,AddSimpleAlarmActivity.class));
    }
    
    public void showAlarms(View target){
    	List<Alarm> alarms = getActiveAlarms();
    	bindList(alarms);
    }
    
    public void showHistory(View target){
    	List<Alarm> alarms = getHistoryAlarms();
    	bindList(alarms);
    }
    
    private void showEmptyUI(){
    	
    }
    
    private void initialAlarmList(){
    	if(getActiveAlarms().size()>0){
    		showAlarms(null);
    	}else{
    		showEmptyUI();
    	}
    }
    
    /**Fill Data into Task List*/
	private void bindList(List<Alarm> alarms){ 
	       AlarmAdapter adapter = 
	    		   new AlarmAdapter(this,alarms);
	       setListAdapter(adapter);
	}
	
	private List<Alarm> getActiveAlarms(){
		List<Alarm> alarms = AlarmManager.getInstance().getAllAlarm();
		ArrayList<Alarm> ret = new ArrayList<Alarm>();
		for(Alarm alarm : alarms){
			if(alarm.isActive()){
				ret.add(alarm);
			}
		}
		return ret;
	}
	
	private List<Alarm> getHistoryAlarms(){
		List<Alarm> alarms = AlarmManager.getInstance().getAllAlarm();
		ArrayList<Alarm> ret = new ArrayList<Alarm>();
		for(Alarm alarm : alarms){
			if(!alarm.isActive()){
				ret.add(alarm);
			}
		}
		return ret;
	}
	
}