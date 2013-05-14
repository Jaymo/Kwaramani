package com.Ramali.android.app;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;


public class FindMe extends MapActivity implements LocationListener {
	
	private float satAccuracy = 2000,speed,bearing,GPSmoveInterval;
	private static double lat,lon;
	private MapController mapControl;
	private MapView mapView;
	LocationManager locman;
    Location loc;
    String provider = LocationManager.GPS_PROVIDER;
    String TAG = "KwaRamani";
	Bundle locBundle;
	private int numberSats = -1;
	private double altitude;
	private String currentProvider;
	long GPSupdateInterval;      
	private RamaliLocationOverlay myLocationOverlay; 
	private List<Overlay> mapOverlays;
	public RamaliDisplayOverlay displayOverlay;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map); 
        
        updateGPSprefs();
        
 	    locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 	
 	    final GpsStatus.Listener onGpsStatusChange = new GpsStatus.Listener(){
		 	public void onGpsStatusChanged(int event){
			 	switch(event){
				 	case GpsStatus.GPS_EVENT_STARTED:
				 		// GPS started...
				 		Toast.makeText(FindMe.this, "Powering GPS engine....... ", Toast.LENGTH_LONG).show();
				 	break;
				 	case GpsStatus.GPS_EVENT_FIRST_FIX:
				 		// First Fix...
				 		Toast.makeText(FindMe.this, "GPS has obtained a Lock", Toast.LENGTH_LONG).show();
				 	break;
				 	case GpsStatus.GPS_EVENT_STOPPED:
				 		// Stopped...
				 	break;
				 	case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				 		// Satellite update
				 	break;
			 	}
			 	
		 	}
	 	};

	 	locman.addGpsStatusListener(onGpsStatusChange);
	    locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);  
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setBuiltInZoomControls(true); 
        mapControl = mapView.getController();
        mapControl.setZoom(14);
        List<Overlay> overlays = mapView.getOverlays();
        myLocationOverlay = new RamaliLocationOverlay(this, mapView);
        overlays.add(myLocationOverlay);
        displayOverlay = new RamaliDisplayOverlay();
        mapOverlays = mapView.getOverlays();
        mapOverlays.add(displayOverlay);
	}
	
	
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;  
	}

	
	@Override
	public void onLocationChanged(Location location) {
		centerOnLocation();
	}

	
	@Override
	public void onProviderDisabled(String provider) {
		
		
		locman.removeUpdates(this);	
	}

	
	@Override
	public void onProviderEnabled(String provider) {
		
		locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);
	}

	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
		centerOnLocation();	
	}
	
    private void centerOnLocation() {
    	loc = locman.getLastKnownLocation(provider);
    	if(loc != null){
    		lat = loc.getLatitude();
   	    	lon = loc.getLongitude();
	    	GeoPoint newPoint = new GeoPoint((int)(lat*1e6),(int)(lon*1e6)); 
	    	mapControl.animateTo(newPoint);
	    	getSatelliteData();
	    	if(displayOverlay != null){
	    		displayOverlay.putSatStuff(lat, lon, satAccuracy, bearing, altitude,
	    				speed, currentProvider, numberSats);
	    	}
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        int groupId = 0;
        int menuItemOrder = Menu.NONE;
        int menuItemId1 = Menu.FIRST;
        int menuItemId2 = Menu.FIRST+1;
        int menuItemText1 = R.string.settings;
        int menuItemText2 = R.string.home;
        @SuppressWarnings("unused")
		MenuItem menuItem1 = menu.add(groupId, menuItemId1, menuItemOrder, menuItemText1)
                .setIcon(R.drawable.ic_menu_settings);
        @SuppressWarnings("unused")
		MenuItem menuItem2 = menu.add(groupId, menuItemId2, menuItemOrder, menuItemText2)
                .setIcon(R.drawable.ic_menu_home);
        return true;
    }  
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case (Menu.FIRST):
				Intent p = new Intent(this, RamaliPrefs.class);
				startActivity(p);
				return true;
                case (Menu.FIRST+1):
                	FindMe.this.finish();
                	return true;
        }
        return false;
    }
    
    
       public void getSatelliteData(){
    	if(loc != null){
    		
	   	    locBundle = loc.getExtras();
	   	    if(locBundle != null){ 
	   	    	numberSats = locBundle.getInt("satellites",-1);
	   	    	
	   	    }
			satAccuracy = loc.getAccuracy();
			bearing = loc.getBearing();
			altitude = loc.getAltitude();
			speed = loc.getSpeed();
    	}
    }
	public void onPause() {
		super.onPause();
		
		if(RamaliPrefs.getCompass(getApplicationContext())) myLocationOverlay.disableCompass();
        myLocationOverlay.disableMyLocation();
		locman.removeUpdates(this);
	}
	
	public void onResume(){
		super.onResume();
		updateGPSprefs();
	 	locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);	
	 	if(RamaliPrefs.getCompass(getApplicationContext())) myLocationOverlay.enableCompass();
        myLocationOverlay.enableMyLocation();
        
	}
	
	
	public void updateGPSprefs(){
		int gpsPref = Integer.parseInt(RamaliPrefs.getGPSPref(getApplicationContext()));
		switch(gpsPref){
		case 1:
			GPSupdateInterval = 5000; 
			GPSmoveInterval = 1;      
			break;
		case 2:
			GPSupdateInterval = 10000;
			GPSmoveInterval = 100;
			break;
		case 3:
			GPSupdateInterval = 125000;
			GPSmoveInterval = 1000;
			break;
		}	
	}
}
