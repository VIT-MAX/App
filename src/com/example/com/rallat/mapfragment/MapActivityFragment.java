package com.example.com.rallat.mapfragment;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapActivityFragment extends FragmentActivity implements LocationListener {
	private final static String TAG = MapActivityFragment.class.getName();
	private GoogleMap googleMap;
	private Location myLocation;
	private FrameLayout mapPrieview;
	private Button menuButton;
	private LocationManager locationManager;
	private RelativeLayout mainLayout;
	private FrameLayout frame;
	private SupportMapFragment fm;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void printDebugLog(String message) {
		Log.d("debug", message);
	}
	
	 private Location getLastBestLocation() {
		 	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    Location locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		    
		    long GPSLocationTime = 0;
		    if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

		    long NetLocationTime = 0;

		    if (null != locationNet) {
		        NetLocationTime = locationNet.getTime();
		    }

		    if ( 0 < GPSLocationTime - NetLocationTime ) {
		        return locationGPS;
		    }
		    else{
		        return locationNet;
		    }

		}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // этот метод показывает Zoom na Maps
		setContentView(R.layout.activity_maplist);
		 // Getting Google Play availability status
		
		mapPrieview = (FrameLayout) findViewById(R.id.map_preview);

		frame = (FrameLayout) findViewById(R.id.list);
		
		menuButton = (Button) findViewById(R.id.button1);
		
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
	
		
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
 
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_main.xml
            fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
          
            //Getting Current Location
            myLocation = getLastBestLocation();        
            
    		menuButton.setVisibility(4);
    		googleMap.getUiSettings().setZoomControlsEnabled(false);
    		googleMap.getUiSettings().setMyLocationButtonEnabled(false);            
            
            if(myLocation!=null){
    			LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());            	
            	googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)); 
		        int moveValue = mainLayout.getHeight()/2 - mapPrieview.getHeight()/2;		        
				googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, moveValue));            
				printDebugLog("start sdvig");
            }                       
    		    		
    		mapPrieview.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				mapPrieview.setVisibility(4);
    				frame.setVisibility(4);
    				googleMap.getUiSettings().setZoomControlsEnabled(true);
    				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    				menuButton.setVisibility(0);    		    				
    				animateMap(myLocation);
    			}
    		});

    		menuButton.setOnClickListener(new View.OnClickListener() {
				boolean mapMoved = true;
				@Override
				public void onClick(View v) {					

    				googleMap.getUiSettings().setZoomControlsEnabled(false);
    				googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    				menuButton.setVisibility(4);
    				LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
    				fm
    				googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, 15)), 200,  new CancelableCallback() {

						@Override
						public void onCancel() {
							
						}

						@Override
						public void onFinish() {
							mapMoved = true;
		        	        if (menuButton.getVisibility() == 4){
		        	        	//raschitivaju na skoljko nado sdvinutj kartu
		        		        int moveValue = mainLayout.getHeight()/2 - mapPrieview.getHeight()/2;        		               		      
		        		        
		        				googleMap.animateCamera(CameraUpdateFactory.scrollBy(0, moveValue), 200, new CancelableCallback() {

		        	                @Override
		        	                public void onFinish() {    	        	    				
		        	    				mapPrieview.setVisibility(0);
		        	    				frame.setVisibility(0);
		        	                }
		        	                @Override
		        	                public void onCancel() {                
		        	                }
		        	            });
		        	        }
    	    				mapPrieview.setVisibility(0);
    	    				frame.setVisibility(0);
						}
    					
    				});	    		
				}
			});
        }
	}
	
	 public void animateMap(Location location) {	        
			LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
			//googleMap.getUiSettings().setScrollGesturesEnabled(false);			
			googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng), 200,  new CancelableCallback() {

                @Override
                public void onFinish() {
        	        if (menuButton.getVisibility() == 4){
        	        	//raschitivaju na skoljko nado sdvinutj kartu
        		        int moveValue = mainLayout.getHeight()/2 - mapPrieview.getHeight()/2;
        				googleMap.animateCamera(CameraUpdateFactory.scrollBy(0, moveValue), 200, new CancelableCallback() {

        	                @Override
        	                public void onFinish() {

        	                }
        	                @Override
        	                public void onCancel() {                
        	                }
        	            });
        	        }
                }

                @Override
                public void onCancel() {                
                }
            });
			        
	 }
	
	
	@Override
	 public void onLocationChanged(Location location) {
	      myLocation = location;	
	      
	      printDebugLog("onLocationChanged location: "+location);
	        // Zoom in the Google Map

	        
	        if (menuButton.getVisibility() == 4){
	    		// Getting latitude of the current location
		        double latitude = location.getLatitude();
		 
		        // Getting longitude of the current location
		        double longitude = location.getLongitude();
		 
		        // Creating a LatLng object for the current location
		        LatLng latLng = new LatLng(latitude, longitude);
		 
		        // Showing the current location in Google Map
		        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
		        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		        
		        int moveValue = mainLayout.getHeight()/2 - mapPrieview.getHeight()/2;
		        printDebugLog("move: "+moveValue);
		       
	        	googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, moveValue));
	        }
	 
	    } 
	 
	    public void onProviderDisabled(String provider) {
	        // TODO Auto-generated method stub
	    }
	 
	 
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        // TODO Auto-generated method stub
	    }
	


	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	public void onStart() {
		super.onStart();
		
		
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public void onStop() {
		super.onStop();
	}

	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {

		default:

			break;
		}
		return true;
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
