package com.utopia.lijiang.contacts;

import java.util.ArrayList;

import android.content.Context;

import com.utopia.lijiang.contacts.android.AndroidContactCommand;
import com.utopia.lijiang.contacts.sim.SimContactCommand;

public class ContactHelper {
    
	private Context mContext = null;
	  
	public ContactHelper(Context context){
	
		mContext = context;
	}
	

	public Contact[] getAndroidContact(){
		ContactCommand cmmd = new AndroidContactCommand(mContext);
		return getContacts(cmmd);
	}
	
	public Contact[] getSimContact(){
		ContactCommand cmmd = new SimContactCommand(mContext);
		return getContacts(cmmd);
	}
	
	public Contact[] getContacts(ContactCommand cmmd) {  
		ContactReader reader = cmmd.read();
	  
		if(!reader.canRead()){
			reader.close();
			return null;
		}
	
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		while(reader.moveToNext()){
			contacts.add(reader.getContact());		
		}
		
		return (Contact[]) contacts.toArray(new Contact[contacts.size()]);	      
	}

	
}
