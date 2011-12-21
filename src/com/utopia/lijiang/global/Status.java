package com.utopia.lijiang.global;

import android.location.Location;

public class Status {

	private static Status currentStatus = null;
	
	public static Status getCurrentStatus(){
		if(currentStatus == null){
			currentStatus = new Status();
		}
			
		return currentStatus;
	}
	
	private Location lastLocation = null;
	private Location location = null;
	
	public Location getLastLocation() {
		return lastLocation;
	}

	protected void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	public Location getLocation(){
		return location;
	}

	public void setLocation(Location l){
		setLastLocation(location);
		location = l;
	}

	
}
