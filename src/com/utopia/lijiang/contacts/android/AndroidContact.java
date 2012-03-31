package com.utopia.lijiang.contacts.android;

import java.io.InputStream;

import com.utopia.lijiang.contacts.BaseContact;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

public class AndroidContact extends BaseContact 
{
	public AndroidContact(Context context) {
		super(context);
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
	

}
