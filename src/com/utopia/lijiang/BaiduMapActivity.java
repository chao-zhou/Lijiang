package com.utopia.lijiang;

import android.os.Bundle;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MapActivity;


public class BaiduMapActivity extends MapActivity {
	protected BMapManager mBMapMan = null;
	protected String apiKey = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		apiKey = this.getString(R.string.baidu_key);	
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(apiKey, null);
		super.initMapActivity(mBMapMan);
		
		setContentView(R.layout.baidumapdemo);
	
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	protected void onDestroy() {
	    if (mBMapMan != null) {
	        mBMapMan.destroy();
	        mBMapMan = null;
	    }
	    super.onDestroy();
	}
	@Override
	protected void onPause() {
	    if (mBMapMan != null) {
	        mBMapMan.stop();
	    }
	    super.onPause();
	}
	@Override
	protected void onResume() {
	    if (mBMapMan != null) {
	        mBMapMan.start();
	    }
	    super.onResume();
	}
}
