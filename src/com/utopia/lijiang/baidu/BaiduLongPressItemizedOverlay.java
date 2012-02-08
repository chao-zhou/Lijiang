package com.utopia.lijiang.baidu;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.utopia.lijiang.R;

public class BaiduLongPressItemizedOverlay extends BaiduItemizedOverlay implements OnGestureListener {
	private GestureDetector gestureScanner = new GestureDetector(this);  
	
	public BaiduLongPressItemizedOverlay(BaiduMapActivity activity,
			Drawable marker) {
		super(activity, marker);
		// TODO Auto-generated constructor stub
	}

	public BaiduLongPressItemizedOverlay(BaiduMapActivity activity,
			Drawable marker, List<OverlayItem> items) {
		super(activity, marker, items);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView view) {
		return gestureScanner.onTouchEvent(event);  
	}

	
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		Log.d("lijiang","onLongPress");
		GeoPoint pt = activity.getMapView().getProjection().fromPixels((int) e.getX(),(int) e.getY());
		
		String title = activity.getString(R.string.customLocation);
		String message ="X:"+(int) e.getX()+" Y:"+ (int) e.getY();	
		this.items.clear();
		this.items.add(new OverlayItem(pt,title,message));
		this.populate();
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
	
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
