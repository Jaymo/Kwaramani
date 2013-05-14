package com.Ramali.android.app;
//android/api_rental.php
//units
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.Ramali.android.app.functions.JSONfunctions;
import com.Ramali.android.app.util.ActionItem;
import com.Ramali.android.app.util.LazyAdapter_rental;
import com.Ramali.android.app.util.QuickAction;
import com.Ramali.android.app.util.Util;


public class RentalList extends Activity {
	/** Called when the activity is first created. */
    public static int updt = 0;
    String rental_search="",query;
    static final String[] estates;
    private static final int PHONE = 1;
	private static final int TEXT = 2;
    static
	{
	String[] arrayOfString = new String[11]; 
	
		arrayOfString[0] ="BuruBuru";
		arrayOfString[1] ="Eastlands";
		arrayOfString[2] ="Kileleshwa";
		arrayOfString[3] ="Githurai";
		arrayOfString[4] ="Zimmerman";
		arrayOfString[5] ="Kahawa sukari";
		arrayOfString[6] ="Kahawa wendani";
		arrayOfString[7] ="Pangani";
		arrayOfString[8] ="Karen";
		arrayOfString[9] ="Roysambu";
		arrayOfString[10]="Ngara";
		estates = arrayOfString;
	}
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_RENT = "rent";
	public static final String KEY_UNITS = "units";//rooms
	public static final String KEY_REF = "ref";
	public static final String KEY_PHONE = "phone";
	public static final String KEY_IMAGE_URL = "image_url";
	
	//public static String URL="http://10.0.2.2/android/api_rental.php";
	//public static String Updatestracker="http://10.0.2.2/android/api_update.php";
	//public static String search="http://10.0.2.2/android/tintin.php";
	
