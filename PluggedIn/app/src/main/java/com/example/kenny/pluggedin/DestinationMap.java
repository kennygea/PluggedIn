package com.example.kenny.pluggedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PolylineOptions;


public class DestinationMap extends ActionBarActivity
        implements OnMapReadyCallback{

    //path from Confirmation.java
    PolylineOptions path = new PolylineOptions();

    //Google Map (adding path in this class)
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_map);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        path = (PolylineOptions) bundle.get("PATH");

        MapFragment navigationFrag = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.DisplayDestinationMap);
        navigationFrag.getMapAsync(this);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.parent_lay) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MusicPlayer firstFragment = new MusicPlayer();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.parent_lay, firstFragment).commit();
        }

    }


    @Override
    public void onMapReady(GoogleMap map){
        mMap = map;
        mMap.setMyLocationEnabled(true);

        mMap.addPolyline(path);

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_destination_map, container, false);
            return rootView;
        }
    }
}
