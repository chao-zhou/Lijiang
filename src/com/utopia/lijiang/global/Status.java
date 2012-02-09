package com.utopia.lijiang.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import android.location.Location;

/**Singleton instance for saving properties. 
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class Status extends Observable{

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
		
		this.setChanged();
		this.notifyObservers();
	}

	
}
