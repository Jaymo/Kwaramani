package com.Ramali.android.app;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.Ramali.android.app.functions.JSONfunctions;
import com.Ramali.android.app.util.ActionItem;
import com.Ramali.android.app.util.LazyAdapter_motel;
import com.Ramali.android.app.util.QuickAction;


public class MotelList extends Activity {
	
	private static final int PHONE = 1;
	private static final int TEXT = 2;
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_PRICE = "rent";//price
	public static final String KEY_ROOMS = "rooms";
	public static final String KEY_REF = "ref";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_IMAGE_URL = "image_url";
    //public static String URL="http://10.0.2.2/android/api_motel.php";	
	public static String URL="http://akajaymo.kodingen.com/api_motel.php";
	ListView list;
	LazyAdapter_motel adapter;
	MediaPlayer mp;
	ImageView selected;
    QuickAction quickAction_motel_list;
    ActionItem telephone,textmessage;
    ActionItem actionItem ;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.placeholder);
        setTitleFromActivityLabel(R.id.title_text);
        selected	= (ImageView)findViewById(R.id.arrw);
        
        ImageButton Search = (ImageButton)findViewById(R.id.srch_btn);
        Search.setVisibility(View.GONE);
        
        telephone	= new ActionItem(PHONE, "Phone", getResources().getDrawable(R.drawable.phone));
        textmessage	= new ActionItem(TEXT, "Text", getResources().getDrawable(R.drawable.sms));
   	    
   	    
        ArrayList<HashMap<String, String>> motellist = new ArrayList<HashMap<String, String>>(); 
        JSONObject json = JSONfunctions.getJSONfromURL(URL);
     
        
        try{
        	
        	JSONArray  mot = json.getJSONArray("MOTEL");
	        for(int i=0;i<mot.length();i++){						
	        	HashMap<String, String> map = new HashMap<String, String>();		
				JSONObject e = mot.getJSONObject(i);
				map.put(KEY_ID, e.getString(KEY_ID));
				map.put(KEY_NAME, e.getString(KEY_NAME));
				map.put(KEY_PRICE, e.getString(KEY_PRICE));
				map.put(KEY_ROOMS, e.getString(KEY_ROOMS));
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_PHONE, e.getString(KEY_PHONE));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				motellist.add(map);
			}		
        }catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.lis);
        adapter=new LazyAdapter_motel(this, motellist);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        		
        		quickAction_motel_list = new QuickAction(MotelList.this, QuickAction.HORIZONTAL);
           	    quickAction_motel_list.addActionItem(telephone);
           	    quickAction_motel_list.addActionItem(textmessage);
        		quickAction_motel_list.show(view);
        		
        		quickAction_motel_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			

        			public void onItemClick(QuickAction source, int pos, int actionId) {				
        				actionItem = quickAction_motel_list.getActionItem(pos);
        	             
        				if (actionId == PHONE) {        					
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String PHONENUMBER =o.get(KEY_PHONE);
    						String number ="tel:"+PHONENUMBER.toString().trim();
    						Intent call = new Intent (Intent.ACTION_DIAL,Uri.parse(number));
    		        		startActivity(call);	
        						
        				} else if (actionId == TEXT) {
        					HashMap<String, String> o = (HashMap<String, String>) list.getAdapter().getItem(position);
    						String PHONENUMBER =o.get(KEY_PHONE);
    						String NAME =o.get(KEY_NAME);
    						Intent sendIntent= new Intent(Intent.ACTION_VIEW);
    						sendIntent.putExtra("sms_body", "KwaRamani:"+NAME);
    						sendIntent.putExtra("address",PHONENUMBER );
    						sendIntent.setType("vnd.android-dir/mms-sms"); 
    						startActivity(sendIntent);
        					
        				} 
        				else {
        				}
        			}
        		});
        		quickAction_motel_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
        			@Override
        			public void onDismiss() {}});        			
        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
        	}
		});
    }
    public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
    public void onClickSearch (View v)
	{
    	
	}
    public void onClickAdd (View v)
	{
		Intent intent = new Intent(MotelList.this, AddMotel.class);
        startActivity(intent);
        this.finish();
	}
    public void onClickHome (View v)
	{
    	this.finish();
	}
}
