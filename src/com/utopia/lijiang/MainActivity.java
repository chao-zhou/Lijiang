package com.utopia.lijiang;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.utopia.lijiang.widget.MenuBarLayout;
import com.utopia.lijiang.widget.OnMenuBarSelectListener;

public class MainActivity extends TabActivity {
	
	public final static int ADD_POSITION_TAB_INDEX = 0;
	final static int ANIMATIION_DURATION = 450;	
	static MainActivity instance = null;
		
	View lastView = null;
	MenuBarLayout menuBar = null;
	TabHost tabHost = null;
	int currentTabId,lastTabId;
	
	public static MainActivity getInstance(){
		return instance;
	}
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_tab);
	   
	    instance = this;
	    
	    tabHost = getTabHost();  
	    menuBar = (MenuBarLayout)this.findViewById(R.id.menubar);
	   
	    addTab(this,LijiangMapActivity.class,"Position","postion");
	    addTab(this,LijiangActivity.class,"Alarms","alarms");    
	    
	    setCurrentTab(1);
	    
	    tabHost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabId) {
				if(currentTabId > lastTabId){
					MoveRightToLeft();
				}else
				{
					MoveLeftToRigt();
				}
			}	
			
	    });
	    
	    menuBar.setOnMenuBarSelectListener(new OnMenuBarSelectListener(){

			@Override
			public void onSelected(int index, View v) {
				setCurrentTab(index);
			}}); 
	}

	public void setCurrentTab(int index){
		   lastView = tabHost.getCurrentView();
		   lastTabId = currentTabId;
		   
		   currentTabId = index;
		   tabHost.setCurrentTab(index);
		   menuBar.setButtonSelected(index, true);
		   
		   //Is first view
		   if(lastView == null){
			   lastView = tabHost.getCurrentView(); 
		   }
	}
	
	private void addTab(Context ctx, Class<?> cls,String indicator, String tag) {
		Intent intent = new Intent().setClass(ctx, cls);
		TabHost.TabSpec spec = tabHost.newTabSpec(tag).setIndicator(indicator).setContent(intent);
	    tabHost.addTab(spec);
	}
	
	
	/*
	 * Pass the Back Press event to parent
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		showExitNotification();
	}

	private void showExitNotification(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.exit_warning))
		       .setCancelable(true)
		       .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int id) {
					MainActivity.this.finish();
				}})
				.setNegativeButton(getString(R.string.no),  new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}})
				.create()
				.show();
	}
	
	/*
	 * Animation 
	 */	
	private void MoveLeftToRigt(){
		View currentView = tabHost.getCurrentView();
		 lastView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_left_right));
	     currentView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_left_right));
	}
	
	private void MoveRightToLeft(){
		View currentView = tabHost.getCurrentView();
		 lastView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.out_right_left));
	     currentView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_right_left));
	}
	
}
