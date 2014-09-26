package com.kskkbys.unitygcmplugin;

import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

public class Util {

	// GameObject name of the receiver object
	private static final String RECEIVER_NAME = "GCMReceiver";
	
	public static boolean notificationsEnabled = true; 

	public static List<String> messages = new ArrayList<String>();

	
	/**
	 * Send message to GameObject of Unity
	 * @param method
	 * @param message
	 */
	public static void sendMessage(final String method, final String message) {
		try {
			if (TextUtils.isEmpty(message)) {
				UnityPlayer.UnitySendMessage(RECEIVER_NAME, method, "");
			} else {
				UnityPlayer.UnitySendMessage(RECEIVER_NAME, method, message);
			}
		} catch (UnsatisfiedLinkError e) {
			// When app process is launched by GCM, linking with native code may not be done.
			e.printStackTrace();
		}
	}

	
	/**
	 * Show toast message (for debugging)
	 * @param message
	 */
	public static void showToast(final String message) {
		UnityPlayer.currentActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(UnityPlayer.currentActivity, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	
	public static void saveMessage(JSONObject json) {
		
		// don't save if it's a link
		String customData = null;
		try {
			customData = json.getString("custom_data");
		} catch (JSONException e) {
		}
		if(customData != null && customData.startsWith("market") || customData.startsWith("http"))
			return;
		
		messages.add(json.toString());
	}
	
	
	public static void getBackgroundMessages()
	{
		if(UnityPlayer.currentActivity == null)
			return;
		for(int i = 0; i < messages.size(); i++)
			sendMessage(UnityGCMIntentService.ON_MESSAGE, messages.get(i));
		messages.clear();
	}
	
	
	public static void showNotification(final String title, final String text, final String ticker, final String sound, final long when)
	{
		UnityGCMNotificationManager.showNotification(UnityPlayer.currentActivity, title, text, ticker, null, sound, when);
	}
}
