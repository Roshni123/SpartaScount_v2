package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;


public class VRInfoActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_info);

        Button start = (Button) findViewById(R.id.start);

        if (start != null) {
            start.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the campus news View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent virtualTourIntent = new Intent(VRInfoActivity.this, VirtualTourActivity.class);
                    startActivity(virtualTourIntent);
                }
            });
        }
    }
}
