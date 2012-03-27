package com.utopia.lijiang;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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
	
	private final int STATE_LIST = AlarmAdapter.ListAlarmViewState;
	private final int STATE_EDIT = AlarmAdapter.EditAlarmViewState;
	private final int STATE_HISTORY = -1;
	
	
	private int viewState;
	private ListView listView = null;
	//private ToggleButton btnAlarms = null;
	private ToggleButton btnHistory = null;
	private View emptyView = null;
	private List<Alarm> alarms  = null;

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
    	setContentView(R.layout.main_new);
    	
    	listView = (ListView)this.findViewById(R.id.alarmList);
    	//btnAlarms = (ToggleButton)this.findViewById(R.id.btnAlarms);
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
    	setListViewState(STATE_LIST);
    }
    
    /**Called when the activity is finished*/
    @Override
    public void onDestroy(){
    	Log.d(getString(R.string.debug_tag),"Destroy LijiangActivity");
    	super.onDestroy();
    }   
        
   /* @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	int menuId = item.getItemId();
    	Alarm alarm = getAlarmFromListView(item.getMenuInfo());
    	AlarmManager mgr = AlarmManager.getInstance();
    	
    	switch(menuId){
    		case R.id.deleteAlarm:
    			mgr.removeAlarm(alarm);
    			mgr.delete2DB(this);
    			setListViewState(STATE_LIST);
    			break;
    		case R.id.deleteHistory:
    			mgr.removeAlarm(alarm);
    			mgr.delete2DB(this);
    			setListViewState(STATE_HISTORY);
    			break;
    		case R.id.renewHistory:
    			alarm.setActive(true);
    			setListViewState(STATE_HISTORY);
    			break;
    		default:
    			break;
    	}
	  
	    return true;
    }
   
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
    	Alarm alarm = getAlarmFromListView(menuInfo);
    	
    	if(alarm.isActive()){	
    		createAlarmContextMenu(menu,alarm.getTitle());
    	}else{	
    		createHistoryContextMenu(menu,alarm.getTitle());
    	}	
    }*/
    
    /** Do work after showAddAlarm button is clicked
     * @param target trigger
     * */
    public void showAddAlarmActivity(View target){
   	 //Just test Map 
     this.startActivity(new Intent(this,LijiangMapActivity.class));
    }
    
    public void showEditView(View target){
    	int viewState = getListViewState();
    	
    	if(viewState == STATE_LIST){
    		setListViewState(STATE_EDIT);
    		return;
    	}
    	
    	setListViewState(STATE_LIST);
    }
    
    public void showAlarms(View target){
    	setListViewState(STATE_LIST);
    }
    
    public void showHistory(View target){  		
    	setListViewState(STATE_HISTORY);
    }

    
    /**Fill Data into Task List*/
	private void bindList(List<Alarm> alarms){ 
	       AlarmAdapter adapter = 
	    		   new AlarmAdapter(this,alarms);
	       adapter.setViewState(viewState);
	       listView.setAdapter(adapter);  
	}
	
	private int getListViewState(){
		return viewState;
	}
	
	private void setListViewState(int stateNum){
		viewState = stateNum;
		switch(stateNum){
			case STATE_LIST: 
				alarms = AlarmManager.getInstance().getAllAlarms();
				btnHistory.setChecked(false);	
				setEmptyView(emptyView);
				bindList(alarms);
				break;
			case STATE_EDIT:
				alarms = AlarmManager.getInstance().getAllAlarms();
				btnHistory.setChecked(false);	
				setEmptyView(emptyView);
				bindList(alarms);
				break;
			/*case STATE_HISTORY:
				alarms = AlarmManager.getInstance().getHistoryAlarms();
				btnAlarms.setChecked(false);
				setEmptyView(null);
				bindList(alarms);
				break;*/
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
		
	/*private void createAlarmContextMenu(ContextMenu menu,String title){
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
	
	private Alarm getAlarmFromListView(ContextMenu.ContextMenuInfo menuInfo){
	    AdapterView.AdapterContextMenuInfo info =
	    	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    return (Alarm)info.targetView.getTag();
	}*/

}