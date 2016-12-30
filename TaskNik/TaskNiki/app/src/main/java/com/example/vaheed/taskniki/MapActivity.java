package com.example.vaheed.taskniki;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by vaheed on 26/10/16.
 */
public class MapActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

// Receiving latitude from MainActivity screen
        //double latitude = getIntent().getDoubleExtra("lat",0);
        String name=getIntent().getStringExtra("name");
        String lati=getIntent().getStringExtra("latitude");
        double latitude=Double.parseDouble(lati);

         String longi=getIntent().getStringExtra("longitude");
        double longitude=Double.parseDouble(longi);
        LatLng position = new LatLng(latitude, longitude);

        // Instantiating MarkerOptions class
        MarkerOptions options = new MarkerOptions();

        // Setting position for the MarkerOptions
        options.position(position);


        // Setting title for the MarkerOptions
        options.title(name);

        // Setting snippet for the MarkerOptions
        options.snippet("Latitude:" + latitude + ",Longitude:" + longitude);

        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting reference to google map
        GoogleMap googleMap = fm.getMap();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, -100));
        // Adding Marker on the Google Map
        googleMap.addMarker(options);

        // Creating CameraUpdate object for position
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        // Creating CameraUpdate object for zoom
        CameraUpdate updateZoom = CameraUpdateFactory.zoomBy(4);

        // Updating the camera position to the user input latitude and longitude
        googleMap.moveCamera(updatePosition);

        // Applying zoom to the marker position
        googleMap.animateCamera(updateZoom);

    }
}