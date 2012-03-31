package com.utopia.lijiang.contacts;

import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class Contact {

	private String mDisplayName = null;
    private String mPhoneNumber = null;
    private Long mId = null;
    private Long mPhotoId = null;
	
    private Context mContext = null;
    
    public Contact(Context context){
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
	
	public Bitmap getPhotoPic(){
		if(mPhotoId > 0 ) {  
			return getContactPic();
		}  	        
		return getDefaultPic();	     
	}  
	
	private Bitmap getContactPic(){
		ContentResolver resolver = mContext.getContentResolver();  
		Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,mId);  
	   
		InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
	    return BitmapFactory.decodeStream(input);  
	}
	
	private Bitmap getDefaultPic(){
		
		return null;
		//return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact_photo); 
		
	}
}
