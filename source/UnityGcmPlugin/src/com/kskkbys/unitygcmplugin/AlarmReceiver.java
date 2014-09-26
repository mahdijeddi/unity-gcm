package com.kskkbys.unitygcmplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	 public void onReceive(Context context, Intent intent) {
	   try {
		   Log.v("AlarmReceiver", "recieved alarm!");
		   Bundle bundle = intent.getExtras();
		   String title = bundle.getString("notification_title");
		   String text = bundle.getString("notification_text");
		   String ticker = bundle.getString("notification_ticker");
		   String sound = bundle.getString("notification_sound");
		   Util.showNotification(title, text, ticker, sound, 0);
	   } catch (Exception e) {
		   e.printStackTrace();	 
	   }
	 }
}
