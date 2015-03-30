package com.example.kenny.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends ActionBarActivity{


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView v = (TextView)this.findViewById(R.id.text);
        v.setText("Starting...");
        RadioGroup grouping = (RadioGroup) this.findViewById(R.id.radioGroup);
        int idx = grouping.getCheckedRadioButtonId();
        LocationListener locationListener = new LocationListener(){
            public void onLocationChanged(Location location){
                    // Called when a new location is found by the network location provider.
                    makeUseOfNewLocation(location);
                    }

            public void onStatusChanged(String provider,int status,Bundle extras){
                    }

            public void onProviderEnabled(String provider){
                    }

            public void onProviderDisabled(String provider){
                    }
        };

        try {
            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 400, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 400, locationListener);

        } catch (SecurityException e) {
            // requires ACCESS_FINE_LOCATION permission
            v.setText(e.getMessage());
        }
    }


    private void makeUseOfNewLocation(Location loc) {
        TextView v = (TextView)this.findViewById(R.id.text);
        String newText = v.getText().toString() + "\n" + "lat: " + loc.getLatitude() + ", long: " + loc.getLongitude() + ", error: " + loc.getAccuracy();
        v.setText(newText);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);

        //Write to file
        boolean writable = isExternalStorageWritable();
        if(writable){
            Context ourContext = getApplicationContext();
            File logs = new File(ourContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "locationLogs.txt");
            if(!logs.mkdirs()){
                Log.e("Writable", "Directory unavailable");
                return;
            }
            String sep = System.lineSeparator();
            try{
                FileWriter logsWriter = new FileWriter(logs);
                logsWriter.write("Latitude: " + loc.getLatitude() + sep);
                logsWriter.write("Longitude: " + loc.getLongitude() + sep);
                logsWriter.write("Error: " + loc.getAccuracy() + sep);
                logsWriter.close();
            }
            catch(IOException error) {
                Log.e("FileWriting", error.getMessage());
            }
        }

    }
    //Check if the external storage is ready for writing
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}