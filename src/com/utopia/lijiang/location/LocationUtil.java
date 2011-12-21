package com.utopia.lijiang.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class LocationUtil {

	private static final int DELTA_SECOND = 1000 * 15;
	
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  */
	public static boolean isBetterLocation(Location location, Location currentBestLocation){
		return isBetterLocation(location, currentBestLocation,DELTA_SECOND);
	}
	
	/** Determines whether one Location reading is better than the current Location fix
	  * @param location  The new Location that you want to evaluate
	  * @param currentBestLocation  The current Location fix, to which you want to compare the new one
	  * @param deltaSecond	The delta second, to judge location is newer or older
	  */
  	public static boolean isBetterLocation(Location location, Location currentBestLocation,int deltaSecond) {
  	  if (currentBestLocation == null) {
          // A new location is always better than no location
          return true;
      }

      // Check whether the new location fix is newer or older
      long timeDelta = location.getTime() - currentBestLocation.getTime();
      boolean isSignificantlyNewer = timeDelta > deltaSecond;
      boolean isSignificantlyOlder = timeDelta < -deltaSecond;
      boolean isNewer = timeDelta > 0;

      // If it's been more than deltaSecond since the current location, use the new location
      // because the user has likely moved
      if (isSignificantlyNewer) {
          return true;
      // If the new location is more than deltaSecond older, it must be worse
      } else if (isSignificantlyOlder) {
          return false;
      }

      // Check whether the new location fix is more or less accurate
      int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
      boolean isLessAccurate = accuracyDelta > 0;
      boolean isMoreAccurate = accuracyDelta < 0;
      boolean isSignificantlyLessAccurate = accuracyDelta > 200;

      // Check if the old and new location are from the same provider
      boolean isFromSameProvider = isSameProvider(location.getProvider(),
              currentBestLocation.getProvider());

      // Determine location quality using a combination of timeliness and accuracy
      if (isMoreAccurate) {
          return true;
      } else if (isNewer && !isLessAccurate) {
          return true;
      } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
          return true;
      }
      return false;
	}

	/** Checks whether two providers are the same */
  	public static boolean isSameProvider(String provider1, String provider2) {
	    if (provider1 == null) {
	      return provider2 == null;
	    }
	    return provider1.equals(provider2);
	}
  	
  	public static String getLocationMessage(Location location){
		if(location !=null){
			return location.toString();
		}else{
			return "No Location Info";
		}				
  	}

  	public static Location createLijingLocation(
  			double latitude, double longitude, double altitude,float accuracy){
  		Location loc = new Location("com.utopia.lijiang");
  		loc.setLatitude(latitude);
  		loc.setLongitude(longitude);	
  		loc.setAltitude(altitude);
  		loc.setAccuracy(accuracy);
  		return loc;
  	}

  	public static Location getBestLastKnowLocation(Context context){
  		LocationManager locMgr = 
     			(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
     	
     	Location locGPS = getLastGPSKnownLocation(locMgr);
     	Location locNetWork = getLastNetworkKnownLocation(locMgr);
     	
     	if(isBetterLocation(locGPS, locNetWork)){
     		return locGPS;
     	}else{
     		return locNetWork;
     	}
  	}


  	
    private static Location getLastGPSKnownLocation(LocationManager locMgr){
     	return locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
     }
     
    private static Location getLastNetworkKnownLocation(LocationManager locMgr){
     	return locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
     }
}















































