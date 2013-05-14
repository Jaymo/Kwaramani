package com.Ramali.android.app;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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

public class Rental extends UserLocationMap {
	public static int update = 0;
	Drawable drawable;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	List<Overlay> mapOverlays;
	public static String URL="http://akajaymo.kodingen.com/api_rental.php";
	public static String LINK="http://akajaymo.kodingen.com/api_update.php";
	

	@Override
	 public void onCreate(Bundle savedInstanceState)
	 {
	 super.onCreate(savedInstanceState);
	 requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.rental);
	 setTitleFromActivityLabel(R.id.title_text);
	 
       mapView = (MapView)findViewById(R.id.mapView);
   	   mapController = mapView.getController();
   	   
       initializeMap();
       
       JSONObject json_updt = JSONfunctions.getJSONfromURL(LINK);
   	try{
       	JSONArray  updates = json_updt.getJSONArray("UPDATES");
       	for(int i=0;i<1;i++){
   			JSONObject e = updates.getJSONObject(i);
   			update= e.getInt("MAX(id)");
   			
           	
       	}
   	}catch(JSONException e)      {
      	 Log.e("log_tag", "Error kupitisha data ya updates "+e.toString());
      }
   	Ramanipref.loadSettings(this);
   	Ramanipref.rentals=update;
   	Ramanipref.saveSettings(this);
	
	 }
	private void initializeMap() {
		 
		 
		 JSONObject json = JSONfunctions.getJSONfromURL(URL); 
		 try{
			    List<Overlay> mapOverlays = mapView.getOverlays();
		     	JSONArray  places = json.getJSONArray("RENTAL");
		     	
			        for(int i=0;i<places.length();i++){							
						JSONObject e = places.getJSONObject(i);
			    	    String name=    e.getString("name");
			    	    String rent=   e.get("rent").toString();
			    	    String phone=  e.get("phone").toString();
			    	    
			    	    drawable = this.getResources().getDrawable(R.drawable.rental);
			    	    itemizedOverlay = new CustomItemizedOverlay<CustomOverlayItem>(drawable, mapView);
			    	    
			    	    GeoPoint point = new GeoPoint((int)(e.getDouble("lat")*1E6),(int) (e.getDouble("lon")*1E6));			    	    
			    	    CustomOverlayItem overlayItem = new CustomOverlayItem(point,"Rental: "+name , " Price: "+rent+" Phone: "+phone);			    	      	    
			    	    itemizedOverlay.addOverlay((CustomOverlayItem) overlayItem);			    	    
			    	    mapOverlays.add(itemizedOverlay);
			        }		
		     }catch(JSONException e)        {
		     	 Log.e("log_tag", "Error kupitisha JSON "+e.toString());
		     }
		
	}
	 
		
		
		public void onClickHome (View v)
		{
		    goHome (this);
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
