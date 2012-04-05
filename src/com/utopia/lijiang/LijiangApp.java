/**
 * 
 */
package com.utopia.lijiang;

import android.app.Application;
import android.content.Context;

/**Initial global variables in this application. 
 * Like starting services, reading data from SQLite, configuring settings
 * @author chao_zhou
 * @version 1.0.0.0
 */
public class LijiangApp extends Application {

	@SuppressWarnings("unused")
	private Context ctx = null;
	
	/**
	 * 
	 */
	public LijiangApp() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void onCreate() {	
		ctx = getApplicationContext();	
		//new AppLoadHelper(ctx).load();	
		super.onCreate();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

 
}
