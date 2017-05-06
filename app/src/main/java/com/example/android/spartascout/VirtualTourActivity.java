package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeStandalonePlayer;



public class VirtualTourActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_tour);
//        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/v/Z2O-jY1YNVA?start=10&end=20&version=3&autoplay=1"));
        Bundle b = getIntent().getExtras();
        int seek_point = b.getInt("seek");
        String url = b.getString("url");
        String uri = String.format(url, Integer.toString(seek_point));
        System.out.println(String.format("Roshni.....%s", uri));
//      To Specify start and end times - https://www.youtube.com/embed/Z2O-jY1YNVA?start=10&end=20
        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(ytIntent);
        try {
            Thread.sleep(8000);
            Runtime.getRuntime().exec("su -c /system/bin/input tap 1700 1000");
            Thread.sleep(1000);
            Runtime.getRuntime().exec("su -c /system/bin/input tap 1700 1000");
            System.out.println("ROSHNI ACTIVITY SUCCESS.......");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
