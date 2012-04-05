package com.utopia.lijiang.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.util.Log;

public abstract class BaseAlarmManager implements Observer{

	protected List<Alarm> alarms = null;
	protected List<Alarm> activeAlarms = null;
	protected List<Alarm> historyAlarms = null;
	protected List<Alarm> removedAlarms = null;
	protected List<AlarmListener> alListeners = null;
	
	public abstract int alarmAllPossible();
	public abstract void alarm(Alarm[] alarm);
	
	
	public BaseAlarmManager(){
		alarms = new ArrayList<Alarm>();
		activeAlarms = new ArrayList<Alarm>();
		historyAlarms = new ArrayList<Alarm>();
		removedAlarms = new ArrayList<Alarm>();
		alListeners = new ArrayList<AlarmListener>();
	}

	public List<Alarm> getAllAlarms(){
		return alarms;
	}
	
	public List<Alarm> getActiveAlarms(){
		activeAlarms.clear();
		for(Alarm alarm : alarms){
			if(alarm.isActive()){
				activeAlarms.add(alarm);
			}
		}
		return activeAlarms;
	}
	
	public List<Alarm> getHistoryAlarms(){
		historyAlarms.clear();
		for(Alarm alarm : alarms){
			if(!alarm.isActive()){
				historyAlarms.add(alarm);
			}
		}
		return historyAlarms;
	}
	
	public void addAlarm(Alarm alarm){
		alarms.add(alarm);
	}
		
	public int getLocation(Alarm alarm){
		return alarms.indexOf(alarm);
	}
	
	public Alarm getAlarm(int location){
		return alarms.get(location);
	}
	
	public Alarm removeAlarm(int location){
		 Alarm alarm = alarms.remove(location);
		 removedAlarms.add(alarm);
		 return alarm;
	}
	
	public boolean removeAlarm(Object object){
		removedAlarms.add((Alarm) object);
		return alarms.remove(object);
	}
	
	public int addAlarmListener(AlarmListener al){
		Log.d("lijiang","add AlarmListener");
		
		boolean isAdded = alListeners.add(al);
		
		if(isAdded){
			int lastIndex = alListeners.size() -1; 
			return lastIndex; 
		}else{
			return -1;
		}
	}
	
	public AlarmListener removeAlarmListener(int location){
		return alListeners.remove(location);
	}
	
	public boolean removeAlarmListener(Object object){
		return alListeners.remove(object);
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		alarmAllPossible();	
	}
		
	public void reset(){
		alarms.clear();
	}
}
