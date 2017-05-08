package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.nearby.messages.Strategy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class VirtualTourActivity2 extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_tour);
        Bundle b = getIntent().getExtras();
        int seek_point = b.getInt("seek");
        String url = b.getString("url");
        String uri = String.format(url, Integer.toString(seek_point));
        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        startActivity(ytIntent);
        try {
            Thread.sleep(8000);
//            Runtime.getRuntime().exec("su -c /system/bin/input tap 1700 1000"); Nexus 5
            Runtime.getRuntime().exec("su -c /system/bin/input tap 1180 700"); // Nexus 4
            Thread.sleep(1000);
//            Runtime.getRuntime().exec("su -c /system/bin/input tap 1700 1000"); Nexus 5
            Runtime.getRuntime().exec("su -c /system/bin/input tap 1180 700"); // Nexus 4
            System.out.println("ROSHNI ACTIVITY SUCCESS.......");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
