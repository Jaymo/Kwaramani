package com.Ramali.android.app;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.Ramali.android.app.functions.CustomItemizedOverlay;
import com.Ramali.android.app.functions.CustomOverlayItem;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class Houses extends UserLocationMap {
	Drawable drawable;
	static JSONObject json ;
	CustomItemizedOverlay<CustomOverlayItem> itemizedOverlay;
	List<Overlay> mapOverlays;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.houses);
        setTitleFromActivityLabel(R.id.title_text);
        
        mapView = (MapView)findViewById(R.id.mapView);
    	mapController = mapView.getController();
    	 
   	   
   	 try{
   		List<Overlay> mapOverlays = mapView.getOverlays();
      	JSONArray  places = json.getJSONArray("SALE");
      	
 	        for(int i=0;i<places.length();i++){							
 				JSONObject e = places.getJSONObject(i);
 	    	    String name=   e.getString("location");
 	    	    String price=  e.get("price").toString();
 	    	    String place=  e.get("place").toString();
 	    	    String units=  e.get("units").toString();
 	    	    String phone=  e.get("phone").toString();
 	    	    
 	    	    drawable = this.getResources().getDrawable(R.drawable.house);
 	    	    RamaliItemizedOverlay itemizedoverlay = new RamaliItemizedOverlay(drawable,this);
 	    	    GeoPoint point = new GeoPoint((int)(e.getDouble("lat")*1E6),(int) (e.getDouble("lon")*1E6));
	    	    OverlayItem overlayitem = new OverlayItem(point,"Houses on SALE" , "Location:"+place+"\nNeighbourhood: "+name+"\nPrice: "+price+"\nPhone: 0"+phone+"\nAvailable Units: "+units);
 	    	    itemizedoverlay.addOverlay(overlayitem);
 	    	    mapOverlays.add(itemizedoverlay);
 	        }		
      }catch(JSONException e)        {
      	 Log.e("log_tag", "Error kupitisha JSON "+e.toString());
      }
   
 	
   	    
	}
	
	public static void mapping(JSONObject jArray) {
		  json = jArray;
		
	}
	public void onClickHome (View v)
	{
	    this.finish();
	}
	
	 public void onClickSearch (View v)
		{
		    final Intent intent = new Intent(this, BuyHouse.class);
	        startActivity (intent);
	        this.finish(); 
		}
		
	@Override
	protected boolean isRouteDisplayed() {
		return false;
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

