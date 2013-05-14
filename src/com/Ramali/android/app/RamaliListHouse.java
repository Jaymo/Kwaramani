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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.Ramali.android.app.util.ActionItem;
import com.Ramali.android.app.util.LazyAdapter_home_buyer;
import com.Ramali.android.app.util.QuickAction;



public class RamaliListHouse extends Activity {
	
	private static final int PHONE = 1;
	private static final int TEXT = 2;
	
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "location";
	public static final String KEY_PRICE = "price";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_UNITS = "units";
	public static final String KEY_REF = "ref";
	public static final String KEY_IMAGE_URL = "image_url";
    ListView list;
    LazyAdapter_home_buyer adapter;
    MediaPlayer mp;
    static JSONObject json ;
    ImageView selected;
    QuickAction quickAction_buyer_list;
    ActionItem telephone,textmessage;
    ActionItem actionItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.placeholder);
        setTitleFromActivityLabel(R.id.title_text);
        ImageButton ADD = (ImageButton)findViewById(R.id.add_btn);
        ADD.setVisibility(View.GONE);
        selected	= (ImageView)findViewById(R.id.arrw);
        
        telephone	= new ActionItem(PHONE, "Phone", getResources().getDrawable(R.drawable.phone));
        textmessage	= new ActionItem(TEXT, "Text", getResources().getDrawable(R.drawable.sms));
   	    
   	    
   
        ArrayList<HashMap<String, String>> Buyerlist = new ArrayList<HashMap<String, String>>();
         try{
        	
        	JSONArray  HAO = json.getJSONArray("SALE");
        	
	        for(int i=0;i<HAO.length();i++){						
				HashMap<String, String> map = new HashMap<String, String>();	
				JSONObject e = HAO.getJSONObject(i);
				
				map.put(KEY_ID, e.getString(KEY_ID));
				map.put(KEY_NAME, e.getString(KEY_NAME));
				map.put(KEY_PRICE, e.getString(KEY_PRICE));
				map.put(KEY_PHONE, e.getString(KEY_PHONE));
				map.put(KEY_UNITS, e.getString(KEY_UNITS));
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				Buyerlist.add(map);
			}		
        }catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.lis);
        adapter=new LazyAdapter_home_buyer (this, Buyerlist);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { 
        		quickAction_buyer_list = new QuickAction(RamaliListHouse.this, QuickAction.HORIZONTAL);
           	    quickAction_buyer_list.addActionItem(telephone);
           	    quickAction_buyer_list.addActionItem(textmessage);
        		quickAction_buyer_list.show(view);

        		quickAction_buyer_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
        			@Override
        			public void onItemClick(QuickAction source, int pos, int actionId) {				
        				actionItem = quickAction_buyer_list.getActionItem(pos);
        	             
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
        		quickAction_buyer_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
        			@Override
        			public void onDismiss() {}});
        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
			}
		});
    }
    public static void mapping(JSONObject jArray) {
		  json = jArray;
		
	}
    public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
    public void onClickSearch (View v)
	{
    	final Intent intent = new Intent(this, BuyHouse.class);
        startActivity (intent);
        this.finish();
	}
    public void onClickHome (View v)
	{
    	this.finish();
	}
}
