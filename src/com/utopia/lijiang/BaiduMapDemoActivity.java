package com.utopia.lijiang;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;

import android.location.Location;
import android.os.Bundle;

public class BaiduMapDemoActivity extends MapActivity {
	
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	LocationListener mLocationListener = null;
	MyLocationOverlay mLocationOverlay = null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.baidumapdemo);
        
		if (mBMapMan == null) {
			mBMapMan = new BMapManager(getApplication());
			mBMapMan.init("DBC116220F2040B76081BF632757104D2960CF1A", null);
		}
		mBMapMan.start();
        super.initMapActivity(mBMapMan);
        
        mMapView = (MapView)findViewById(R.id.bmapsView);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setDrawOverlayWhenZooming(true);
        
        mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		
        mLocationListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				if (location != null){
					GeoPoint pt = new GeoPoint((int)(location.getLatitude()*1e6),
							(int)(location.getLongitude()*1e6));
					mMapView.getController().animateTo(pt);
				}
			}
        };
	}

	@Override
	protected void onPause() {
		mBMapMan.getLocationManager().removeUpdates(mLocationListener);
		mLocationOverlay.disableMyLocation();
        mLocationOverlay.disableCompass(); 
		mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
        mBMapMan.getLocationManager().requestLocationUpdates(mLocationListener);
        mLocationOverlay.enableMyLocation();
        mLocationOverlay.enableCompass(); 
		mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}