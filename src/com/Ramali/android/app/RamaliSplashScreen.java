package com.Ramali.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

public class RamaliSplashScreen extends Activity {
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
        SplashHandler handle =new SplashHandler();
        
        setContentView(R.layout.splash_screen);
        Message msg =new Message();
        msg.what=0;
        handle.sendMessageDelayed (msg,2500);
        
    }
private class SplashHandler extends Handler {
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
		default:
		case 0:
			super.handleMessage (msg);
		Intent splashintent =new Intent();
		splashintent.setClass(RamaliSplashScreen.this, RamaliHome.class);
		startActivity(splashintent);
		RamaliSplashScreen.this.finish();
		
		
		}
	}
    }	

}