	public static String URL="http://akajaymo.kodingen.com/api_rental.php";
	public static String Updatestracker="http://akajaymo.kodingen.com/api_update.php";
	public static String search="http://akajaymo.kodingen.com/api_search.php";
	ListView list;
	LazyAdapter_rental adapter;
	MediaPlayer mp;
	ImageView selected;
	static JSONObject json ;
	QuickAction quickAction_rental_list,quickAction_rental_search;
	ActionItem actionItem,MactionItem;
	ActionItem telephone,textmessage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.placeholder);
        setTitleFromActivityLabel(R.id.title_text);
        selected	= (ImageView)findViewById(R.id.arrw);
        ImageButton ADD = (ImageButton)findViewById(R.id.add_btn);
        ADD.setVisibility(View.GONE);
        String[] arrayOfString = estates;
        ArrayAdapter<String> adapter_rental = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,arrayOfString);
        AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.query);    
        acTextView.setThreshold(3);
        acTextView.setAdapter(adapter_rental);
        
        final ActionItem phn	= new ActionItem(PHONE, "Phone", getResources().getDrawable(R.drawable.phone));
   	    final ActionItem sms	= new ActionItem(TEXT, "Text", getResources().getDrawable(R.drawable.sms));
   	    
   	    
   	     telephone	= new ActionItem(PHONE, "Phone", getResources().getDrawable(R.drawable.phone));
	     textmessage	= new ActionItem(TEXT, "Text", getResources().getDrawable(R.drawable.sms));
	    
		
        ArrayList<HashMap<String, String>> rentalist = new ArrayList<HashMap<String, String>>();     
        json = JSONfunctions.getJSONfromURL(URL);
     
        try{
        	
           JSONArray  mot = json.getJSONArray("RENTAL");
            
	        for(int i=0;i<mot.length();i++){						
	        	HashMap<String, String> map = new HashMap<String, String>();	
				JSONObject e = mot.getJSONObject(i);
				map.put(KEY_ID, e.getString(KEY_ID));
				
				map.put(KEY_NAME, e.getString(KEY_NAME));
				map.put(KEY_RENT, e.getString(KEY_RENT));
				map.put(KEY_UNITS, e.getString(KEY_UNITS));
				map.put(KEY_REF, e.getString(KEY_REF));
				map.put(KEY_PHONE, e.getString(KEY_PHONE));
				map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
				rentalist.add(map);
	        	
	        				
			}		
        }catch(JSONException e)      {
        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
        }
        list=(ListView)findViewById(R.id.lis);
        adapter=new LazyAdapter_rental(this, rentalist);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { 
        		
        		quickAction_rental_list = new QuickAction(RentalList.this, QuickAction.HORIZONTAL);
           	    quickAction_rental_list.addActionItem(phn);
           	    quickAction_rental_list.addActionItem(sms);
           	    quickAction_rental_list.show(view);
           	    
           	    quickAction_rental_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
     			public void onItemClick(QuickAction source, int pos, int actionId) {				
     				actionItem = quickAction_rental_list.getActionItem(pos);
     	             
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
     				quickAction_rental_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
     					@Override
     					public void onDismiss() {
     						
     					}});
     					
     			}
     		});
           	   
        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
        		
        		
        		
			}
		});
        
   
   
   
    JSONObject json_updt = JSONfunctions.getJSONfromURL(Updatestracker);
	try{
    	JSONArray  updates = json_updt.getJSONArray("UPDATES");
    	for(int i=0;i<1;i++){
			JSONObject e = updates.getJSONObject(i);
			updt= e.getInt("MAX(id)");
			
        	
    	}
	}catch(JSONException e)      {
   	 Log.e("log_tag", "Error kupitisha data ya updates "+e.toString());
   }
	Ramanipref.loadSettings(this);
	Ramanipref.rentals=updt;
	Ramanipref.saveSettings(this);
	
 }
    public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
    public void onClickSearch (View v)
	{

		LinearLayout search1 = (LinearLayout)findViewById(R.id.Search);
		LinearLayout top = (LinearLayout)findViewById(R.id.top);
	      
	    	  search1.setVisibility(View.VISIBLE);
	    	  top.setVisibility(View.GONE);   
	}
    public void onClickHome (View v)
	{
    	this.finish();
	}
    public void onClickfind(View v)
	{
		AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.query);
		rental_search =acTextView.getText().toString();
		if (acTextView.getText().toString().equals(""))
		{
			Toast.makeText(RentalList.this, "\nSearch Field is Blank\n please type in an Estate\n ", Toast.LENGTH_LONG).show();
			
		}
		else
		{
			LinearLayout search1 = (LinearLayout)findViewById(R.id.Search);
			LinearLayout top = (LinearLayout)findViewById(R.id.top);
			search1.setVisibility(View.GONE);
	    	top.setVisibility(View.VISIBLE);
	    	
			new fetch(){ 
				public void onPostExecute(JSONObject jArray) {
					if(jArray!=null){
						  this.progressDialog.dismiss();
						  ArrayList<HashMap<String, String>> rentalist = new ArrayList<HashMap<String, String>>();   
						  json=jArray;
						  
				        try{
				        	
				           JSONArray  mot = json.getJSONArray("RENTAL");
				            
					        for(int i=0;i<mot.length();i++){						
					        	HashMap<String, String> map = new HashMap<String, String>();	
								JSONObject e = mot.getJSONObject(i);
								map.put(KEY_ID, e.getString(KEY_ID));
								
								map.put(KEY_NAME, e.getString(KEY_NAME));
								map.put(KEY_RENT, e.getString(KEY_RENT));
								map.put(KEY_UNITS, e.getString(KEY_UNITS));
								map.put(KEY_REF, e.getString(KEY_REF));
								map.put(KEY_PHONE, e.getString(KEY_PHONE));
								map.put(KEY_IMAGE_URL, e.getString(KEY_IMAGE_URL));
								rentalist.add(map);
					        	
					        				
							}		
				        }catch(JSONException e)      {
				        	 Log.e("log_tag", "Error kupitisha data "+e.toString());
				        }
				        list=(ListView)findViewById(R.id.lis);
				        adapter=new LazyAdapter_rental(RentalList.this, rentalist);
				        list.setAdapter(adapter);
				        list.setTextFilterEnabled(true);
				        list.setOnItemClickListener(new OnItemClickListener() {
				        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) { 
				        		
				        		quickAction_rental_search = new QuickAction(RentalList.this, QuickAction.HORIZONTAL);
				        		quickAction_rental_search.addActionItem(telephone);
				        		quickAction_rental_search.addActionItem(textmessage);
				        		quickAction_rental_search.show(view);
				        		quickAction_rental_search.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
				        			public void onItemClick(QuickAction source, int pos, int actionId) {				
				        				ActionItem actionItem = quickAction_rental_search.getActionItem(pos);
				        	             
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
				        		quickAction_rental_search.setOnDismissListener(new QuickAction.OnDismissListener() {			
				        			@Override
				        			public void onDismiss() {
				        			}});
				        		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
				                mp.start();
							}
						});
				        
				    
				       
					}
					else
					{
					this.progressDialog.dismiss();
					Toast.makeText(RentalList.this,
							"\nSorry No House listed in the \nspecified area.\n", Toast.LENGTH_LONG).show();  
					}
				}
			}
			.execute();
		}
	}
    public class fetch extends
    AsyncTask<String, Integer, JSONObject> {
		protected ProgressDialog progressDialog = new ProgressDialog(RentalList.this);
	
        protected void onPreExecute() {
		
        	progressDialog.setMessage("Downloading data....");
	         progressDialog.show();
	         
    		    }

		@Override
		protected JSONObject doInBackground(String... params) {
			
			InputStream is = null;
			String result = "";
			JSONObject jArray = null;
			ArrayList<NameValuePair> namevaluepairs;
		    try{
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost(search);
		            namevaluepairs = new ArrayList<NameValuePair>(1);
					namevaluepairs.add (new BasicNameValuePair("parameter",rental_search));
					httppost.setEntity (new UrlEncodedFormEntity(namevaluepairs));
		            HttpResponse response = httpclient.execute(httppost);
		            HttpEntity entity = response.getEntity();
		            is = entity.getContent();
		            
		            

		       }catch(Exception e){
		            Log.e("log_tag", "Error in http connection "+e.toString());
		      }
		 
		      try{
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                    sb.append(line + "\n");
		            }
		            is.close();
		            result=sb.toString();
		      }
		      catch(Exception e){
		            Log.e("log_tag", "Error converting result "+e.toString());
		       }
              try{
		    	
	            jArray = new JSONObject(result);            
		      }
               catch(JSONException e){
		            Log.e("log_tag", "Error parsing data "+e.toString());
		       }
			  return jArray;
		  }
		}
				
}

