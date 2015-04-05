package com.example.kenny.pluggedin;

/**
 * Created by Kenny on 4/5/2015.
 */
public class Playlist {
    private long id;
    private String title;

    public Playlist(long playlistID, String playlistTitle) {
        id = playlistID;
        title = playlistTitle;
    }

    public long getId() {return id;}
    public String getTitle() {return title;}
}
