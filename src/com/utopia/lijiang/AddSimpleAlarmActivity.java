package com.utopia.lijiang;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmManager;
import com.utopia.lijiang.alarm.SimpleAlarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddSimpleAlarmActivity extends Activity {

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
