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
import android.widget.TextView;
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
        setOpenContexMenu(convertView);
 
		return convertView;
	}	
	
	/**Set each alarm's title
	 * */
	private void setTitle(Alarm item, View convertView){
		TextView title = (TextView)convertView.findViewById(R.id.alarmTitle);
		title.setText(item.getTitle());
	}
	
	/**Set each alarm's active
	 * and add a listener to enable/disable active
	 * */
	private void setOpenContexMenu(final View convertView){
		Button btnShowContexMenu = (Button)convertView.findViewById(R.id.alarmShowContexMenu);
		btnShowContexMenu.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				activity.openContextMenu(convertView);
			}
			
		});
	}
}
