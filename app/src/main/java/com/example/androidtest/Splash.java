package com.example.androidtest;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;


/**
 * Created by Yanqing Ma on 11/1/2016.
 */
public class Splash extends Activity {
	
	MediaPlayer mp;

	private void gotoFirstView() {
		Intent intent = new Intent(this, First.class);
		startActivity(intent);
	}


	// when app starts, animation and background music automatically runs for two seconds
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		mp = MediaPlayer.create(getApplicationContext(), R.raw.qian);
		mp.start();
		
		Thread timer = new Thread()
		{
				public void run()
				{
					try
					{
						sleep(2000);
						
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
						
					}
					finally
					{
						gotoFirstView();
						
					}
					
				}

		};
		
			timer.start();
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp.release();
		finish();
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	

}
