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
public class AlarmManager extends BaseAlarmManager {
	
	private static AlarmManager instance = null;
	public static AlarmManager getInstance(){
		if(instance == null){
			instance = new AlarmManager();
		}
		return instance;
	}

	public List<Alarm> getAlarmingAlarms(){
		ArrayList<Alarm> alarmingAlarms = new ArrayList<Alarm>();
		Iterator<Alarm> it = alarms.iterator();	
		while(it.hasNext()){
			Alarm alarm = it.next();
			if(alarm.isActive()
			&& alarm.shouldAlarm(Status.getCurrentStatus())){
				alarmingAlarms.add(alarm);
			}
		}
		
		return alarmingAlarms;
	}
	
	public int alarmAllPossible(){
		List<Alarm> alarmingAlamrs = getAlarmingAlarms();
		alarm(alarmingAlamrs.toArray(new Alarm[alarmingAlamrs.size()]));
		return alarmingAlamrs.size();
	}
	
	public void alarm(Alarm[] alarm){
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
	
	
	//---------------------------------------
	// Load/Save alarms to Database
	//---------------------------------------
	
	public <T extends Alarm> void load4DB(Context context, Class<T> clazz){
		DBHelper helper = getDBHelper(context);		
		clearAll();
		
		int count = 0;
		try {
			helper.openConnectionSource();
			Iterator<T> iterator = helper.read(clazz);
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
		DBHelper helper = getDBHelper(context);
		try {
			helper.openConnectionSource();	
			for(Alarm item : alarms){
				helper.save(item);
			}
			
			for(Alarm item : removedAlarms){
				helper.delete(item);
				removedAlarms.remove(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			helper.closeConnection();
		}
	}
	
	public void delete2DB(Context context){
		DBHelper helper = getDBHelper(context);
		try {
			helper.openConnectionSource();
			for(Alarm item : removedAlarms){
				helper.delete(item);
				removedAlarms.remove(item);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			helper.closeConnection();
		}
	}
	
	private DBHelper getDBHelper(Context context){
		String dbVersion = context.getString(R.string.dbVersion);
		DBHelper helper = new DBHelper(context,null,Integer.parseInt(dbVersion));
		Log.d("lijiang",dbVersion);
		return helper;
	}
	
	private void clearAll(){
		alarms.clear();
		activeAlarms.clear();
		historyAlarms.clear();
		removedAlarms.clear();
	}

	
}
