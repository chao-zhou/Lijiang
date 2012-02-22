package com.utopia.lijiang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.LocationAlarm;
/** The activity for add and view SimpleAlarm
 * Should be removed in release version
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class AddAlarmActivity extends Activity {

	 public final static String ALARM_TITLE = "alarm_title";
	 public final static String ALARM_MESSAGE = "alarm_message";
	 public final static String ALARM_LONGITUDEE6 = "alarm_longitude";
	 public final static String ALARM_LATITUDEE6 = "alarm_latitude";
	 
	 private EditText alarmTitle = null;
	 private EditText alarmMessage = null;

	 private double longitude = -1.0;
	 private double latidude = -1.0;
	 private Alarm alarm = null;
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	    	
	    	super.onCreate(savedInstanceState);     	
	    	setContentView(R.layout.add_alarm);
	    	
	    	alarmTitle = (EditText)findViewById(R.id.addAlarmTitle);
	    	alarmMessage = (EditText)findViewById(R.id.addAlarmMsg);
	    }  
	 
	 @Override
	 public void onStart(){
		 super.onStart();
		 Intent intent = this.getIntent();
		 getAlarmFromIntent(intent);
		 
		 viewAlarm(alarm);
	 }
	 
	 private void getAlarmFromIntent(Intent intent){
		 alarmTitle.setText(intent.getStringExtra(ALARM_TITLE));
		 alarmMessage.setText(intent.getStringExtra(ALARM_MESSAGE));
		 longitude = intent.getDoubleExtra(ALARM_LONGITUDEE6, -1.0);
		 latidude = intent.getDoubleExtra(ALARM_LATITUDEE6, -1.0);
	 }
	 
	 private void viewAlarm(Alarm alarm){
		 if(alarm == null){
			 return;
		 }
		 
		 alarmTitle.setText(alarm.getTitle());
		 alarmMessage.setText(alarm.getMessage());
		 
	 }
	 
	 public void saveAlarm(View target){
		
		 String title = alarmTitle.getText().toString();
		 String message = alarmMessage.getText().toString();
		 
		 alarm = new LocationAlarm(title,message,longitude,latidude);
		 AlarmManager.getInstance().addAlarm(alarm);
		 AlarmManager.getInstance().save2DB(this);
		 
		 this.finish();
	 }
	 
	 public void cancelAddAlarm(View target){
		this.finish();
	 }
}
