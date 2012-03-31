package com.utopia.lijiang.contacts;

import android.content.Context;
import android.database.Cursor;

class ContactReader {
	Cursor mCursor = null;
	Context mContext = null;
	
	public ContactReader(Context context, Cursor cursor){
		mCursor = cursor;
		mContext = context;
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
		if(mCursor == null || mCursor.isClosed())
			return;
		
		mCursor.close();
	}


	public Boolean canRead(){
		return !(mCursor == null);
	}
	
	public Boolean hasNext(){
		return !mCursor.isLast();
	}

	public Boolean moveToNext(){
		return mCursor.moveToNext();
	}
	
	public void close(){
		mCursor.close();
	}
	
	public Contact getContact(){
		Contact contact = new Contact(mContext);
		contact.setmId(this.getContactId());
		contact.setmDiplayName(this.getDisplayName());
		contact.setmPhoneNumber(this.getPhoneNumber());
		contact.setmPhotoId(this.getPhotoId());
		
		return contact;
	}
	
	protected String getPhoneNumber(){
		return mCursor.getString(ContactProjectionBuilder.PHONES_NUMBER_INDEX);
	}
	
	protected String getDisplayName(){
		return mCursor.getString(ContactProjectionBuilder.PHONES_DISPLAY_NAME_INDEX);  
	}
	
	protected Long getContactId(){
		return mCursor.getLong(ContactProjectionBuilder.PHONES_CONTACT_ID_INDEX);  
	}
	
	protected Long getPhotoId(){
		return mCursor.getLong(ContactProjectionBuilder.PHONES_PHOTO_ID_INDEX);
	}
	

}
