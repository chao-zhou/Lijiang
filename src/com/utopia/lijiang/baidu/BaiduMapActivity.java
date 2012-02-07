package com.utopia.lijiang.baidu;

import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;

public abstract class BaiduMapActivity extends MapActivity {

	public abstract MapView getMapView();
	public abstract boolean onTapped(int i, OverlayItem item);
	public abstract void onTapping(GeoPoint pt, MapView v);
	
	protected abstract int getConentViewId();
	
	protected BMapManager mBMapMan = null;	

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
        setContentView(this.getConentViewId());
        
		if (mBMapMan == null) {
			mBMapMan = new BMapManager(getApplication());
			mBMapMan.init("DBC116220F2040B76081BF632757104D2960CF1A", null);
		}
		mBMapMan.start();
        super.initMapActivity(mBMapMan);
	}

	@Override
	protected void onPause() {
		mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
