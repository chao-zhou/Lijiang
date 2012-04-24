package com.utopia.lijiang;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.MKPoiInfo;

public class PoiListActivity extends ListActivity {

	int maxPage = 1;
	int currentPage = 1;
	ListView list = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_result_list);
		
		maxPage = LijiangMapActivity.searchResult.getNumPages();
		currentPage = LijiangMapActivity.searchResult.getPageIndex();
		
		fillList();
	}

	private void fillList(){
		ArrayList<MKPoiInfo> poiInfos = 
				LijiangMapActivity.searchResult.getAllPoi();
		
		ArrayList<String> poiTitles = new ArrayList<String>();
		for(int index = 0; index<poiInfos.size();index++){
			poiTitles.add(poiInfos.get(index).name);
		}
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(
						this, 
						android.R.layout.simple_list_item_1,
						android.R.id.text1,
						poiTitles.toArray(new String[poiInfos.size()])
				);
		setListAdapter(adapter);
	}

}
