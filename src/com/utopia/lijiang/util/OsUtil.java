package com.utopia.lijiang.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.SmsManager;

public class OsUtil {

	public static void Dial(Context context,String number){
		Uri telUri = Uri.parse("tel:"+number);
		Intent intent = new Intent(Intent.ACTION_DIAL,telUri);
		context.startActivity(intent);
	}

	public static void Vibrate(Context context){
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {800, 50, 400, 30}; // OFF/ON/OFF/ON...  
        vibrator.vibrate(pattern, 2);	
	}
	
	public static void SendMessaqge(String address,String message){
		SmsManager smsMgr = SmsManager.getDefault();
		ArrayList<String> parts = smsMgr.divideMessage(message);
		smsMgr.sendMultipartTextMessage(address, null, parts, null, null);
	}
	
}
