package com.utopia.lijiang.alarm;

import com.utopia.lijiang.global.Status;

import android.location.Location;

public class LocationAlarm implements Alarm{
	public static float Range = 200; //200 meters
	
	private int id = 0;
	private double longitude = 0;
	private double latitude = 0;
	private String message = null;
	
	public LocationAlarm(Location location){
		initial(location.getLongitude(),location.getLatitude());
	}

	public LocationAlarm(double longitude,double latitude){
		initial(longitude,latitude);
	}
	
	protected void initial(double longitude,double latitude){
		this.longitude = longitude;
		this.latitude = latitude;
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
}
