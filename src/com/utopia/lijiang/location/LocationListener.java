package com.utopia.lijiang.location;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.utopia.lijiang.global.Status;

public class LocationListener implements android.location.LocationListener {

	@Override
	public void onLocationChanged(Location location) {
			
		// TODO Auto-generated method stub
		// Send Intent there;
		Log.i("lijiang",location.toString());
		
		Location oldLocation = Status.getCurrentStatus().getLocation();
		if(LocationUtil.isBetterLocation(location,oldLocation))
		{
			Status.getCurrentStatus().setLocation(location);
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}	
	
}
