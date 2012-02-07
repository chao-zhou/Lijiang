package com.utopia.lijiang.baidu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;

public class BaiduItemizedOverlay extends ItemizedOverlay<OverlayItem>{

	private BaiduMapActivity activity = null;
	private List<OverlayItem> items = null;
	private Drawable marker;
	
	public BaiduItemizedOverlay(BaiduMapActivity activity,Drawable marker){
		this(activity,marker,null);
	}
		
	public BaiduItemizedOverlay(BaiduMapActivity activity,Drawable marker,List<OverlayItem> items) {
		super(boundCenterBottom(marker));
		
		this.activity = activity;
		this.marker = marker;
		this.items = items;
		
		if(this.items == null){
			this.items = new ArrayList<OverlayItem>();
			
		}

		populate();  
	}

	public void setData(List<MKPoiInfo> infos){
		items.clear();
		MKPoiInfo info = null;
		Iterator<MKPoiInfo> it = infos.iterator(); 
		while(it.hasNext()){
			info = it.next();
			items.add(new OverlayItem(info.pt,info.name,info.address));
		}
		
		populate();  
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		
		Projection projection = mapView.getProjection(); 
		for (int index = size() - 1; index >= 0; index--) { 
			OverlayItem overLayItem = getItem(index);
			String title = overLayItem.getTitle();
			Point point = projection.toPixels(overLayItem.getPoint(), null); 

			Paint paintText = new Paint();
			paintText.setColor(Color.BLUE);
			paintText.setTextSize(15);
			canvas.drawText(title, point.x-5, point.y+15, paintText); 
		}

		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return items.get(i);
	}

	@Override
	public int size() {
		return items.size();
	}
	
	@Override	
	protected boolean onTap(int i) {
		setFocus(items.get(i));	
		return activity.onTapped(i, items.get(i));
	}

	@Override
	public boolean onTap(GeoPoint pt, MapView v) {
		activity.onTapping(pt, v);
		return super.onTap(pt, v);
	}
}
