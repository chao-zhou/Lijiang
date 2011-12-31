package com.utopia.lijiang.alarm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.utopia.lijiang.global.Status;

import android.location.Location;

@DatabaseTable
public class LocationAlarm implements Alarm{
	public static float Range = 200; //200 meters
	
	@DatabaseField(id = true)
	private int id = 0;
	
	@DatabaseField
	private String title = null;
	
	@DatabaseField
	private double longitude = 0;
	
	@DatabaseField
	private double latitude = 0;
	
	@DatabaseField
	private String message = null;
	
	@DatabaseField
	private boolean active = false;
	
	public LocationAlarm(){
		
	};
	
 	public LocationAlarm(String title,Location location){
		initial(title,location.getLongitude(),location.getLatitude());
	}

	public LocationAlarm(String title,double longitude,double latitude){
		initial(title,longitude,latitude);
	}
	
	protected void initial(String title,double longitude,double latitude){
		this.title = title;
		this.longitude = longitude;
		this.latitude = latitude;
		this.active = true;
	}
	
	protected boolean isNear(double longitude2,double latitude2){
		double distance  = getDistance(longitude2,latitude2);
		
		if (distance <= Range){
			setMessage("Near Longitude:"+longitude+" Latidude:"+latitude);
			return true;
		}else{
			return false;
		}
	}

	protected double getDistance(double longitude2,double latitude2){
		double longDistance = longitude - longitude2;
		double latiDistance = latitude - latitude2;
		double distance =
				Math.sqrt(Math.pow(longDistance,2.0) + Math.pow(latiDistance,2.0));
		
		return distance;
	}
	
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public void setMessage(String msg){
		message = msg;
	}
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean shouldAlarm(Status status) {
		// TODO Auto-generated method stub
		Location loc = status.getLocation();
		return isNear(loc.getLongitude(),loc.getLatitude());
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		this.active = active;
	}

}
