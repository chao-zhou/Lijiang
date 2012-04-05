package com.utopia.lijiang.alarm;

/** A interface for listen which alarm rises a alarm.
 *  and the receiver do it work here.
 * */
public interface AlarmListener {
	public void onAlarm(Alarm[] alarms);
}
