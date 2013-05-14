package com.Ramali.android.app.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Ramali.android.app.R;
import com.Ramali.android.app.RamaliListHouse;



public class LazyAdapter_home_buyer extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter_home_buyer(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);//nilifix hapa ju am using a custom
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.buyer_list_row, null);

        TextView HOOD = (TextView)vi.findViewById(R.id.hood); // Neighbourhood
        TextView PRICE = (TextView)vi.findViewById(R.id.price); // Price
        TextView REF= (TextView)vi.findViewById(R.id.ref); // Ref:
        TextView BUYER = (TextView)vi.findViewById(R.id.buyers); // Available Places
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); //  thumb image
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        // Setting all values in listview
        HOOD.setText(hash.get(RamaliListHouse.KEY_NAME));
        PRICE.setText("PRICE: "+hash.get(RamaliListHouse.KEY_PRICE));
        REF.setText("REF:"+hash.get(RamaliListHouse.KEY_REF));
        BUYER.setText("AVAILABLE UNITS: "+hash.get(RamaliListHouse.KEY_UNITS));
        imageLoader.DisplayImage(hash.get(RamaliListHouse.KEY_IMAGE_URL), thumb_image);
        return vi;
    }
}
