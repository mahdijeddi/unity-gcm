package com.kskkbys.unitygcmplugin;

import com.unity3d.player.UnityPlayer;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Notification manager class.
 * @author Keisuke Kobayashi
 *
 */
public class UnityGCMNotificationManager {
	
	private static final String TAG = UnityGCMNotificationManager.class.getSimpleName();
	
	// Request code for launching unity activity
	private static final int REQUEST_CODE_UNITY_ACTIVITY = 1001;
	// ID of notification
	private static final int ID_NOTIFICATION = 1;
	
	/**
	 * Show notification view in status bar
	 * @param context
	 * @param contentTitle
	 * @param contentText
	 * @param ticker
	 */
	public static void showNotification(Context context, String contentTitle, String contentText, String ticker, String customData) {
		Log.v(TAG, "showNotification");
		
		// Intent 
		Intent intent = getLaunchIntentForCurrentPackage(context, customData);
		PendingIntent contentIntent = PendingIntent.getActivity(context, REQUEST_CODE_UNITY_ACTIVITY, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//　Show notification in status bar
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext());
		builder.setContentIntent(contentIntent);
		builder.setTicker(ticker);
		builder.setContentText(contentText);
		builder.setContentTitle(contentTitle);
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		
		Resources res = context.getResources();
		builder.setSmallIcon(res.getIdentifier("app_icon", "drawable", context.getPackageName()));
		
		builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
		
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(ID_NOTIFICATION, builder.build());
	}
	
	/**
	 * Show notification view in status bar
	 * @param context
	 * @param contentTitle
	 * @param contentText
	 * @param ticker
	 */
	public static void showNotification(Context context, String contentTitle, String contentText, 
			String ticker, String customData, String sound, long when) {
		Log.v(TAG, "showNotification");
		
		// Intent 
		Intent intent = getLaunchIntentForCurrentPackage(context, customData);
		PendingIntent contentIntent = PendingIntent.getActivity(context, REQUEST_CODE_UNITY_ACTIVITY, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		//　Show notification in status bar
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext());
		builder.setContentIntent(contentIntent);
		builder.setTicker(ticker);
		builder.setContentText(contentText);
		builder.setContentTitle(contentTitle);
		builder.setWhen(System.currentTimeMillis() + when);
		builder.setAutoCancel(true);
		
		Log.v(TAG, "GCM: setting notification time to: " + when);
		
		if(sound != null)
		{
			Log.v(TAG, "GCM: setting notification sound to: " + sound);
			Uri soundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + "raw/" + sound);
			builder.setSound(soundUri);
			builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
		}
		else
		{
			builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
		}
		
		Resources res = context.getResources();
		builder.setSmallIcon(res.getIdentifier("app_icon", "drawable", context.getPackageName()));
		
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(ID_NOTIFICATION, builder.build());
	}
	
	public static Intent getLaunchIntentForCurrentPackage(Context context, String customData) {
		if(customData != null && (customData.startsWith("http") || customData.startsWith("market") ))
		{
			Log.v(TAG, "open link");
			String url = customData;
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			return i;
		}
		Log.v(TAG, "open main app");
		String packageName = context.getPackageName();
		Intent result = context.getPackageManager().getLaunchIntentForPackage(packageName);
		result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return result;
	}
	
	/**
	 * Show notification view in status bar
	 * @param context
	 * @param contentTitle
	 * @param contentText
	 * @param ticker
	 */
	public static void clearAllNotifications() {
		Log.v(TAG, "clearAllNotifications");
		
		NotificationManager nm = (NotificationManager)UnityPlayer.currentActivity.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancelAll();
	}
	
}
