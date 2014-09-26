package com.kskkbys.unitygcmplugin;

import java.util.Calendar;

import android.app.*;
import android.content.*;

public class LocalNotificationManager
{
	private static final int REQUEST_CODE = 192837;
	
	
	public static void CreateAlarmEvent(Context context, String title, String text, 
			String ticker, String sound, int offsetInMillis)
	{
		// get a Calendar object with current time
		 Calendar cal = Calendar.getInstance();
		 // add 5 minutes to the calendar object
		 cal.add(Calendar.MILLISECOND, offsetInMillis);
		 Intent intent = new Intent(context, AlarmReceiver.class);
		 intent.putExtra("notification_title", title);
		 intent.putExtra("notification_text", text);
		 intent.putExtra("notification_ticker", ticker);
		 intent.putExtra("notification_sound", sound);
		 // In reality, you would want to have a static variable for the request code instead of 192837
		 PendingIntent sender = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
	}
}
