package com.utopia.lijiang.alarm;

import com.j256.ormlite.field.DatabaseField;
import com.utopia.lijiang.global.Status;

/** The activity for add and view SimpleAlarm
 * Should be removed in release version
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class SimpleAlarm implements Alarm {

	@DatabaseField(generatedId = true)
	private int id = 0;
	
	@DatabaseField
	private String title = null;
	
	@DatabaseField
	private String message = null;
	
	@DatabaseField
	private boolean active = false;
	
	public SimpleAlarm(){
		
	}
	
	public SimpleAlarm(String title,String message,boolean active){
		this.title = title;
		this.message = message;
		this.active = active;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public boolean shouldAlarm(Status status) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		this.message = message;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		this.active = active;
	}

}
