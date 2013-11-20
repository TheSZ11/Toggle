package com.szaidi.silentmodetoggle;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends Activity {
	
	private AudioManager mAudioManager; //used to change the audio settings
	private boolean mPhoneIsSilent; //Checks to see what mode the phone is on at the moment
	private boolean wifiEnabled; //Checks to see if the device WiFi is enabled at the moment
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		
		//Call all the set up functions here
		checkIfPhoneIsSilent();
		checkIfWifiIsOn();
		setButtonClickListener();
		setButtonClickListenerWifi();
		
	}
	
	//Logic for the WiFi toggle button
	private void  setButtonClickListenerWifi() {
		
		ImageButton wifiButton = (ImageButton)findViewById(R.id.wifi_icon);
		wifiButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				WifiManager wifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
				wifiEnabled = wifiManager.isWifiEnabled(); //Check again if WiFi is enabled
				
				if(!wifiEnabled)
				{
					wifiManager.setWifiEnabled(true);
					wifiEnabled = true;
				}
				else
				{
					wifiManager.setWifiEnabled(false);
					wifiEnabled = false;
				}
				
				//Call function to toggle the image
				toggleWifiUi();
			
			}
			});
	}
	
	private void setButtonClickListener() {
		
		ImageButton toggleButton = (ImageButton)findViewById(R.id.phone_icon);
		toggleButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				if(mPhoneIsSilent) {
					// change back to normal mode
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
				} else {
					// Change to silent
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					mPhoneIsSilent = true;
				}
				
				//Now toggle the UI 
				toggleUi();
			}
		});
	}
	
	/**
	 * Checks to see if the phone is currently in Silent
	 */

	private void checkIfPhoneIsSilent() {
		
		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
		} else {
			mPhoneIsSilent = false;
		}
	}
	
	/**
	 * Checks to see if WiFi is on
	 */
	
	private void checkIfWifiIsOn() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiEnabled = wifiManager.isWifiEnabled();
	}
	
	
	/**
	 * Toggles the UI images for the Silent button from silent to normal and Vice Versa.
	 */
	
	private void toggleUi() {
		ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
		Drawable newPhoneImage;
		
		if(mPhoneIsSilent) {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_off);
		} else {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
		}
		
		imageView.setImageDrawable(newPhoneImage);
	}
	
	/**
	 * Toggles the WiFi images UI
	 */
	
	private void toggleWifiUi() {
		
		ImageView imageView = (ImageView) findViewById(R.id.wifi_icon);
		Drawable newWifiImage;
				
		if(!wifiEnabled) {
			newWifiImage = getResources().getDrawable(R.drawable.wifi_off);
		} else {
			newWifiImage = getResources().getDrawable(R.drawable.wifi_on);
		}
		
		imageView.setImageDrawable(newWifiImage);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkIfPhoneIsSilent();
		checkIfWifiIsOn();
		toggleUi();
		toggleWifiUi();
	}

}
