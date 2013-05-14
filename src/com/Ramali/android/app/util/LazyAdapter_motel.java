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

import com.Ramali.android.app.MotelList;
import com.Ramali.android.app.R;



public class LazyAdapter_motel extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter_motel(Activity a, ArrayList<HashMap<String, String>> d) {
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
            vi = inflater.inflate(R.layout.motel_list_row, null);

        TextView NAME = (TextView)vi.findViewById(R.id.name); // Motel Name
        TextView PRICE = (TextView)vi.findViewById(R.id.board); // Price
        TextView REF= (TextView)vi.findViewById(R.id.ref); // Ref:
        TextView ROOMS = (TextView)vi.findViewById(R.id.units); // Available Rooms
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); //  thumb image
        
        HashMap<String, String> hash = new HashMap<String, String>();
        hash = data.get(position);
        
        // Setting all values in listview
        NAME.setText(hash.get(MotelList.KEY_NAME));
        PRICE.setText("PRICE: "+hash.get(MotelList.KEY_PRICE));
        REF.setText("REF:"+hash.get(MotelList.KEY_REF));
        ROOMS.setText("ROOMS: "+hash.get(MotelList.KEY_ROOMS));
        imageLoader.DisplayImage(hash.get(MotelList.KEY_IMAGE_URL), thumb_image);
        return vi;
    }
}