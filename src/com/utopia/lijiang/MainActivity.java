package com.utopia.lijiang;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.utopia.lijiang.widget.MenuBarLayout;
import com.utopia.lijiang.widget.OnMenuBarSelectListener;

public class MainActivity extends TabActivity {
	
	final int ADD_POSITIN_TAB_INDEX = 0;
	
	int lastTabIndex = 0;
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
	    menuBar.setButtonSelected(1, true);
	    
	   
	    menuBar.setOnMenuBarSelectListener(new OnMenuBarSelectListener(){

			@Override
			public void onSelected(int index, View v) {
				// TODO Auto-generated method stub
				lastTabIndex = tabHost.getCurrentTab();
				tabHost.setCurrentTab(index);
				
			}});
	    
	}
}
