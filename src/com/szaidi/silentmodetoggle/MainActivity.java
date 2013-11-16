package com.szaidi.silentmodetoggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private AudioManager mAudioManager;
	private boolean mPhoneIsSilent;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
		
		checkIfPhoneIsSilent();
		
		setButtonClickListener();
	}
	
	private void setButtonClickListener() {
		ImageButton toggleButton = (ImageButton)findViewById(R.id.phone_icon);
		toggleButton.setOnClickListener(new View.OnClickListener()
		{
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
	 * Toggles the UI images from silent to normal and Vice Versa.
	 */
	
	private void toggleUi() {
		ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
		Drawable newPhoneImage;
		
		if(mPhoneIsSilent) {
			newPhoneImage = 
					getResources().getDrawable(R.drawable.phone_off);
		} else {
			newPhoneImage = 
					getResources().getDrawable(R.drawable.phone_on);
		}
		
		imageView.setImageDrawable(newPhoneImage);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkIfPhoneIsSilent();
		toggleUi();
	}

}
