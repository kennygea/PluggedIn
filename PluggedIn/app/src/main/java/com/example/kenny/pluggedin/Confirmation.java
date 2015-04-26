package com.example.kenny.pluggedin;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.PolylineOptions;


public class Confirmation extends ActionBarActivity {
    String playlist_name;
    Long playlist_id;
    String destination;
    String path;
    public final static String PLAYLIST_ID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            playlist_name = (String) bundle.get("PlaylistName");
            playlist_id = (Long) bundle.get("PlaylistID");
            destination = (String) bundle.get("Destination");
            path = (String) bundle.get("PATH");
        }
        System.out.println(path);
        System.out.println(destination);
        System.out.println(playlist_name);
        System.out.println(playlist_id);
        TextView playlist = (TextView) findViewById(R.id.playlist);
        playlist.setText(playlist_name);
        TextView dest = (TextView) findViewById(R.id.destination);
        dest.setText(destination);
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchIntent = new Intent(Confirmation.this, DestinationMap.class);
                switchIntent.putExtra("PATH", path);
                switchIntent.putExtra("Name", playlist_name);
                switchIntent.putExtra("ID", playlist_id);
                Confirmation.this.startActivity(switchIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);
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
