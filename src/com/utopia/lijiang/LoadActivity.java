package com.utopia.lijiang;

import com.utopia.lijiang.global.AppLoadHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadActivity extends Activity {
	final static int DISPLAY_SECONDS = 3*1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		postHandler();
	}

	private void postHandler(){
		new Handler().postDelayed(getRunnable(), DISPLAY_SECONDS); 
	}
	
	private Runnable getRunnable(){
		return new Runnable() {
			 	public void run() { 
			 		loading();
			 		startLijiangActivty();
			 		finishLoadActivty();	
	         }};
	}
	
	private void loading(){	
		new AppLoadHelper(this).load();	
	}
	
	private void startLijiangActivty(){
		 Intent mainIntent = new Intent(this, LijiangActivity.class);
		 this.startActivity(mainIntent);
	    
	}
	
	private void finishLoadActivty(){
		 this.finish();
	}
	
}
