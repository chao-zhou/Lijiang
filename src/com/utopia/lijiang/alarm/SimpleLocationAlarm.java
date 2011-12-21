package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

import android.location.Location;

public class SimpleLocationAlarm implements Alarm{
	public static float Range = 200; //200 meters
	
	private Location target = null;
	
	public Location getTarget() {
		return target;
	}

	public void setTarget(Location location) {
		this.target = location;
	} 
	
	public SimpleLocationAlarm(Location location){
		target = location;
	}
	
	protected boolean isNear(Location location){
		float distance  = location.distanceTo(target);
		return distance < Range;
	}

	@Override
	public boolean shouldAlarm(Status status) {
		//return isNear(status.getLocation());
		return true;
	}

	@Override
	public boolean isSound() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShake() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isNotification() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Near "+ target.toString();
	}
}
