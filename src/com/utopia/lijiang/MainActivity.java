package com.utopia.lijiang;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.utopia.lijiang.widget.MenuBarLayout;
import com.utopia.lijiang.widget.OnMenuBarSelectListener;

public class MainActivity extends TabActivity {
	
	public final static int ADD_POSITION_TAB_INDEX = 0;
	final static int ANIMATIION_DURATION = 300;	
	static MainActivity instance = null;
		
	View lastView = null;
	MenuBarLayout menuBar = null;
	TabHost tabHost = null;
	
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
				 View currentView = tabHost.getCurrentView();
				 lastView.setAnimation(outToLeftAnimation());
			     currentView.setAnimation(inFromRightAnimation());
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
		
	private Animation inFromRightAnimation() {

		    Animation inFromRight = new TranslateAnimation(
		            Animation.RELATIVE_TO_PARENT, +1.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f);
		    inFromRight.setDuration(ANIMATIION_DURATION);
		    inFromRight.setInterpolator(new AccelerateInterpolator());
		    return inFromRight;
	}

	private Animation outToLeftAnimation() {
		    Animation outtoLeft = new TranslateAnimation(
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, -1.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f);
		    outtoLeft.setDuration(ANIMATIION_DURATION);
		    outtoLeft.setInterpolator(new AccelerateInterpolator());
		    return outtoLeft;
	}
}
