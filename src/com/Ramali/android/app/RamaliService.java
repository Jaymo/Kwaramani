package com.Ramali.android.app;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.Ramali.android.app.functions.JSONfunctions;
import com.Ramali.android.app.util.Util;

public class RamaliService extends Service {
	    
	    public static int update = 0;
	   
	    public static String URL="http://akajaymo.kodingen.com/api_update.php";
	    
	    //public static String URL="http://10.0.2.2/android/api_update.php";
	    
	    private TimerTask mDoTask;

	    private Timer mT = new Timer();

	    private Handler handler = new Handler();

	    private Context mContext;

	    private static final String TAG = "KwaRamani Notification";
	    
	    private static final String PROMPT ="New rental Houses are available.";
	    
	    public static boolean vibrate = true;

	    public static boolean flashLed = true;

	    public static final int NOTIFICATION_ID = 1;

	    public static final String PREFS_NAME = "KwaRamaniService";
	    
	    private Notification KwaRamaniReportNotification;

	    private NotificationManager mNotificationManager;
	    
	    @Override
	    public void onCreate() {

	        mContext = getApplicationContext();

	        Ramanipref.loadSettings(mContext);

	        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	        this.startService();

	    } 

	private void startService() {
		Ramanipref.loadSettings(mContext);
        long delay = 120000;
        long period =21600000;
        //long period = 60000;
        if (Util.isConnected(RamaliService.this)) { 
        mDoTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                      
                    public void run() {  
                    
                    	JSONObject json = JSONfunctions.getJSONfromURL(URL);
                    	try{
                        	JSONArray  updates = json.getJSONArray("UPDATES");
                        	for(int i=0;i<1;i++){
                				JSONObject e = updates.getJSONObject(i);
                				update= e.getInt("MAX(id)");
                				
                	        	
                        	}
                    	}catch(JSONException e)      {
                       	 Log.e("log_tag", "Error kupitisha data "+e.toString());
                       }
                    	Ramanipref.loadSettings(mContext);
                    	if(update>Ramanipref.rentals){
                    		showNotification(PROMPT);
                    		
                    	}
                    	
                    	
                    	
                    }

                });
            }

        };
        mT.scheduleAtFixedRate(mDoTask, delay, period);
	}
        else{}
  	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
    private void stopService() {
        if (mDoTask != null) {
            mDoTask.cancel();
            mT.cancel();
            mT.purge();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopService();
    }
    private void showNotification(String tickerText) {

        Intent baseIntent = new Intent(this, RentalList.class);
        baseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, baseIntent, 0);

        KwaRamaniReportNotification = new Notification(R.drawable.favicon, tickerText,
                System.currentTimeMillis());
        KwaRamaniReportNotification.contentIntent = contentIntent;
        KwaRamaniReportNotification.flags = Notification.FLAG_AUTO_CANCEL;
        KwaRamaniReportNotification.defaults = Notification.DEFAULT_ALL;
        KwaRamaniReportNotification.setLatestEventInfo(this, TAG, tickerText, contentIntent);


        if (Ramanipref.vibrate) {
            double vibrateLength = 100 * Math.exp(0.53 * 20);
            long[] vibrate = new long[] {
                    100, 100, (long)vibrateLength
            };
            KwaRamaniReportNotification.vibrate = vibrate;

        if (Ramanipref.flashLed) {
                int color = Color.BLUE;
                KwaRamaniReportNotification.ledARGB = color;
            }

            KwaRamaniReportNotification.ledOffMS = (int)vibrateLength;
            KwaRamaniReportNotification.ledOnMS = (int)vibrateLength;
            KwaRamaniReportNotification.flags = KwaRamaniReportNotification.flags
                    | Notification.FLAG_SHOW_LIGHTS;
        }

        mNotificationManager.notify(Ramanipref.NOTIFICATION_ID, KwaRamaniReportNotification);
    }
    
	  
	    

}
