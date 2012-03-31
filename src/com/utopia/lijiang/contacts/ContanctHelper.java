package com.utopia.lijiang.contacts;

import java.util.ArrayList;

import android.content.Context;

public class ContanctHelper {
    
	private Context mContext = null;
	  
	public ContanctHelper(Context context){
	
		mContext = context;
	}
	
	public Contact[] getPhoneContacts() {  
		
	ContactProjectionBuilder builder = getProjectionBuilder();
		
	ContactCommand cmmd = new ContactCommand(mContext,builder);
	ContactReader reader = cmmd.read();
	  
	if(!reader.canRead()){
		reader.close();
		return null;
	}
	
	ArrayList<Contact> contacts = new ArrayList<Contact>();
	while(reader.hasNext()){
		contacts.add(reader.getContact());		
	}
	
	return (Contact[]) contacts.toArray();
	      
	}

	private ContactProjectionBuilder getProjectionBuilder() {
		ContactProjectionBuilder builder = new ContactProjectionBuilder();
		builder.inlcudeContactId(true);
		builder.inlcudeDisplayName(true);
		builder.inlcudeNumber(true);
		builder.inlcudePhotoId(true);
		return builder;
	}  
}
