package com.kskkbys.unitygcmplugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.*;
import android.content.*;
import android.util.Log;

public class LocalNotificationManager
{
	private static int REQUEST_CODE = 192837;
	
	
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
		 PendingIntent sender = PendingIntent.getBroadcast(
				 context, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Get the AlarmManager service
		 AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		 am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		 
		 requestCodes.add(REQUEST_CODE);
		 REQUEST_CODE++;
	}
	public static List<Integer> requestCodes = new ArrayList<Integer>();
	
	
	public static void CancelAllAlarms(Context context)
	{
		Log.v("LocalNotificationManager", "CancelAllAlarms!");
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		for(int i = 0; i < requestCodes.size(); i++)
		{
			int reqId = requestCodes.get(i);
			Log.v("LocalNotificationManager", "cancel alaram with requestid: " + reqId);

			Intent updateServiceIntent = new Intent(context, AlarmReceiver.class);
		    PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(
		    		context, reqId, updateServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		    try {
		    	am.cancel(pendingUpdateIntent);
		    } catch (Exception e) {
		        Log.e("LocalNotificationManager", "AlarmManager update was not canceled. " + e.toString());
		    }
		}
		requestCodes.clear();
	}
	
}
