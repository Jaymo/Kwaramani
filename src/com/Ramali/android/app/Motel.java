package com.Ramali.android.app;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.Ramali.android.app.functions.CustomItemizedOverlay;
import com.Ramali.android.app.functions.CustomOverlayItem;
import com.Ramali.android.app.functions.JSONfunctions;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class Motel extends UserLocationMap {

	Drawable drawable;
	String motel1="",query;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	List<Overlay> mapOverlays;
	static JSONObject json ;
	public static String URL="http://akajaymo.kodingen.com/api_motel.php";
	
	
	@Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	 super.onCreate(savedInstanceState);
	 requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.motel); 
	 setTitleFromActivityLabel(R.id.title_text);   
     mapView = (MapView)findViewById(R.id.mapView);
 	 mapController = mapView.getController();
     
	   initializeMap();	
	 }
	 
		 
		 
		 
		 private void initializeMap() {
			 
			 JSONObject json = JSONfunctions.getJSONfromURL(URL); 
		   
			 try{
				 List<Overlay> mapOverlays = mapView.getOverlays();
		     	 JSONArray  places = json.getJSONArray("MOTEL");
		     	
			        for(int i=0;i<places.length();i++){							
						JSONObject e = places.getJSONObject(i);
			    	    String name=    e.getString("name");
			    	    String price=   e.get("rent").toString();
			    	    String phone=  e.get("phone").toString();
			    	    drawable = this.getResources().getDrawable(R.drawable.motel);
			    	    itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
			    	    GeoPoint point = new GeoPoint((int)(e.getDouble("lat")*1E6),(int) (e.getDouble("lon")*1E6));			    	    
			    	    CustomOverlayItem overlayItem = new CustomOverlayItem(point,"Motel: "+name , " Price: "+price+" Phone: "+phone);			    	      	    
			    	    itemizedOverlay.addOverlay((CustomOverlayItem) overlayItem);			    	    
			    	    mapOverlays.add(itemizedOverlay);
			        }		
		     }
		     catch(JSONException e)        {
		     	 Log.e("log_tag", "Error kupitisha JSON "+e.toString());
		     }
		     
		 }
		
	
	
		
		public void onClickHome (View v)
		{
		    goHome (this);
		}
		public void onClickAdd (View v)
		{
			Intent intent = new Intent(Motel.this, AddMotel.class);
            startActivity(intent);
            this.finish();
		}
			
		private void goHome(Context context) {
	        this.finish();
			
		}
		
	 @Override
	 protected boolean isRouteDisplayed()
	 {
	 return false;
	 
	 } 
	  
	 @Override
	 protected void onResume() {
	        super.onResume();
	        initializeMap();
	    }
	 @Override
	 public void onPause() {
		    super.onPause();
	    }
	 
	    
	    public void setTitleFromActivityLabel (int textViewId)
	    {
	        TextView tv = (TextView) findViewById (textViewId);
	        if (tv != null) tv.setText (getTitle ());
	    }




		@Override
		protected void updateInterface() {
			
		}
	    }
	    
	    
	 

