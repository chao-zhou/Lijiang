package com.utopia.lijiang.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class ContactCommand {
	
	Context mContext = null;
	ContactProjectionBuilder mBuilder = null;
	
	public ContactCommand(Context context,ContactProjectionBuilder builder){
		mContext = context;
		mBuilder = builder;	
	}
	
	public ContactReader read(){
		ContentResolver resolver = mContext.getContentResolver();  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,mBuilder.getProjection(), null, null, null);  
		
		return new ContactReader(mContext,phoneCursor);
	}
	
}
