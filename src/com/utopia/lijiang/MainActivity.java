package com.utopia.lijiang;

import android.app.TabActivity;
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
	
	final int ANIMATIION_DURATION = 300;
	
	int lastTabIndex = 0;
	View lastView = null;
	MenuBarLayout menuBar = null;
	TabHost tabHost = null;
	 
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main_tab);
	   
	    tabHost = getTabHost();  // The activity TabHost
	    menuBar = (MenuBarLayout)this.findViewById(R.id.menubar);
	   
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab
 
	    intent = new Intent().setClass(this, LijiangMapActivity.class);
	    spec = tabHost.newTabSpec("postion").setIndicator("Position").setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, LijiangActivity.class);
	    spec = tabHost.newTabSpec("artists").setIndicator("Artists").setContent(intent);
	    tabHost.addTab(spec);   
	    
	    tabHost.setCurrentTab(1);
	    lastTabIndex = 1;
	    lastView = tabHost.getCurrentView();
	    menuBar.setButtonSelected(1, true);
	    
	   
	    tabHost.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				 View currentView = tabHost.getCurrentView();
				 lastView.setAnimation(outToLeftAnimation());
			     currentView.setAnimation(inFromRightAnimation());
			}
	    	
	    });
	    
	    menuBar.setOnMenuBarSelectListener(new OnMenuBarSelectListener(){

			@Override
			public void onSelected(int index, View v) {
				// TODO Auto-generated method stub
				lastTabIndex = tabHost.getCurrentTab();
				lastView = tabHost.getCurrentView();
				tabHost.setCurrentTab(index);	
			}}); 
	}
	
	public Animation inFromRightAnimation() {

		    Animation inFromRight = new TranslateAnimation(
		            Animation.RELATIVE_TO_PARENT, +1.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f,
		            Animation.RELATIVE_TO_PARENT, 0.0f);
		    inFromRight.setDuration(ANIMATIION_DURATION);
		    inFromRight.setInterpolator(new AccelerateInterpolator());
		    return inFromRight;
	}

	public Animation outToLeftAnimation() {
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
