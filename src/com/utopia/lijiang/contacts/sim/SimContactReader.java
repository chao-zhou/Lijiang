package com.utopia.lijiang.contacts.sim;

import android.content.Context;
import android.database.Cursor;

import com.utopia.lijiang.contacts.BaseContactReader;
import com.utopia.lijiang.contacts.Contact;

public class SimContactReader extends BaseContactReader {

	public SimContactReader(Context context, Cursor cursor) {
		super(context, cursor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Contact getContact() {
		Contact contact = new SimContact(mContext);
		contact.setmId(this.getContactId());
		contact.setmDiplayName(this.getDisplayName());
		contact.setmPhoneNumber(this.getPhoneNumber());
		contact.setmPhotoId(this.getPhotoId());
		
		return contact;
	}

}
