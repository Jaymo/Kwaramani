package com.Ramali.android.app;



import android.content.Context;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;


public class RamaliLocationOverlay extends MyLocationOverlay {

	private Context context;

	public RamaliLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		this.context = context;  
	}
	
	@Override
	protected boolean dispatchTap(){
		if(RamaliDisplayOverlay.showData){ 
			Toast.makeText(context, "Retracting data readout", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context,"Display data readout", Toast.LENGTH_SHORT).show();
		}
		
		RamaliDisplayOverlay.showData = ! RamaliDisplayOverlay.showData;
		return true;
	}
}

