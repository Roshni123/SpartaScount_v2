package com.example.android.spartascout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the View that shows the vr category
        Button virtual_tour = (Button) findViewById(R.id.virtual_tour);

        // Set a click listener on that View
        if (virtual_tour != null) {
            virtual_tour.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the virtual tour View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent vrIntent = new Intent(HomeActivity.this, VRActivity.class);
                    startActivity(vrIntent);
                }
            });
        }

        // Find the View that shows the voice assistant category
        /*Button voice_assistant = (Button) findViewById(R.id.voice_assistant);

        // Set a click listener on that View
        if (voice_assistant != null) {
            voice_assistant.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the voice assistant View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent voiceAssistantIntent = new Intent(HomeActivity.this, VoiceAssistantActivity.class);
                    startActivity(voiceAssistantIntent);
                }
            });
        }*/


        // Find the View that shows the paths category
        Button paths = (Button) findViewById(R.id.paths);

        // Set a click listener on that View
        if (paths != null) {
            paths.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the paths View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent paths = new Intent(HomeActivity.this, PathsActivity.class);
                    startActivity(paths);
                }
            });
        }


        // Find the View that shows the crime report category
        Button crimeReport = (Button) findViewById(R.id.crime_report);

        // Set a click listener on that View
        if (crimeReport != null) {
            crimeReport.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the crime report View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent crimeReportIntent = new Intent(HomeActivity.this, CrimeReportActivity.class);
                    startActivity(crimeReportIntent);
                }
            });
        }


        // Find the View that shows the campus news category
        Button campusNews = (Button) findViewById(R.id.campus_news);

        // Set a click listener on that View
        if (campusNews != null) {
            campusNews.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the campus news View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent campusNewsIntent = new Intent(HomeActivity.this, CampusNewsActivity.class);
                    startActivity(campusNewsIntent);
                }
            });
        }


        // Find the View that shows the two D map category
        Button twoDMap = (Button) findViewById(R.id.twoD_map);

        // Set a click listener on that View
        if (twoDMap != null) {
            twoDMap.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the campus news View is clicked on.
                @Override
                public void onClick(View view) {

                    Intent TwoDMapIntent = new Intent(HomeActivity.this, TwoDMapActivity.class);
                    startActivity(TwoDMapIntent);


                }

            });
        }

        //
    }
}

