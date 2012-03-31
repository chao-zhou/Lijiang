package com.utopia.lijiang.contacts.sim;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.utopia.lijiang.contacts.BaseContactCommand;
import com.utopia.lijiang.contacts.ContactProjectionBuilder;
import com.utopia.lijiang.contacts.ContactReader;

public class SimContactCommand extends BaseContactCommand {

	public SimContactCommand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SimContactCommand(Context context, ContactProjectionBuilder builder) {
		super(context, builder);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ContactReader read() {
		Uri uri = Uri.parse("content://icc/adn");  
		Cursor phoneCursor = getContactCursor(uri);  	
		return new SimContactReader(mContext,phoneCursor);
	}

}
