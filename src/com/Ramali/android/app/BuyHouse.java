package com.Ramali.android.app;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Ramali.android.app.util.ActionItem;
import com.Ramali.android.app.util.QuickAction;
import com.Ramali.android.app.util.Util;


public class BuyHouse  extends Activity{
	private static final int LIST_HOUSES = 1;
	private static final int MAP_HOUSES = 2;
	 Spinner   s,s1,s2,s3;
	 static String  place,price,type,rooms,eprice;
	 Button search;
	 int cash;
	 MediaPlayer mp; 
	 final Context mContext = this;
	 private String mErrorMessage = "";
	 private boolean mError = false;
	 public static String URL="http://akajaymo.kodingen.com/api_homebuyer.php";
	 QuickAction quickAction_buyer_list;
	 ActionItem actionItem;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.buy_house);
        setTitleFromActivityLabel(R.id.title_text);
        search = (Button)findViewById(R.id.search);
        
        ActionItem phn	= new ActionItem(LIST_HOUSES, "List Results", getResources().getDrawable(R.drawable.list));
   	    ActionItem sms	= new ActionItem(MAP_HOUSES, "Map Results", getResources().getDrawable(R.drawable.map));
   	     quickAction_buyer_list = new QuickAction(BuyHouse.this, QuickAction.VERTICAL);
   	     quickAction_buyer_list.addActionItem(phn);
   	     quickAction_buyer_list.addActionItem(sms);
        
         s = (Spinner) findViewById(R.id.spn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        
         s1 = (Spinner) findViewById(R.id.spn1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.PriceLimit, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        
         s2 = (Spinner) findViewById(R.id.spn2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.PropertyType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
         s3 = (Spinner) findViewById(R.id.spn3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.BedRooms, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(adapter3);
        
        handlesearch();

}
	private void handlesearch() {
		
  	search.setOnClickListener(new View.OnClickListener() {
    		
			@Override
			public void onClick(View v) {
				place = (String) s.getSelectedItem();
				price = (String) s1.getSelectedItem();
				type = (String)  s2.getSelectedItem();
				rooms = (String) s3.getSelectedItem();
				mError = false;
				
            	if(place.equals("Select Location"))
                {
                	mErrorMessage = getString(R.string.empty_place)+"\n";
                    mError = true;	
                }
            	if(price.equals("Select PriceLimit"))
                {
                	mErrorMessage += getString(R.string.empty_price)+"\n";
                    mError = true;	
                }
                
            	if(type.equals("Select PropertyType"))
                {
                	mErrorMessage += getString(R.string.empty_type)+"\n";
                    mError = true;	
                }
            	if(rooms.equals("Select BedRooms"))
                {
                	mErrorMessage += getString(R.string.empty_rooms)+"\n";
                    mError = true;	
                }
            	if (!mError) {
            		quickAction_buyer_list.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
            			@Override
            			public void onItemClick(QuickAction source, int pos, int actionId) {				
            				actionItem = quickAction_buyer_list.getActionItem(pos);
            	             
            				if (actionId == LIST_HOUSES) {        					
            					if (Util.isConnected(BuyHouse.this)) {
            						new fetch() {
            							public void onPostExecute(JSONObject jArray) {
            								if(jArray!=null){
            									this.progressDialog.dismiss();
            									RamaliListHouse.mapping(jArray);
            									startActivity (new Intent(getApplicationContext(), RamaliListHouse.class));
            									BuyHouse.this.finish();
            									
            								}
            								else
            								{
            								this.progressDialog.dismiss();
            								Toast.makeText(BuyHouse.this,
            					      	         "\nNo House listed fitting the \nspecified criteria.\n", Toast.LENGTH_LONG).show();  
            								}
            							}
            							
            						}.execute();
            						}
            						else
            						{
            							Toast.makeText(BuyHouse.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
            						}
            						
            				} else if (actionId == MAP_HOUSES) {
            					
            					if (Util.isConnected(BuyHouse.this)) {
            						new fetch() { 
            							public void onPostExecute(JSONObject jArray) {
            								if(jArray!=null){
            									this.progressDialog.dismiss();
            									Houses.mapping(jArray);
            									startActivity (new Intent(getApplicationContext(), Houses.class));
            									BuyHouse.this.finish();
            									
            								}
            								else
            								{
            								this.progressDialog.dismiss();
            								Toast.makeText(BuyHouse.this,
            										"\nNo House listed fitting the \nspecified criteria.\n", Toast.LENGTH_LONG).show();  
            								}
            							}
            						}
            						.execute();
            						}
            						else
            						{
            							Toast.makeText(BuyHouse.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
            							
            						}
            						
            					
            					}
            			 
            				else {
            				}
            			}
            		});
            		 quickAction_buyer_list.show(v);
            		 quickAction_buyer_list.setOnDismissListener(new QuickAction.OnDismissListener() {			
            			@Override
            			public void onDismiss() {
  
            			}});
            		mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
					mp.start();
            	}
            	else {
                    final Toast t = Toast.makeText(BuyHouse.this, "Error!!!\n" + mErrorMessage,
                            Toast.LENGTH_LONG);
                    t.show();
                    mErrorMessage = "";
                  
                }
                
                
                
			}
		});
		
	}
	
	public void onClickHome (View v)
	{
	    this.finish();
	}
	
	public void onClickSearch (View v)
	{
	}
	
	public class fetch extends AsyncTask<String, Integer, JSONObject> {
		  ProgressDialog progressDialog = new ProgressDialog(BuyHouse.this);
		 
		protected void onPreExecute() {
			progressDialog.setMessage("Fetching data....");
	        progressDialog.show();
	        if(price.equals("1,000,000"))
			{
				cash=1000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("2,000,000"))
			{
				cash=2000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("3,000,000"))
			{
				cash=3000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("4,000,000"))
			{
				cash=4000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("5,000,000"))
			{
				cash=5000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("6,000,000"))
			{
				cash=6000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("7,000,000"))
			{
				cash=7000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
			if(price.equals("8,000,000"))
			{
				cash=8000000;
				eprice=String.valueOf(cash);
				return;
				 
			}
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			InputStream is = null;
			String result = "";
			JSONObject jArray = null;
			ArrayList<NameValuePair> namevaluepairs;
		    try{
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost(URL);
		            namevaluepairs = new ArrayList<NameValuePair>(4);
					namevaluepairs.add (new BasicNameValuePair("place",place));
					namevaluepairs.add (new BasicNameValuePair("price",eprice));
					namevaluepairs.add (new BasicNameValuePair("type",type));
					namevaluepairs.add (new BasicNameValuePair("rooms",rooms));
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
	
		public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
}

		
	
	
	
		
		


    
	
