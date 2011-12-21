package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

public interface Alarm {
	public boolean isSound();
	public boolean isShake();
	public boolean isNotification();
	public String  getMessage();
	public boolean shouldAlarm(Status status);
	
}
