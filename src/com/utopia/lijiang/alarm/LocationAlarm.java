package com.utopia.lijiang.alarm;

import java.util.Date;

import android.location.Location;

import com.utopia.lijiang.global.Status;
import com.utopia.lijiang.location.InOut;

public class LocationAlarm implements Alarm {

	boolean sound = true;
	boolean shake = true;
	boolean notification = true;
	Location location = null;
	InOut	inOut = InOut.Both;
	Date beginDate = null;
	Date endDate = null;
	
	public InOut getInOut() {
		return inOut;
	}

	public void setInOut(InOut inOut) {
		this.inOut = inOut;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}

	public void setShake(boolean shake) {
		this.shake = shake;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}
	
	public LocationAlarm(Location location,InOut inOut,Date beginDate,Date endDate){
		this.location = location;
		this.inOut = inOut;
		this.beginDate = beginDate;
		this.endDate = endDate;
	} 
	
	
	@Override
	public boolean isSound() {
		// TODO Auto-generated method stub
		return sound;
	}

	@Override
	public boolean isShake() {
		// TODO Auto-generated method stub
		return shake;
	}

	@Override
	public boolean isNotification() {
		// TODO Auto-generated method stub
		return notification;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldAlarm(Status status) {
		// TODO Auto-generated method stub
		return false;
	}

}
