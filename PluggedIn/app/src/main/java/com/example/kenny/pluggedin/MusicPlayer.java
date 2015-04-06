package com.example.kenny.pluggedin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MusicPlayer extends Fragment {

    private long playlistID;
    private Button playButt, stopButt, pauseButt, resumeButt;
    private ProgressBar progressBar;
    private TextView minutesDoneTV;
    private static final String LOGGING_TAG = "PluggedIn";
    private MediaPlayer player;


    public MusicPlayer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playlistID = getArguments().getLong("ID");
        playTrackFromPlaylist(playlistID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, null);
        pauseButt = (Button)view.findViewById(R.id.pauseButt);
        resumeButt = (Button)view.findViewById(R.id.resumeButt);
        pauseButt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (player.isPlaying()) {
                    player.pause();
                }
            }
        });
        resumeButt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!player.isPlaying()) {
                    int length = player.getCurrentPosition();
                    player.seekTo(length);
                    player.start();
                }
            }
        });

        return view;
    }
    public void playAudio(final String path) {
        player = new MediaPlayer();
        if (path == null) {
            Log.e(LOGGING_TAG, "Called playAudio with null data stream.");
            return;
        }
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (Exception e) {
            Log.e(LOGGING_TAG, "Failed to start MediaPlayer: " + e.getMessage());
            return;
        }
    }
    public void playTrackFromPlaylist(final long playListID) {
        final ContentResolver resolver = this.getActivity().getContentResolver();
        final Uri uri = MediaStore.Audio.Playlists.Members.getContentUri("external", playListID);
        final String dataKey = MediaStore.Audio.Media.DATA;
        Cursor tracks = resolver.query(uri, new String[] { dataKey }, null, null, null);
        if (tracks != null) {
            tracks.moveToFirst();
            final int dataIndex = tracks.getColumnIndex(dataKey);
            final String dataPath = tracks.getString(dataIndex);
            this.playAudio(dataPath);
            tracks.close();
        }
    }
}
