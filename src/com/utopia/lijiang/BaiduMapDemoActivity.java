package com.utopia.lijiang;

import android.os.Bundle;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;

public class BaiduMapDemoActivity extends BaiduMapActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baidumapdemo);

		MapView mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);  
		 
		MapController mMapController = mMapView.getController();  // 
		GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
		        (int) (116.404 * 1E6));
		mMapController.setCenter(point); 
		mMapController.setZoom(12);
		
	}

}
