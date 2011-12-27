package com.utopia.lijiang;

import java.util.List;

import com.utopia.lijiang.alarm.Alarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class AlarmAdapter extends BaseAdapter {

	private List<Alarm> data = null;
	private LayoutInflater layoutInflater; 
	
	
	public AlarmAdapter(Context context,List<Alarm> data){
		this.data = data;
		this.layoutInflater = LayoutInflater.from(context); 
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
        if (convertView == null) {  
            convertView = layoutInflater.inflate(R.layout.alarm_listitem, null); 
        }  
       
        Alarm item = (Alarm)getItem(position);
        
        setTitle(item,convertView);
        setMessage(item,convertView);
        setActive(item,convertView);
        
		return convertView;
	}
	
	private void setTitle(Alarm item, View convertView){
		TextView title = (TextView)convertView.findViewById(R.id.alarmTitle);
		title.setText(item.getTitle());
	}
	
	private void setMessage(Alarm item, View convertView){
		TextView msg = (TextView)convertView.findViewById(R.id.alarmMsg);
		msg.setText(item.getMessage());
	}
	
	private void setActive(Alarm item, View convertView){
		CheckBox active = (CheckBox)convertView.findViewById(R.id.alarmActive);
		active.setChecked(item.isActive());
	}
}
