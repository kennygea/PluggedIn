package com.example.kenny.pluggedin;

import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.View;
import android.widget.ListView;


public class PlaylistActivity extends ActionBarActivity {

    private ArrayList<Playlist> playlistList;
    private ListView playlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        playlistView = (ListView) findViewById(R.id.play_list);
        getPlaylistList();
        Collections.sort(playlistList, new Comparator<Playlist>() {
            public int compare(Playlist a, Playlist b) {
               return a.getTitle().compareTo(b.getTitle());
            }
        });
        PlaylistAdapter playlistAdt = new PlaylistAdapter(this, playlistList);
        playlistView.setAdapter(playlistAdt);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playlist, menu);
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

    public void getPlaylistList() {
        playlistList = new ArrayList<Playlist>();
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        String [] columns = {MediaStore.Audio.Playlists._ID, MediaStore.Audio.Playlists.NAME};
        Cursor musicCursor = musicResolver.query(musicUri, columns, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Playlists.NAME);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                playlistList.add(new Playlist(thisId, thisTitle));
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();
    }

    public void songPicked(View v) {
    }
}
