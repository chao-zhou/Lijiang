package com.utopia.lijiang;

import com.baidu.mapapi.MKPoiResult;
public interface GetPoiResultListener {
	public void onGetPoiResult(MKPoiResult res, int type, int error);
}
