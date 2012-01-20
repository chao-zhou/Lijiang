package com.utopia.lijiang.alarm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.utopia.lijiang.R;
import com.utopia.lijiang.db.DBHelper;
import com.utopia.lijiang.global.Status;

/**
 * 
 * */
public class AlarmManager {
	
	private static AlarmManager instance = null;
	public static AlarmManager getInstance(){
		if(instance == null){
			instance = new AlarmManager();
		}
		return instance;
	}
	
	private List<Alarm> alarms = null;
	private List<Alarm> activeAlarms = null;
	private List<Alarm> historyAlarms = null;
	private List<AlarmListener> alListeners = null;
	
	public AlarmManager(){
		alarms = new ArrayList<Alarm>();
		activeAlarms = new ArrayList<Alarm>();
		historyAlarms = new ArrayList<Alarm>();
		alListeners = new ArrayList<AlarmListener>();
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
		 return alarms.remove(location);
	}
	
	public boolean removeAlarm(Object object){
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
	
	public int alarmAllPossible(){
		int count = 0;
		Iterator<Alarm> it = alarms.iterator();
		while(it.hasNext()){
			Alarm alarm = it.next();
			if(alarm.isActive()
			&& alarm.shouldAlarm(Status.getCurrentStatus())){
				try{
					alarm(alarm);
					count++;
				}catch(Exception ex){
					Log.d("lijiang","Error Message:"+ex.getMessage());
				}
			}
		}
		return count;
	}
	
	public void alarm(Alarm alarm){
		Iterator<AlarmListener> it = alListeners.iterator();
		while(it.hasNext()){
			AlarmListener al = it.next();
			try{
				al.onAlarm(alarm);
			}catch(Exception ex){
				Log.d("lijiang","Error Message:"+ex.getMessage());
			}
		}
	}
	
	public void reset(){
		alarms.clear();
	}
	
	public <T extends Alarm> void load4DB(Context context, Class<T> clazz){
		String dbVersion = context.getString(R.string.database_version);
		Log.d("lijiang","DB Ver:"+dbVersion);
		
		int count = 0;
		DBHelper helper = new DBHelper(context,null,Integer.parseInt(dbVersion));
		try {
			helper.openConnectionSource();
			Iterator<T> iterator = helper.Read(clazz);
			while(iterator.hasNext()){
				this.addAlarm(iterator.next());
				count++;
			}
			
			Log.d("lijiang","load "+String.valueOf(count)+"records");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			helper.closeConnection();
		}
	}
	
	public void save2DB(Context context){
		String dbVersion = context.getString(R.string.database_version);
		Log.d("lijiang",dbVersion);
		DBHelper helper = new DBHelper(context,null,Integer.parseInt(dbVersion));
		try {
			helper.openConnectionSource();
			
			for(Alarm item : alarms){
				helper.save(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			helper.closeConnection();
		}
	}
}
