package com.example.com.rallat.mapfragment;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapActivityFragment extends MapActivity {
	private final static String TAG = MapActivityFragment.class.getName();

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 // этот метод показывает Zoom na Maps
				
		setContentView(R.layout.activity_maplist);
		MapView mapView = (MapView) findViewById(R.id.map);
        LinearLayout zoomLayout = (LinearLayout)findViewById(R.id.zoom);  
        View zoomView = mapView.getZoomControls(); 
  
        zoomLayout.addView(zoomView, 
            new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT)); 
        mapView.displayZoomControls(true);

	}
	
	
	 public void onLocationChanged(Location location) {
	      
	 
	        double latitude = 0;
			double longitude = 0;
			// Creating an instance of GeoPoint corresponding to latitude and longitude
	        GeoPoint point = new GeoPoint((int)(latitude * 1E6), (int)(longitude*1E6));
	 
	        MapView mapView = null;
			// Getting MapController
	        MapController mapController = mapView.getController();
	 
	        // Locating the Geographical point in the Map
	        mapController.animateTo(point);
	 
	        // Applying a zoom
	        mapController.setZoom(10);
	 
	        // Redraw the map
	        mapView.invalidate();
	 
	        // Getting list of overlays available in the map
	        List<Overlay> mapOverlays = mapView.getOverlays();
	 
	        // Creating a drawable object to represent the image of mark in the map
	        Drawable drawable = this.getResources().getDrawable(R.drawable.cur_position);
	 
	        // Creating an instance of ItemizedOverlay to mark the current location in the map
	        CurrentLocationOverlay currentLocationOverlay = new CurrentLocationOverlay(drawable);
	 
	        // Creating an item to represent a mark in the overlay
	        OverlayItem currentLocation = new OverlayItem(point, "Current Location", "Latitude : " + latitude + ", Longitude:" + longitude);
	 
	        // Adding the mark to the overlay
	        currentLocationOverlay.addOverlay(currentLocation);
	 
	        // Clear Existing overlays in the map
	        mapOverlays.clear();
	 
	        // Adding new overlay to map overlay
	        mapOverlays.add(currentLocationOverlay);
	 
	    }
	 
	    public void onProviderDisabled(String provider) {
	        // TODO Auto-generated method stub
	    }
	 
	 
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        // TODO Auto-generated method stub
	    }
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
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

}
