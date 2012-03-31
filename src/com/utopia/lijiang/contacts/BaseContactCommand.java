package com.utopia.lijiang.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public abstract class BaseContactCommand implements ContactCommand{
	
	protected Context mContext = null;
	protected ContactProjectionBuilder mBuilder = null;
	
	public BaseContactCommand(Context context){
		mContext = context;
		mBuilder = getDefaultProjectionBuilder();	
	}
	
	public BaseContactCommand(Context context,ContactProjectionBuilder builder){
		mContext = context;
		mBuilder = builder;	
	}
	
	public abstract ContactReader read();
	
	protected Cursor getContactCursor(Uri uri){
		ContentResolver resolver = mContext.getContentResolver();  
		return resolver.query(uri,mBuilder.getProjection(), null, null, null);  
	}
	
	public  ContactProjectionBuilder getDefaultProjectionBuilder() {
		ContactProjectionBuilder builder = new ContactProjectionBuilder();
		builder.inlcudeContactId(true);
		builder.inlcudeDisplayName(true);
		builder.inlcudeNumber(true);
		builder.inlcudePhotoId(true);
		return builder;
	}  
}
