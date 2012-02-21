package com.utopia.lijiang.view;


import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;


public class SafeProgressDialog extends ProgressDialog {
	
	int millSeconds = -1;
	Handler handler = null;
	Timer timer = null;

	public SafeProgressDialog(Context context,int ms) {
		super(context);
		// TODO Auto-generated constructor stub
		millSeconds = ms;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
		StratTimer();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
		timer.cancel();
	}

	protected void StratTimer()
	{
		if(millSeconds < -1){
			return;
		}
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(SafeProgressDialog.this.isShowing()){
					SafeProgressDialog.this.cancel();
				}
				super.handleMessage(msg);
			}
			
		};
		
		timer = new Timer();
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendMessage(new Message());
			}	
		}, millSeconds);
	}
	
}
