package com.utopia.lijiang.contacts;

import android.graphics.Bitmap;

public interface Contact {
    public String getmDiplayName();
	public void setmDiplayName(String displayName); 
	public String getmPhoneNumber();
	public void setmPhoneNumber(String phoneNumber);
	public Long getmId();
	public void setmId(Long id);
	public Long getmPhotoId();
	public void setmPhotoId(Long photoId); 	
	public Bitmap getPhotoPic();
}
