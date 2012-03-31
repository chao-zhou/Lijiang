package com.utopia.lijiang.contacts.sim;

import com.utopia.lijiang.contacts.BaseContact;

import android.content.Context;
import android.graphics.Bitmap;

public class SimContact extends BaseContact{

	public SimContact(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Bitmap getPhotoPic() {
		// TODO Auto-generated method stub
		return getDefaultPic();
	}

}
