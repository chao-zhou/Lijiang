package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

/** The interface for all alarm this application
 * All alarm should implement this interface.
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public interface Alarm {
	public int getId();
	public String getTitle();
	public void setTitle(String title);
	public String  getMessage();
	public void setMessage(String message);
	public boolean isActive();
	public void setActive(boolean active);
	
	
	/**This alarm should be alarm or not
	 * AlarmManager will call this method when global status is modified.
	 * If this method return true, a Android notification will be rose.
	 * @param status Application status to judge should be alarm
	 * */
	public boolean shouldAlarm(Status status);
	
}
