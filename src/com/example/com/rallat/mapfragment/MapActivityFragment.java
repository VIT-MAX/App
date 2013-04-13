package com.example.com.rallat.mapfragment;

import android.app.Dialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivityFragment extends FragmentActivity implements LocationListener {
	private final static String TAG = MapActivityFragment.class.getName();
	private GoogleMap googleMap;
	private Location myLocation;
	private FrameLayout mapPrieview;
	private ImageView imageView;
	private Button menuButton;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // этот метод показывает Zoom na Maps
		setContentView(R.layout.activity_maplist);
		 // Getting Google Play availability status
		
		mapPrieview = (FrameLayout) findViewById(R.id.map_preview);

		imageView = (ImageView) findViewById(R.id.imageView1);
		
		menuButton = (Button) findViewById(R.id.button1);
	
		
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
 
        // Showing status
        if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
 
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
 
        }else { // Google Play Services are available
 
            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
 
            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();
 
            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);
 
            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
 
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
 
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
 
            // Getting Current Location
            myLocation = locationManager.getLastKnownLocation(provider);
 
            if(myLocation!=null){
            	//moveCamera(myLocation);
            }            
           

    		menuButton.setVisibility(8);
    		googleMap.getUiSettings().setZoomControlsEnabled(false);
    		googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    		
    		
    		mapPrieview.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				mapPrieview.setVisibility(4);
    				imageView.setVisibility(4);
    				googleMap.getUiSettings().setZoomControlsEnabled(true);
    				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
    				menuButton.setVisibility(0);
    				moveCamera(myLocation);
    			}
    		});

    		menuButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
    				moveCamera(myLocation);
    				mapPrieview.setVisibility(0);
    				imageView.setVisibility(0);
    				googleMap.getUiSettings().setZoomControlsEnabled(false);
    				googleMap.getUiSettings().setMyLocationButtonEnabled(false);
    				menuButton.setVisibility(4);
    				//googleMap.animateCamera(CameraUpdateFactory.scrollBy(0, 120));
				}
			});
            //locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }
	}
	
	@Override
	 public void onLocationChanged(Location location) {
	      myLocation = location;	 
	        // Zoom in the Google Map

	        
	        if (menuButton.getVisibility() != 4){
	    		// Getting latitude of the current location
		        double latitude = location.getLatitude();
		 
		        // Getting longitude of the current location
		        double longitude = location.getLongitude();
		 
		        // Creating a LatLng object for the current location
		        LatLng latLng = new LatLng(latitude, longitude);
		 
		        // Showing the current location in Google Map
		        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
		        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	        	googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, 120));
	        }
	 
	    } 
	
	
	 public void moveCamera(Location location) {
	      
		// Getting latitude of the current location
	        double latitude = location.getLatitude();
	 
	        // Getting longitude of the current location
	        double longitude = location.getLongitude();
	 
	        // Creating a LatLng object for the current location
	        LatLng latLng = new LatLng(latitude, longitude);
	        
	        // Showing the current location in Google Map
	        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
	 
	        // Zoom in the Google Map
	        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
	        
	        if (menuButton.getVisibility() != 4){
	        	googleMap.animateCamera(CameraUpdateFactory.scrollBy(0, 120));
	        	//googleMap.moveCamera(CameraUpdateFactory.scrollBy(0, 120));
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
