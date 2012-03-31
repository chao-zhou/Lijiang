package com.utopia.lijiang.contacts.android;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.utopia.lijiang.contacts.BaseContactCommand;
import com.utopia.lijiang.contacts.ContactProjectionBuilder;
import com.utopia.lijiang.contacts.ContactReader;

public class AndroidContactCommand extends BaseContactCommand {
	
	public AndroidContactCommand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AndroidContactCommand(Context context,
			ContactProjectionBuilder builder) {
		super(context, builder);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ContactReader read() {	  
		Cursor phoneCursor = getContactCursor(Phone.CONTENT_URI);  	
		return new AndroidContactReader(mContext,phoneCursor);
	}

}
