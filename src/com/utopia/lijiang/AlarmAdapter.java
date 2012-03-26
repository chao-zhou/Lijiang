package com.utopia.lijiang;

import java.util.List;

import com.utopia.lijiang.alarm.Alarm;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
/** Adapter for show Alarm list
 *  This activity is bound with alarm_listitem.xml
 * @author chao_zhou
 * @version 1.0.0.0
 * */
public class AlarmAdapter extends BaseAdapter {

	private List<Alarm> data = null;
	private Activity activity = null;
	private LayoutInflater layoutInflater; 
	
	/**Constructor
	 * @param context Current context
	 * @param data Alarm list
	 * */
	public AlarmAdapter(Activity activity,List<Alarm> data){
		this.data = data;
		this.activity = activity;
		this.layoutInflater = LayoutInflater.from(activity); 
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return data.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
      
		if(convertView == null){
			Log.d("lijiang","inflate a new view");
			convertView = layoutInflater.inflate(R.layout.alarm_listitem, null);
		}
		
        Alarm item = (Alarm)getItem(position);
        convertView.setTag(item);
        
        setTitle(item,convertView);
        setMessage(item,convertView);
        setActiveAction(item,convertView);
 
		return convertView;
	}	
	
	/**Set each alarm's title
	 * */
	private void setTitle(Alarm item, View convertView){
		TextView title = (TextView)convertView.findViewById(R.id.alarmTitle);
		title.setText(item.getTitle());
	}
	
	private void setMessage(Alarm item, View convertView) {
		// TODO Auto-generated method stub
		TextView message = (TextView)convertView.findViewById(R.id.alarmMessage);
		message.setText(item.getMessage());
	}
	
	private void setActiveAction(final Alarm item, View convertView)
	{
		ToggleButton actButton = (ToggleButton)convertView.findViewById(R.id.alarmActive);
		actButton.setChecked(item.isActive());
		actButton.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				item.setActive(isChecked);
			}});
		
	}
}
