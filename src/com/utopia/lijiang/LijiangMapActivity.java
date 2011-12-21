package com.utopia.lijiang;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class LijiangMapActivity extends MapActivity {

	MapView mapView = null;
	Geocoder geoCoder = null;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.map);
		
		mapView = (MapView)findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setStreetView(true);
		
		geoCoder = new Geocoder(this, Locale.CHINA);
		mapView.setTraffic(true);
		mapView.setSatellite(false);
		mapView.setStreetView(true);
		
		mapView.setBuiltInZoomControls(true);
	        
	    GeoPoint geoBeijing=new GeoPoint((int)(39.95*1000000), (int)(116.37*1000000));
	    mapView.getController().setCenter(geoBeijing);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	private String getLocationName(){
		TextView location = (TextView)findViewById(R.id.location);
		return location.getText().toString();
	}
	
	public void btnMapSearchClickHandler(View target){		
		try{
			String locationName = getLocationName();
			List<Address> addressList = geoCoder.getFromLocationName(locationName, 5);
			if(addressList !=null && addressList.size()>0){
				int lat = (int)(addressList.get(0).getLatitude()*1000000);
				int lng = (int)(addressList.get(0).getLongitude()*1000000);
				GeoPoint pt = new GeoPoint(lat,lng);
				mapView.getController().setZoom(10);
				mapView.getController().setCenter(pt);
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
