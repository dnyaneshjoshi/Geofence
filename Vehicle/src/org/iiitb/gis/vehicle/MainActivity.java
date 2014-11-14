package org.iiitb.gis.vehicle;

import java.io.IOException;

import org.iiitb.gis.vehicle.net.HttpConnection;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tvStatus;
	EditText etRegNo, etServerDetails;
	CheckBox cbReady;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvStatus=(TextView)findViewById(R.id.TextView03);
		etRegNo=(EditText)findViewById(R.id.editText1);
		etServerDetails=(EditText)findViewById(R.id.EditText01);
		cbReady=(CheckBox)findViewById(R.id.checkBox1);
		
		etRegNo.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(cbReady.isChecked())
					cbReady.setChecked(false);
			}
		});
		
		etServerDetails.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(cbReady.isChecked())
					cbReady.setChecked(false);
			}
		});
		
		try
		{
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
			
			LocationListener locationListener = new LocationListener() {
			    public void onLocationChanged(Location location) {
			    	
			    	double latitude=location.getLatitude();
			    	double longitude=location.getLongitude();
			    	
			    	String msg="Location changed : "
			    			+ " Latitude: "+latitude
                            + ", Longitude: "+longitude;
			    	Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
			    	tvStatus.setText(msg);
			    	
			    	try
			    	{
			    		if(!etRegNo.getText().toString().trim().equals("")
			    			&& !etServerDetails.getText().toString().trim().equals("")
			    			&& cbReady.isChecked())
				    		new HttpConnection().sendHttpGetRequest("http://"
				    				+etServerDetails.getText().toString().trim()
				    				+"/IbtsicServer/setBusLocationAction?regNo="
				    				+etRegNo.getText().toString().trim()
				    				+"&latitude="
				    				+Double.toString(latitude)
				    				+"&longitude="
				    				+Double.toString(longitude));
			    	}
			    	catch(IOException e)
			    	{
			    		Toast.makeText(getBaseContext(), "Location upload failure! Please check your network connection.", Toast.LENGTH_SHORT).show();
			    	}
			    }

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub
					
				}
			};
			
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
