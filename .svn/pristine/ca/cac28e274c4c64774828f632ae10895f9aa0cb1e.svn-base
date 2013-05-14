package com.Ramali.android.app;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class RamaliDisplayOverlay extends Overlay {
	
	private Paint paint;
	private double lat;
	private double lon;
	private double satAccuracy;
	private int numberSats;
	private float bearing;
	private double altitude;
	private float speed;
	@SuppressWarnings("unused")
	private String currentProvider;
	public static boolean showData = true;
	
	@Override
	public void draw(Canvas canvas, MapView mapview, boolean shadow) {
		super.draw(canvas, mapview, shadow);
		if(showData){
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setARGB(80,255,255,255);
			canvas.drawRect(0,0,350,33,paint);
			paint.setTextSize(15);
			paint.setARGB(180, 0, 0, 0);
			
		
		}
	}
	public void putSatStuff(double lat, double lon, double satAccuracy, float bearing, double altitude,
			float speed, String currentProvider, int numberSats){
		this.lat = lat;
		this.lon = lon;
		this.satAccuracy = satAccuracy;
		this.bearing = bearing;
		this.altitude = altitude;
		this.speed = speed;
		this.currentProvider = currentProvider;
		this.numberSats = numberSats;	
	}
}


