package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

public interface Alarm {
	public int getId();
	public String getTitle();
	public void setTitle(String title);
	public String  getMessage();
	public void setMessage(String message);
	public boolean isActive();
	public void setActive(boolean active);
	public boolean shouldAlarm(Status status);
	
}
