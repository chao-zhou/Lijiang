package com.utopia.lijiang;

import android.util.Log;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MapActivity;


public class BaiduMapActivity extends MapActivity {
	protected BMapManager mBMapMan = null;
	protected String apiKey = null;
	
	protected void initMapActivity(){
		Log.d(getString(R.string.debug_tag),"BaiduMapActivity.initMapActivity()");
		apiKey = this.getString(R.string.baidu_key);	
		mBMapMan = new BMapManager(this.getApplication());
		mBMapMan.init(apiKey, null);
		super.initMapActivity(mBMapMan);
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onDestroy() {
		Log.d(getString(R.string.debug_tag),"BaiduMapActivity.onDestroy()");
		
	    if (mBMapMan != null) {
	        mBMapMan.destroy();
	        mBMapMan = null;
	    }
	    super.onDestroy();
	}
	@Override
	protected void onPause() {
		Log.d(getString(R.string.debug_tag),"BaiduMapActivity.onPause()");
	    if (mBMapMan != null) {
	        mBMapMan.stop();
	    }
	    super.onPause();
	}
	@Override
	protected void onResume() {
		Log.d(getString(R.string.debug_tag),"BaiduMapActivity.onResume()");
	    if (mBMapMan != null) {
	        mBMapMan.start();
	    }
	    super.onResume();
	}
}
