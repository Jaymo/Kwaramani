package com.Ramali.android.app;


import impl.android.com.twitterapime.xauth.ui.WebViewOAuthDialogWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.twitterapime.rest.Credential;
import com.twitterapime.rest.TweetER;
import com.twitterapime.rest.UserAccountManager;
import com.twitterapime.search.Tweet;
import com.twitterapime.xauth.Token;
import com.twitterapime.xauth.ui.OAuthDialogListener;
public class About extends Activity implements OAuthDialogListener {
	Facebook mfacebook = new Facebook("319814018100200");
	 
	private SharedPreferences mPrefs;
	 
    String CALLBACK_URL,CONSUMER_SECRET,CONSUMER_KEY,Responce,access_token;
    
	String FILENAME = "AndroidSSO_data";
	
	private Token accessToken;
	
	private Button teamUrlBtn;

	private Button twitterUrlBtn;

    private Button facebookUrlBtn;

    private Button contactUrlBtn;
      
	private Intent i;
	
	
    private static final String TEAM_URL = "https://twitter.com/#!/akajaymo";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
        setTitleFromActivityLabel (R.id.title_text);
        
        mPrefs = getPreferences(MODE_PRIVATE);
		access_token = mPrefs.getString("access_token", null);
   
        teamUrlBtn = (Button)findViewById(R.id.team_link);
        twitterUrlBtn = (Button)findViewById(R.id.twitter_link);
        facebookUrlBtn = (Button)findViewById(R.id.facebook_link);
        contactUrlBtn = (Button)findViewById(R.id.contact_link);
        
        i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(TEAM_URL));

        teamUrlBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(i);
            }
        });

        
        twitterUrlBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               CONSUMER_KEY = "Oxs4wD3e58RdDyOa05YqXA";
    			
    			
    			CONSUMER_SECRET = "LOiI878xzI6UNmB8g1SF83ZpsmWmjmvor1TfDq9XYY";

    			
    			CALLBACK_URL = "http://www.twitter.com";
    			  
    			  
    		        WebView webView = new WebView(About.this);
    		        setContentView(webView);
    		        webView.requestFocus(View.FOCUS_DOWN);
    		        
    		        WebViewOAuthDialogWrapper pageWrapper =
    		        new WebViewOAuthDialogWrapper(webView);
    				pageWrapper.setConsumerKey(CONSUMER_KEY);
    				pageWrapper.setConsumerSecret(CONSUMER_SECRET);
    				pageWrapper.setCallbackUrl(CALLBACK_URL);
    				pageWrapper.setOAuthListener(About.this);
    				pageWrapper.login();
    				
            	
            	
            }
		
            
        });

        

        facebookUrlBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	long expires = mPrefs.getLong("access_expires", 0);
    			if( access_token !=null){
    				mfacebook.setAccessToken(access_token);
    			}
    			if(expires !=0)
    			{
    				mfacebook.setAccessExpires(expires);
    			}
    			if(!mfacebook.isSessionValid())
    			{
    			mfacebook.authorize(About.this,new String[]{"publish_stream","publish_checkins"}, new DialogListener() {
    				@Override
    				public void onFacebookError(FacebookError e) {
    					// TODO Auto-generated method stub
    					
    				}
    				
    				@Override
    				public void onError(DialogError e) {
    					// TODO Auto-generated method stub
    					
    				}
    				
    				@Override
    				public void onComplete(Bundle values) {
    					SharedPreferences.Editor editor = mPrefs.edit();
    					editor.putString("access_token",
    							mfacebook.getAccessToken());
    					editor.putLong("access_expires",
    							mfacebook.getAccessExpires());
    					editor.commit();					
    				}
    				
    				@Override
    				public void onCancel() {
    					// TODO Auto-generated method stub
    					
    				}
    			});
    			}
    			if(mfacebook.isSessionValid())
    			{
    			try {
    				Responce = mfacebook.request("me");
    				Bundle upDets = new Bundle();
    				upDets.putString("message", "Looking for a Rental House,House on Sale or a motel close by?Use KwaRAMANI for Android (2.2)....Available on Samsung store ,Getjar and Android Market");
    				upDets.putString(Facebook.TOKEN, access_token);
    				Responce = mfacebook.request("me/feed", upDets, "POST");
    				Toast.makeText(About.this, "Sucessfully shared on your Timeline", Toast.LENGTH_LONG).show();
    			} catch (Exception e) {
    				
    				Log.v("fail" ,e.toString());
    				
    				
    			} 
    			
    			}
    			
            	
            	
            }
                
            
        });

       contactUrlBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            	String aEmailList[] = { "wahomey@gmail.com"};
            	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
            	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "KwaRamani:User Request");
            	emailIntent.setType("plain/text");  
            	startActivity(Intent.createChooser(emailIntent, "Select Email Client:"));
            	
            }
        });
       
    }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mfacebook.authorizeCallback(requestCode, resultCode, data);
	}
    public void onClickHome (View v)
    {
    	
        About.this.finish();
    }


    public void onClickSearch (View v)
    {
        
    }
    public void onClickAbout (View v)
    {
    	Toast.makeText(About.this, "\nVersion 2.1\n", Toast.LENGTH_SHORT).show();
    }

    
    
    
	
	public void setTitleFromActivityLabel (int textViewId)
    {
        TextView tv = (TextView) findViewById (textViewId);
        if (tv != null) tv.setText (getTitle ());
    }
    protected void onPause()
    {
      super.onPause();
    }
	@Override
	public void onAccessDenied(String arg0) {
		showMessage("Acess Denied");
		
	}
	@Override
	public void onAuthorize(Token accessToken) {
		try {
			Credential creds = new Credential(CONSUMER_KEY, CONSUMER_SECRET, accessToken);
			UserAccountManager userman = UserAccountManager.getInstance(creds);
			
			if (userman.verifyCredential()) {
				Tweet twt =new Tweet("Looking for a Rental House,one on Sale or a Motel close by?Use KwaRAMANI( Android 2.2).Available on Samsung store, Android Market or Getjar" 
				);
				TweetER ter = TweetER.getInstance(userman);
				twt = ter.post(twt);
				Toast.makeText(About.this, "Sucessfully shared on your Timeline", Toast.LENGTH_LONG).show();
				this.finish();
			}
		} catch (Exception e) {
			String error = e.toString();
		      showMessage(error);
		}
	
	}
	@Override
	public void onFail(String arg0, String arg1) {
		showMessage("Something went wrong");
		
	}
	private void showMessage(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false)
				.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		
		builder.create().show();
	}
	
	@Override
	protected void onResume() {
		 super.onResume();
	     mfacebook.extendAccessTokenIfNeeded(About.this, null);
	}
}
