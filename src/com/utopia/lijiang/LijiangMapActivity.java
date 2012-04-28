package com.utopia.lijiang;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.OverlayItem;
import com.utopia.lijiang.view.SafeProgressDialog;

public class LijiangMapActivity extends LijiangOverlayActivity {
	private static LijiangMapActivity instance = null;
	public static LijiangMapActivity getInstance(){
		return instance;
	}
	
	public  MKSearch mSearch = null;
	public  MKPoiResult searchResult = null;
	public  GetPoiResultListener getPoiResListener= null;	
	
	EditText poiNameEditText = null;
	InputMethodManager imm = null;
	ProgressDialog progressDialog = null;
	
	private boolean openedPoiListActivityMark = false; 
	private boolean openProcessDialogMark = false;
	
	private boolean shouldSaveMapStateAndReset(){
		boolean rslt = openedPoiListActivityMark || openProcessDialogMark;
		openedPoiListActivityMark = false;
		openProcessDialogMark =false;
		
		return rslt;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    
		initialProgressDialog();
	    initialSearchInput();
	    initialSearch();
	    
	    instance = this;
	}

	@Override
	protected void onResume(){	
		if(shouldSaveMapStateAndReset()){
			super.onResume(false);
		}
		else{ //clear map
			super.onResume(true);
			clearSearchResult();
		}
	}
	
	/*
	 * Pass the Back Press event to parent
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if(mPopView.getVisibility() == View.VISIBLE){
			mPopView.setVisibility(View.GONE);
			return;
		}
		
		Activity parent = this.getParent();
		if(parent != null){
			parent.onBackPressed();
		}
	}
	
	@Override
	protected int getConentViewId() {
		// TODO Auto-generated method stub
		return R.layout.baidumap;
	}
	
	
	
	public void searchPosition(View targer){
		String poiName = getPostionName();
		if(poiName.length()>0){
			progressDialog.show();
			openProcessDialogMark = true;
			mSearch.poiSearchInCity(CURRENT_CITY,poiName);
		}	
	}
	
	public void clearSearchResult(){
		mSearch = null;
		searchResult = null;
		getPoiResListener= null;	
		
		initialSearch();
	}
	
	public void onTapped(int index){
		OverlayItem item = this.searchOverlay.getItem(index);
		this.onTapped(index, item);
	} 
	
	public void showDetails(View target){
		setPopViewToDetail();
	}
	
	public void addAlarm(View target){
		 String title = popName.getText().toString();
		 String message = popAddress.getText().toString();
		 
		 Intent i = new Intent(this,AddAlarmActivity.class);
		 i.putExtra(AddAlarmActivity.ALARM_TITLE, title);
		 i.putExtra(AddAlarmActivity.ALARM_MESSAGE, message);
		 i.putExtra(AddAlarmActivity.ALARM_LONGITUDEE6, (double)tappedPoint.getLongitudeE6()/1E6);
		 i.putExtra(AddAlarmActivity.ALARM_LATITUDEE6, (double)tappedPoint.getLatitudeE6()/1E6);
		 
		 this.startActivity(i);
	}

	public void zoomIn(View target){
		mMapView.getController().zoomIn();
	}
	
	public void zoomOut(View target){
		mMapView.getController().zoomOut();
	}
	
	public void showCurrentLocation(View target){
		if(!showCurrentLocation()){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(getString(R.string.canNotFindPostion))
			       .setCancelable(true)
			       .setPositiveButton(getString(R.string.known), new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}})
					.create()
					.show();
		}
	}
	
	public void showLocationList(View target){
		if(searchResult == null){
			poiNameEditText.requestFocus();
			return;
		}
		
		openedPoiListActivityMark = true;
		Intent intent = new Intent(this, PoiListActivity.class);
		this.startActivityForResult(intent, 0);
		
	}
		
	public void showPreviousPagePoiResualt(View target){
		if(searchResult == null){
			poiNameEditText.requestFocus();
			return;
		}
		
		int currentPage = searchResult.getPageIndex();
		int previousPage = currentPage-1;	
		if(previousPage>0){
			mSearch.goToPoiPage(previousPage);
			startBMapManager();
		}
	}

	public void showNextPagePoiResualt(View target){
		if(searchResult == null){
			poiNameEditText.requestFocus();
			return;
		}
		
		int maxPages = searchResult.getNumPages();
		int currentPage = searchResult.getPageIndex();
		int nextPage = currentPage+1;
		if(nextPage<maxPages){
			mSearch.goToPoiPage(nextPage);
			startBMapManager();
		}
	}
	
	
	
	private String getPostionName(){
		return poiNameEditText.getText().toString().trim();
	}
	
	private void hideIME(View v){
		if(imm.isActive(v)){
			imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	private void showSearchResult() {
		ArrayList<MKPoiInfo> poiInfos = searchResult.getAllPoi();	
		if(poiInfos == null){
			Toast.makeText(LijiangMapActivity.this, 
					LijiangMapActivity.this.getString(R.string.empty_pos_rslt), 
					100);
			return;
		}

		searchOverlay.setData(poiInfos);
		mMapView.invalidate();
	}
	
	private void initialProgressDialog(){
		//progressDialog = new ProgressDialog(this);
		progressDialog = new SafeProgressDialog(this,MAX_SEARCHING_SECOND);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setMessage(getString(R.string.searching));
		progressDialog.setCancelable(false);
	}
	
	private void initialSearch(){
		   mSearch = new MKSearch();
		   mSearch.init(mBMapMan, new MKSearchListener(){

				@Override
				public void onGetAddrResult(MKAddrInfo arg0, int arg1) {}

				@Override
				public void onGetDrivingRouteResult(MKDrivingRouteResult arg0,int arg1) {}

				@Override
				public void onGetPoiResult(MKPoiResult res, int type, int error) {					
					searchResult = res;					
					if (error != 0 || searchResult == null) {
						progressDialog.hide();
						Toast.makeText(LijiangMapActivity.this, "Error or Empty", Toast.LENGTH_LONG).show();
						return;
					}	
					showSearchResult();
					
					//If it trigger by other activity, so, this activity should be paused 
					//And BMapManager was started manually, should be close;
					if(LijiangMapActivity.this.isPaused() 
							&&getPoiResListener != null){
						getPoiResListener.onGetPoiResult(res, type, error);
						LijiangMapActivity.this.stopBMapManager();
					}
					
					progressDialog.cancel();
				}

				@Override
				public void onGetTransitRouteResult(MKTransitRouteResult arg0,int arg1) {}

				@Override
				public void onGetWalkingRouteResult(MKWalkingRouteResult arg0,int arg1) {}
				});	       
	}
	
	private void initialSearchInput(){
		  imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		  poiNameEditText = (EditText)this.findViewById(R.id.searchPositionName);
		  poiNameEditText.setOnEditorActionListener(new OnEditorActionListener(){

		  @Override
		  public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if(actionId !=  EditorInfo.IME_ACTION_SEARCH){
						return false;
					}
					
					searchPosition(null);
					hideIME(v);
					return true;
				}
		    });
	}

}
