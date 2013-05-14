package com.Ramali.android.app;

import com.google.android.maps.MapView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class LocateMe extends UserLocationMap{

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.locateme);
   	    setTitleFromActivityLabel(R.id.title_text);
        initComponents();
    }
	
	private void initComponents() {
		mapView = (MapView)findViewById(R.id.mapView);
		mapController = mapView.getController();
		
	}
	@Override
	protected void updateInterface() {
		
	}
	
	public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
	
	public void onClickHome (View v)
	{
	    this.finish();
	}

}
