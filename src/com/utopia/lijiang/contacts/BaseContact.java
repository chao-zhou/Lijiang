package com.utopia.lijiang.contacts;

import android.content.Context;
import android.graphics.Bitmap;

public abstract class BaseContact implements Contact  
{
	
	public abstract Bitmap getPhotoPic();
	

	protected String mDisplayName = null;
	protected String mPhoneNumber = null;
	protected Long mId = null;
	protected Long mPhotoId = null;
	protected Context mContext = null;
    
    public BaseContact(Context context){
    	mContext = context;
    }
    
    public String getmDiplayName() {
		return mDisplayName;
	}
	public void setmDiplayName(String displayName) {
		mDisplayName = displayName;
	}
	public String getmPhoneNumber() {
		return mPhoneNumber;
	}
	public void setmPhoneNumber(String phoneNumber) {
		mPhoneNumber = phoneNumber;
	}
	public Long getmId() {
		return mId;
	}
	public void setmId(Long id) {
		mId = id;
	}
	public Long getmPhotoId() {
		return mPhotoId;
	}
	public void setmPhotoId(Long photoId) {
		mPhotoId = photoId;
	}
	
	
	protected Bitmap getDefaultPic(){
		
		return null;
		//return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact_photo); 
		
	}
}
