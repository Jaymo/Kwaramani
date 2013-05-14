package com.Ramali.android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.Ramali.android.app.util.ActionItem;
import com.Ramali.android.app.util.QuickAction;
import com.Ramali.android.app.util.Util;
public class RamaliHome extends Activity{
	private Button findme,buyhouse,rental,motel,Mabout,exit;
	MediaPlayer mp; 
	final Context mContext = this; 
	private static final int MAP_HOUSE = 1;
	private static final int LIST_HOUSE = 2;
	
	private static final int ABOUT = 1;
	private static final int COMMENT = 2;
	
	ActionItem actionItem,MactionItem,NactionItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);    
        setTitleFromActivityLabel(R.id.title_text);
        
        Intent startIntent = new Intent(this, RamaliService.class);
        this.startService(startIntent);
        
        findme = (Button)findViewById(R.id.findme);
        buyhouse = (Button)findViewById(R.id.buyhouse);

        rental = (Button)findViewById(R.id.rental);
        motel = (Button)findViewById(R.id.motel);
        
        Mabout = (Button)findViewById(R.id.about);
        exit = (Button)findViewById(R.id.exit);
        initializeUI();
 }
    
    private void initializeUI() {
    	findme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(RamaliHome.this, LocateMe.class);
                startActivity(intent);

            }
        });
    	buyhouse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(RamaliHome.this, BuyHouse.class);
                startActivity(intent);

            }
        });
   	 ActionItem rentalmap	= new ActionItem(MAP_HOUSE, "Map results", getResources().getDrawable(R.drawable.map));
   	 ActionItem rentallist	= new ActionItem(LIST_HOUSE, "List results", getResources().getDrawable(R.drawable.list));
   	 final QuickAction quickAction_rental = new QuickAction(this, QuickAction.HORIZONTAL);
   	 quickAction_rental.addActionItem(rentalmap);
   	 quickAction_rental.addActionItem(rentallist);
   	 quickAction_rental.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
		@Override
		public void onItemClick(QuickAction source, int pos, int actionId) {				
			 NactionItem = quickAction_rental.getActionItem(pos);
             
			
			if (actionId == MAP_HOUSE) {
				if (Util.isConnected(RamaliHome.this)) {
					Intent intent = new Intent(RamaliHome.this, Rental.class);
	                startActivity(intent);
					}
					else
					{
						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
					}
			} else if (actionId == LIST_HOUSE) {
				if (Util.isConnected(RamaliHome.this)) {
					Intent intent = new Intent(RamaliHome.this, RentalList.class);
	                startActivity(intent);
					}
					else
					{
						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
					}
			} else {
			}
		}
	});
   	quickAction_rental.setOnDismissListener(new QuickAction.OnDismissListener() {			
		@Override
		public void onDismiss() {}});
		
	
    	rental.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				quickAction_rental.show(v);
				mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
				}
		});
    	
    	
    	 ActionItem motelmap	= new ActionItem(MAP_HOUSE, "Map results", getResources().getDrawable(R.drawable.map));
      	 ActionItem motellist	= new ActionItem(LIST_HOUSE, "List results", getResources().getDrawable(R.drawable.list));
      	 final QuickAction quickAction_motel = new QuickAction(this, QuickAction.HORIZONTAL);
      	 quickAction_motel.addActionItem(motelmap);
      	 quickAction_motel.addActionItem(motellist);
      	 quickAction_motel.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
   		@Override
   		public void onItemClick(QuickAction source, int pos, int actionId) {				
   			 MactionItem = quickAction_motel.getActionItem(pos);
                
   			
   			if (actionId == MAP_HOUSE) {
   				if (Util.isConnected(RamaliHome.this)) {
   					Intent intent = new Intent(RamaliHome.this, Motel.class);
   	                startActivity(intent);
   					}
   					else
   					{
   						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
   					}
   			} else if (actionId == LIST_HOUSE) {
   				if (Util.isConnected(RamaliHome.this)) {
   					Intent intent = new Intent(RamaliHome.this, MotelList.class);
   	                startActivity(intent);
   					}
   					else
   					{
   						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
   					}
   			} else {
   			}
   		}
   	});
      	quickAction_motel.setOnDismissListener(new QuickAction.OnDismissListener() {			
   		@Override
   		public void onDismiss() {}});
      	motel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				quickAction_motel.show(v);
				mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
				}
		});
      	 ActionItem about	= new ActionItem(ABOUT, "Info", getResources().getDrawable(R.drawable.about));
     	 ActionItem comment	= new ActionItem(COMMENT, "Comment", getResources().getDrawable(R.drawable.comment));
     	 final QuickAction quickAction_about = new QuickAction(this, QuickAction.HORIZONTAL);
     	 quickAction_about.addActionItem(about);
     	 quickAction_about.addActionItem(comment);
     	 quickAction_about.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {			
  		@Override
  		public void onItemClick(QuickAction source, int pos, int actionId) {				
  			 actionItem = quickAction_about.getActionItem(pos);
               
  			
  			if (actionId == ABOUT) {
  				if (Util.isConnected(RamaliHome.this)) {
  					Intent intent = new Intent(RamaliHome.this, About.class);
  	                startActivity(intent);
  					}
  					else
  					{
  						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
  					}
  			} else if (actionId == COMMENT) {
  				if (Util.isConnected(RamaliHome.this)) {
  					Intent intent = new Intent(RamaliHome.this, RamaliComment.class);
  	                startActivity(intent);
  					}
  					else
  					{
  						Toast.makeText(RamaliHome.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
  					}
  			} else {
  			}
  		}
  	});
     	quickAction_about.setOnDismissListener(new QuickAction.OnDismissListener() {			
  		@Override
  		public void onDismiss() {}});
    	
     	Mabout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				quickAction_about.show(v);
				mp = MediaPlayer.create(getApplicationContext(), R.raw.pling); 
                mp.start();
			}
		});
    	
    	exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	makeDialog();
            }
        });
    	
    	
    	
    	
    	
    }
    @Override
    public void onBackPressed() {

    	makeDialog();
    }
    public void onClickHome (View v)
    {
        goHome (this);
    }
    public void onClickSearch (View v)
    {
     		
    }
    private void makeDialog() {
	    AlertDialog.Builder dialog = new AlertDialog.Builder(this);	    

	    dialog.setMessage("Are you sure you want to exit?");

	    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {
	        	RamaliHome.this.finish();
	        }
	    });

	    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface arg0, int arg1) {}
	        
	    });

	    dialog.show();

	    }

    public void onClickAbout (View v)
    {
    	Toast.makeText(this, "\nDeveloped by:\nJamuhuri Tech\n", Toast.LENGTH_SHORT).show();
    }
    public void goHome(Context context) 
    {
        final Intent intent = new Intent(this, RamaliHome.class);
        startActivity (intent);;
    }

    public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
    
    
    
}
