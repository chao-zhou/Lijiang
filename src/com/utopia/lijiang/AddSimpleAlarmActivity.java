package com.utopia.lijiang;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.SimpleAlarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddSimpleAlarmActivity extends Activity {

	 public static String ALARM_LOCATION = "alarm_location";
	
	 private EditText alarmTitle = null;
	 private EditText alarmMessage = null;
	 private CheckBox alarmActive = null;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	    	
	    	super.onCreate(savedInstanceState);     	
	    	setContentView(R.layout.add_alarm);
	    	
	    	alarmTitle = (EditText)findViewById(R.id.addAlarmTitle);
	    	alarmMessage = (EditText)findViewById(R.id.addAlarmMsg);
	    	alarmActive = (CheckBox)findViewById(R.id.addAlarmActive);
	    }  
	 
	 @Override
	 public void onStart(){
		 super.onStart();
		 Intent intent = this.getIntent();
		 Alarm alarm = getAlarmFromIntent(intent);
		 
		 viewAlarm(alarm);
	 }
	 
	 private Alarm getAlarmFromIntent(Intent intent){
		 int location = intent.getIntExtra(ALARM_LOCATION, -1);
		 
		 if(location < 0){
			 return null;
		 }
		 
		 return AlarmManager.getInstance().getAlarm(location);
	 }
	 
	 private void viewAlarm(Alarm alarm){
		 if(alarm == null){
			 return;
		 }
		 
		 alarmTitle.setText(alarm.getTitle());
		 alarmMessage.setText(alarm.getMessage());
		 alarmActive.setChecked(alarm.isActive());
		 
	 }
	 
	 public void addAlarm(View target){
		 String title = alarmTitle.getText().toString();
		 String message = alarmMessage.getText().toString();
		 boolean active = alarmActive.isChecked();
		 
		 Alarm alarm = new SimpleAlarm(title,message,active);
		 AlarmManager.getInstance().addAlarm(alarm);
		 this.finish();
	 }
	 
	 public void clearAlarmInfo(View target){
		 alarmTitle.setText("");
		 alarmMessage.setText("");
		 alarmActive.setChecked(false);
	 }
}
