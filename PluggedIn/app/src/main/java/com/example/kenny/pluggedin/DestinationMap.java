package com.example.kenny.pluggedin;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class DestinationMap extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    //path from Confirmation.java
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient ApiClient;
    private Location mLastLocation;
    private long playlistID;
    private PolylineOptions path = new PolylineOptions();
    private String full_polyline;
    private String playlistName;
    private LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_map);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
         //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER
        //setUpMapIfNeeded();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            System.out.println("BUNDLE FOUND!");
            playlistID = bundle.getLong("ID");
            playlistName = bundle.getString("Name");
            full_polyline = bundle.getString("PATH");
        }
        decodePolylines(full_polyline);
        path.color(Color.RED);
        System.out.println(path.getPoints());
        MapFragment navigationFrag = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.DisplayDestinationMap);
        navigationFrag.getMapAsync(this);
    }


    @Override
    public void onBackPressed() {

    }
    @Override
    public void onMapReady(GoogleMap map){
        mMap = map;
        mMap.setMyLocationEnabled(true);
        mMap.addPolyline(path);
        LatLng mit;
        Double mitLat;
        Double mitLong;
        Float zoomLens;
        try{
            mitLat = mLastLocation.getLatitude();
            mitLong = mLastLocation.getLongitude();
            zoomLens = 8.0f;
            mit = new LatLng(mitLat, mitLong);
        } catch(NullPointerException npe){
            mitLat = 42.355388;
            mitLong = -71.099714;
            zoomLens = 15.0f;
            mit = new LatLng(mitLat, mitLong);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mit, zoomLens));
    }
    protected synchronized void buildGoogleApiClient() {
        this.ApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void decodePolylines(final String encodedPoints) {
        int index = 0;
        int lat = 0, lng = 0;
        System.out.println("Doing the thing");
        while (index < encodedPoints.length()) {
            int b, shift = 0, result = 0;
            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift; shift += 5;
            }

            while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;

            do {
                b = encodedPoints.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            }

            while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
            System.out.println("Added to path");
            this.path.add(new LatLng((double)lat/1E5, (double)lng/1E5));

            //This algorithm for polyline decoding from
            //http://mrbool.com/google-directions-api-tracing-routes-in-android/32001#ixzz3WaI2DNMD
        }
        System.out.println("Finished doing the thing");
    }
    @Override
    public void onConnectionSuspended(int cause) {
        System.out.println("Sorry brah");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        System.out.println(result.toString());
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                this.ApiClient);

        if(mMap != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 13.0f));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destination_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


