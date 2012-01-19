package com.utopia.lijiang;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.utopia.lijiang.alarm.Alarm;
import com.utopia.lijiang.alarm.AlarmManager;
/** Main Activity of the application.
 * When this activity is created, the application's global variables will be initialed.
 * And a location service will be started. 
 * But this service will be not closed, when this activity is finished.
 * 
 * This activity has a singleton method. 
 * So you can get last instance of this activity anywhere in the application.
 * For example: <i>LijiangActivity.getLatestInstance().setTaskInfoText();</i>
 * 
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class LijiangActivity extends Activity  {
	
	
	private static LijiangActivity instance = null;
	
	private final int STATE_ALARMS = 1;
	private final int STATE_HISTORY = 2;
	
	private ListView listView = null;
	private ToggleButton btnAlarms = null;
	private ToggleButton btnHistory = null;
	private View emptyView = null;
	
	protected final int CMENU_DELETE = 1;
	protected final int CMENU_ACTIVE = 2;

	/**
	 * Singleton method, this will be update after this activity is launched
	 * @return The Latest showed LijiangActivity
	 */
	public static LijiangActivity getLatestInstance(){
		return instance;
	}
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.d(getString(R.string.debug_tag),"Create LijiangActivity ");
    	
    	super.onCreate(savedInstanceState);     	
    	setContentView(R.layout.main);
    	
    	listView = (ListView)this.findViewById(R.id.alarmList);
    	btnAlarms = (ToggleButton)this.findViewById(R.id.btnAlarms);
    	btnHistory = (ToggleButton)this.findViewById(R.id.btnHistory);
    	
    	//You should save that empty view. 
    	//By default, android will setVisible(View.GONE) to that empty view,
    	//so you cannot use "this.findViewById()" to get that view any longer.
    	emptyView = (View)this.findViewById(R.id.alarmListEmpty);
    	
    	registerForContextMenu(listView);
    }    
    
    /**Called when the activity is active*/
    @Override
    public void onStart(){
    	Log.d(getString(R.string.debug_tag),"Start LijiangActivity ");
    	super.onStart();
    	showAlarms(null);
    }
    
    /**Called when the activity is finished*/
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	super.onDestroy();
    }   
        
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	AdapterView.AdapterContextMenuInfo menuInfo 
	     					= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	     		
	    TextView tv = (TextView)menuInfo.targetView.findViewById(R.id.alarmTitle);
	    String title = (String) tv.getText();
	    Log.d(getString(R.string.debug_tag),"onContextItemSelected:"+title);
	    return true;
    }
   
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
    	Alarm alarm = getLongClickedAlarm(menuInfo);
    	
    	if(alarm.isActive()){	
    		createAlarmContextMenu(menu,alarm.getTitle());
    	}else{	
    		createHistoryContextMenu(menu,alarm.getTitle());
    	}	
    }
    
    /** Do work after showAddAlarm button is clicked
     * @param target trigger
     * */
    public void showAddAlarmActivity(View target){
   	 //Just test Map 
     this.startActivity(new Intent(this,AddSimpleAlarmActivity.class));
    }
    
    public void showAlarms(View target){
    	List<Alarm> alarms = getActiveAlarms();
    	
    	changeViewState(STATE_ALARMS);
    	bindList(alarms);
    }
    
    public void showHistory(View target){
    	List<Alarm> alarms = getHistoryAlarms();    	
    	
    	changeViewState(STATE_HISTORY);
    	bindList(alarms);
    }
    
    
    
    
    
    /**Fill Data into Task List*/
	private void bindList(List<Alarm> alarms){ 
	       AlarmAdapter adapter = 
	    		   new AlarmAdapter(this,alarms);
	       
	       listView.setAdapter(adapter);  
	}
	
	private List<Alarm> getActiveAlarms(){
		List<Alarm> alarms = AlarmManager.getInstance().getAllAlarm();
		ArrayList<Alarm> ret = new ArrayList<Alarm>();
		for(Alarm alarm : alarms){
			if(alarm.isActive()){
				ret.add(alarm);
			}
		}
		return ret;
	}
	
	private List<Alarm> getHistoryAlarms(){
		List<Alarm> alarms = AlarmManager.getInstance().getAllAlarm();
		ArrayList<Alarm> ret = new ArrayList<Alarm>();
		for(Alarm alarm : alarms){
			if(!alarm.isActive()){
				ret.add(alarm);
			}
		}
		return ret;
	}
	
	private void changeViewState(int stateNum){
		switch(stateNum){
			case STATE_ALARMS: 
				btnHistory.setChecked(false);	
				setEmptyView(emptyView);
				break;
			case STATE_HISTORY:
				btnAlarms.setChecked(false);
				setEmptyView(null);
				break;
		}
	}
	
	private void setEmptyView(View view){
		Log.d(getString(R.string.debug_tag),"setEmptyView");
		
		View originView = listView.getEmptyView();	
		
		//Because we change the emptyView,
		//we should let that layout gone manually
		if(originView !=null 
		&& originView.getVisibility() != View.GONE){
			originView.setVisibility(View.GONE);
		}
		
		listView.setEmptyView(view);
	}
		
	private void createAlarmContextMenu(ContextMenu menu,String title){
		createContextMenu(menu,title,R.menu.alarmcontext);
	}
	
	private void createHistoryContextMenu(ContextMenu menu,String title){     
        createContextMenu(menu,title,R.menu.historycontext);
	}
	
	private void createContextMenu(ContextMenu menu,String title,int resouceID){
		menu.setHeaderTitle(title); 	
		
		MenuInflater inflater = getMenuInflater();
		inflater = getMenuInflater();
        inflater.inflate(resouceID, menu);
	}
	
	private Alarm getLongClickedAlarm(ContextMenu.ContextMenuInfo menuInfo){
	    	 AdapterView.AdapterContextMenuInfo info =
	    	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    	return (Alarm)info.targetView.getTag();
	    }
}