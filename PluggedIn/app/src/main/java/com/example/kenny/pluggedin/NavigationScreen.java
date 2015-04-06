package com.example.kenny.pluggedin;

import android.location.Location;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

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

import java.sql.Connection;

public class NavigationScreen extends FragmentActivity
        implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient ApiClient;
    private Location mLastLocation;
    private long playlistID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigaton_screen);
        //setUpMapIfNeeded();
       Intent intent = getIntent();
       Bundle bundle = intent.getExtras();
       if (bundle != null) {
           playlistID = bundle.getLong(PlaylistActivity.EXTRA_ID);
       }

        MapFragment navigationFrag = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.DisplayMap);
        navigationFrag.getMapAsync(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.DisplayMap))
//                    .getMap();
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                setUpMap();
//            }
//        }
//    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onMapReady(GoogleMap map){
       mMap = map;
       mMap.setMyLocationEnabled(true);
        //Get Location
        buildGoogleApiClient();
        //mMap.addMarker(new MarkerOptions()
        //        .position(new LatLng(mitLat, mitLong))
        //        .title("MIT"));
        Double mitLat;
        Double mitLong;
        Float zoomLens;
        LatLng mit;
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

    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                this.ApiClient);

        if(mMap != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 13.0f));
        }

    }

    @Override
    public void onConnectionSuspended(int cause) {
        System.out.println("Sorry brah");
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        System.out.println(result.toString());
    }
}
