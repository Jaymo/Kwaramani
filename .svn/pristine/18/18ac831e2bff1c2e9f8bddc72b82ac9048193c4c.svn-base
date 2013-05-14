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
import com.Ramali.android.app.RentalList;



public class LazyAdapter_rental extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter_rental(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);//nilifix hapa
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.rental_list_row, null);

        TextView NAME = (TextView)vi.findViewById(R.id.name); // Rental Name
        TextView RENT = (TextView)vi.findViewById(R.id.rent); // Rent
        TextView REF= (TextView)vi.findViewById(R.id.ref); // Ref:
        TextView UNITS = (TextView)vi.findViewById(R.id.units); // Available Units
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); //  thumb image
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        // Setting all values in listview
        NAME.setText(hash.get(RentalList.KEY_NAME));
        RENT.setText("RENT: "+hash.get(RentalList.KEY_RENT));
        REF.setText("REF:"+hash.get(RentalList.KEY_REF));
        UNITS.setText("AVAILABLE UNITS: "+hash.get(RentalList.KEY_UNITS));
        imageLoader.DisplayImage(hash.get(RentalList.KEY_IMAGE_URL), thumb_image);
        return vi;
    }
}