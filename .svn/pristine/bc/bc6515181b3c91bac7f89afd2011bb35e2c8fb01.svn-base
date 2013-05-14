package com.Ramali.android.app;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Ramali.android.app.util.Util;
import com.google.android.maps.MapView;


public class AddMotel extends UserLocationMap {
	private String mFilename = "";
	
	private boolean refreshState = false;
	private String mErrorMessage = "";
	private boolean mError = false;
	private static final int DIALOG_CHOOSE_IMAGE_METHOD = 0;
	private static final int REQUEST_CODE_IMAGE = 1;
	private static final int REQUEST_CODE_CAMERA = 2;
    private Calendar Calendar;
    private Button BtnSend;
    private ImageButton Picture;
    @SuppressWarnings("unused")
	private TextView CurrentDate,NameTitle,PriceTitle,LocationTitle,latlon,upload;
    private EditText Name,Price,Location;
    private ImageView SelectedPhoto;
    String name="",price="",location="";
    String latitude,longitude;
    HttpPost httpPost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	InputStream inputstream;
	ArrayList<NameValuePair> namevaluepairs;
	private static Random random = new Random();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addmotel);
   	    setTitleFromActivityLabel(R.id.title_text);
        initComponents();
    }
	
	@Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocating();
    }

	private void initComponents() {
		BtnSend = (Button)findViewById(R.id.send);
		upload =  (TextView)findViewById(R.id.upload_pic);
		CurrentDate = (TextView)findViewById(R.id.lbl_date);
		NameTitle = (TextView)findViewById(R.id.lbl_title);
		Name = (EditText)findViewById(R.id.motel_title);
		
		PriceTitle = (TextView)findViewById(R.id.price_title);
		Price = (EditText)findViewById(R.id.price);
		
		LocationTitle = (TextView)findViewById(R.id.loc);
		Location = (EditText)findViewById(R.id.motel_location);
		
		mapView = (MapView)findViewById(R.id.mapview);
		mapController = mapView.getController();
		latlon = (TextView)findViewById(R.id.latlon);
		
		Picture = (ImageButton)findViewById(R.id.btnPicture);
		SelectedPhoto = (ImageView)findViewById(R.id.sel_photo_prev);
		
		Name.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(Name.getText())) {
                        Name.setError(getString(R.string.empty_motel_name));
                    }
                }

            }

        });
		Price.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(Price.getText())) {
                        Price.setError(getString(R.string.empty_motel_price));
                    }
                }

            }

        });
		Location.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (TextUtils.isEmpty(Location.getText())) {
                        Location.setError(getString(R.string.empty_motel_location));
                    }
                }

            }

        });
		BtnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
                mError = false;
                if (TextUtils.isEmpty(Name.getText())) {
                    mErrorMessage = getString(R.string.empty_motel_name)+"\n";
                    mError = true;
                }
                if (TextUtils.isEmpty(Price.getText())) {
                    mErrorMessage += getString(R.string.empty_motel_price)+"\n";
                    mError = true;
                }
                if (TextUtils.isEmpty(Location.getText())) {
                    mErrorMessage += getString(R.string.empty_motel_location)+"\n";
                    mError = true;
                }
                if (!mError) {
                	
                	name =Name.getText().toString();
                	price =Price.getText().toString();
                	location =Location.getText().toString();
                	latitude=String.valueOf(sLatitude);
                	longitude=String.valueOf(sLongitude);                	                 
                    new PostResults().execute();
                    clearFields();
        		    
                }
                else {
                    final Toast t = Toast.makeText(AddMotel.this, "Error!\n" + mErrorMessage,
                            Toast.LENGTH_LONG);
                    t.show();
                    mErrorMessage = "";
                    updateDisplay();
                }
            }
		
	});
		Picture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (!TextUtils.isEmpty(ImageManager.fileName)) {
                    ImageManager.deleteImage(ImageManager.fileName, "");
                }
            	
            	showDialog(DIALOG_CHOOSE_IMAGE_METHOD); 
            	
            	

            }
		
	});
		
        
		Calendar = java.util.Calendar.getInstance();
        updateDisplay();
 	   }

	private void updateDisplay() {
        SimpleDateFormat dispFormat = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a");
        Date datetime = Calendar.getTime();
        CurrentDate.setText(dispFormat.format(datetime));
        
    }
    private void clearFields() {
           
        Name.setText("");
        Price.setText("");
        Location.setText("");
        SelectedPhoto.setImageDrawable(null);
        SelectedPhoto.setImageBitmap(null);
        updateDisplay();
    }
    
    private void updateRefreshStatus() {
        findViewById(R.id.send)
                .setVisibility(refreshState ? View.GONE : View.VISIBLE);
        findViewById(R.id.send_progress).setVisibility(
                refreshState ? View.VISIBLE : View.GONE);
    }
    public void postToOnline() {
    	try{
			httpclient =new DefaultHttpClient();
			httpPost = new HttpPost("http://akajaymo.kodingen.com/Insertmotel.php");
			namevaluepairs = new ArrayList<NameValuePair>(5);
			namevaluepairs.add (new BasicNameValuePair("name",name));
			namevaluepairs.add (new BasicNameValuePair("price",price));
			namevaluepairs.add (new BasicNameValuePair("location",location));
			namevaluepairs.add (new BasicNameValuePair("lat",latitude));
			namevaluepairs.add (new BasicNameValuePair("lon",longitude));
			httpPost.setEntity (new UrlEncodedFormEntity(namevaluepairs));
			response = httpclient.execute(httpPost);
			inputstream=response.getEntity().getContent();
			inputstream.close();
			
		 }
			catch (Exception e)
			{
				Log.e("log_tag", "Error converting result "+e.toString());
			}
    	
    }
	

	@Override
	protected void updateInterface() {

	}
	public class PostResults 
	extends AsyncTask<Void, Void, Integer> {
		 protected Integer stat;
		
		@Override
		  protected void onPreExecute() {
			  refreshState = true;
              updateRefreshStatus();
			  
		}

		@Override
		protected Integer doInBackground(Void... params) {
			if (Util.isConnected(AddMotel.this)) {
				postToOnline();
				stat = 1;//send to online DB
			}
			else
			{
				stat =2;
			}
	   return stat;
	  }
		@Override
		  protected void onPostExecute(Integer result) {
			if (result == 1) {
		   refreshState = false;
           updateRefreshStatus();
           clearFields();
		   Toast.makeText(AddMotel.this, name+" sucessfully submited", Toast.LENGTH_LONG).show();
		   AddMotel.this.finish();
		  }
		 
		else if (result == 2) {
			   refreshState = false;
	           updateRefreshStatus();
			   Toast.makeText(AddMotel.this, " No internet Connection Detected", Toast.LENGTH_LONG).show();
		
		}
		
	}
	}
	public void onClickHome (View v)
	{
	    this.finish();
	}
	
	/**
     * Create various dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_CHOOSE_IMAGE_METHOD: {
            AlertDialog dialog = (new AlertDialog.Builder(this)).create();
            dialog.setTitle(getString(R.string.choose_method));
            dialog.setIcon(R.drawable.camera);
            dialog.setButton(getString(R.string.gallery_option), new Dialog.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE_IMAGE);
                    upload.setText("Image Selected");
                    dialog.dismiss();
                }
            });
            

            
                

            dialog.setCancelable(false);
            return dialog;

        }
      }
		return null;
        
  }
    public static String randomString() {
        return Long.toString(Math.abs(random.nextLong()), 10);
    }
    
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode) {
        case DIALOG_CHOOSE_IMAGE_METHOD:
        	
        	break;
        
        case REQUEST_CODE_CAMERA:
            if (resultCode != RESULT_OK) {
                return;
            }

            // Do something with image
            Bitmap original = new CaptureImage().getBitmap(
                    new CaptureImage().getPhotoUri("photo.jpg", AddMotel.this),
                    AddMotel.this);
            if (original != null) {

                Bitmap scaled = new CaptureImage().scaleBitmap(original);
                // get image URL
                Uri u = new CaptureImage().getPhotoUri("photo.jpg", AddMotel.this);

                ImageManager.fileName = u.getPath();
               // NetworkServices.fileName = u.getPath();

                // use resized images
                if (scaled != null) {
                    SelectedPhoto.setImageBitmap(scaled);
                    SelectedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
            break;

        case REQUEST_CODE_IMAGE:

            if (resultCode != RESULT_OK) {
                return;
            }

            Uri uri = data.getData();
            Bitmap b = null;

            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (FileNotFoundException e) {
                break;
            } catch (IOException e) {
                break;
            }

            ByteArrayOutputStream byteArrayos = new ByteArrayOutputStream();

            try {
                b.compress(CompressFormat.JPEG, 75, byteArrayos);
                byteArrayos.flush();

            } catch (OutOfMemoryError e) {
                break;
            } catch (IOException e) {
                break;
            }

            mFilename = "motel_pic" +randomString() + ".jpg";
            ImageManager
                    .writeImage(byteArrayos.toByteArray(), mFilename, ImageManager.savePath);
            ImageManager.fileName = mFilename;

            SelectedPhoto.setImageBitmap(ImageManager.getBitmap(ImageManager.fileName,
            		ImageManager.savePath));
            SelectedPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            break;

        
        }

}
	 public void setTitleFromActivityLabel (int textViewId)
	    {
	        TextView tv = (TextView) findViewById (textViewId);
	        if (tv != null) tv.setText (getTitle ());
	    }

	
}
