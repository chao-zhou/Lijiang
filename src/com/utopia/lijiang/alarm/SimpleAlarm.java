package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

public class SimpleAlarm implements Alarm {

	private String title = null;
	private String message = null;
	private boolean active = false;
	
	public SimpleAlarm(String title,String message,boolean active){
		this.title = title;
		this.message = message;
		this.active = active;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
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

}
