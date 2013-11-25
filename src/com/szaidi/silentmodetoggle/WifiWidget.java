package com.szaidi.silentmodetoggle;


import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.RemoteViews;


public class WifiWidget extends AppWidgetProvider{

	@Override
	public void onReceive(Context context, Intent intent){
		if (intent.getAction()==null) {
			context.startService(new Intent(context, ToggleWifiService.class));
		} else {
			super.onReceive(context, intent);
		}
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		context.startService(new Intent(context, ToggleWifiService.class));
	}
	
	public static class ToggleWifiService extends IntentService {
		
		public ToggleWifiService() {
			super(ToggleWifiService.class.getName());
		}
		
		@Override
		protected void onHandleIntent(Intent intent) {
			ComponentName me = new ComponentName(this, WifiWidget.class);
			AppWidgetManager mgr = AppWidgetManager.getInstance(this);
			mgr.updateAppWidget(me, buildUpdate(this));
		}
		
		private RemoteViews buildUpdate(Context context) {
			boolean wifiEnabled;
			RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.wifiwidget);
			
			//******COde for changing wifi state goes here ***/
			
			WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
			wifiEnabled = wifiManager.isWifiEnabled(); //Check again if WiFi is enabled
			
			if(!wifiEnabled)
			{
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.wifi_on);
				wifiManager.setWifiEnabled(true);
				wifiEnabled = true;
			}
			else
			{
				updateViews.setImageViewResource(R.id.phoneState, R.drawable.wifi_off);
				wifiManager.setWifiEnabled(false);
				wifiEnabled = false;
			}
			
			Intent i = new Intent(this, WifiWidget.class);
			PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
			updateViews.setOnClickPendingIntent(R.id.phoneState, pi);
			return updateViews;
		}
	}

}

