package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

public interface Alarm {
	public int getId();
	public String getTitle();
	public String  getMessage();
	public boolean shouldAlarm(Status status);
	
}
