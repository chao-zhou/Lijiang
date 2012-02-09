package com.utopia.lijiang.global;

import java.util.Observable;

import android.location.Location;
import android.util.Log;

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
		
		Log.d("lijiang","location changed");
		this.setChanged();
		this.notifyObservers();
	}

	
}
