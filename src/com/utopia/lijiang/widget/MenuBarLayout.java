package com.utopia.lijiang.widget;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

public class MenuBarLayout extends TableLayout implements getButtons {

	View selectedView = null;
	ViewGroup tableRow = null;
	ArrayList<View> buttons = null;
	OnMenuBarSelectListener selectListener = null;
	
	public MenuBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void setOnMenuBarSelectListener(OnMenuBarSelectListener l){
		selectListener = l;
	} 
	
	public void setButtonSelected(int index, boolean selected) {
		selectedView = buttons.get(index);
		selectedView.setSelected(selected);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		getButtons();
		setOnClickListenter();
	}

	
	private void getButtons(){
		buttons = new ArrayList<View>();
		ViewGroup tableRow = (ViewGroup)getChildAt(0);
		for(int index =0 ; index< tableRow.getChildCount();index++){
			buttons.add(tableRow.getChildAt(index));	
		}
	}

	private void setOnClickListenter(){
		View button = null;
		for(int index = 0; index<buttons.size();index++){
			button = buttons.get(index);
			button.setTag(""+index);
			button.setOnClickListener(createOnClickListener());
		}	
	}

	private OnClickListener createOnClickListener(){
		return	new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(selectedView == null || selectedView != v){
					if(selectedView != null){
						selectedView.setSelected(false);
					}
					
					selectedView = v;
					selectedView.setSelected(true);
					
					if(selectListener != null){
						selectListener.onSelected(Integer.parseInt(v.getTag().toString()), v);
					}
				}				
			}			
		};
	}

	
}
