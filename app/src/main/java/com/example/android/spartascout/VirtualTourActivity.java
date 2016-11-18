package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.youtube.player.YouTubeBaseActivity;


public class VirtualTourActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_tour);
//        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/v/Z2O-jY1YNVA?start=10&end=20&version=3&autoplay=1"));
        Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Z2O-jY1YNVA#t=8s"));
        startActivity(ytIntent);
    }
}
