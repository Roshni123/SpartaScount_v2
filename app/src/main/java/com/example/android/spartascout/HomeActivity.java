package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String s = getIntent().getStringExtra("NAME");

        TextView textView = (TextView) findViewById(R.id.sign_in_success);
        if(s != null)
         textView.setText("Welcome "+ s);
        else
         textView.setText("Welcome ");

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


        // Find the View that shows the pub nub category
        Button pubNub = (Button) findViewById(R.id.publish_location);

        // Set a click listener on that View
        if (pubNub != null) {
            pubNub.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the publish location View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent pubNubIntent = new Intent(HomeActivity.this, PubNubActivity.class);
                    startActivity(pubNubIntent);
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

                    Calendar cal = Calendar.getInstance();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    cal.add(Calendar.DATE, -1);

                    String today = dateFormat.format(cal.getTime());
                    cal.add(Calendar.DATE, -1);
                    String yesterday = dateFormat.format(cal.getTime());


                    Intent crimeReportIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.crimereports.com/home/#!/dashboard?lat=37.33502&lng=-121.881783&zoom=13&searchText=San%2520Jose%2520State%2520University%2520Bookstore-Spartan%2520Shops%252C%25201%2520Washington%2520Sq%252C%2520San%2520Jose%252C%2520California%252095112%252C%2520United%2520States&incident_types=Assault%252CAssault%2520with%2520Deadly%2520Weapon%252CBreaking%2520%2526%2520Entering%252CDisorder%252CDrugs%252CHomicide%252CKidnapping%252CLiquor%252COther%2520Sexual%2520Offense%252CProperty%2520Crime%252CProperty%2520Crime%2520Commercial%252CProperty%2520Crime%2520Residential%252CQuality%2520of%2520Life%252CRobbery%252CSexual%2520Assault%252CSexual%2520Offense%252CTheft%252CTheft%2520from%2520Vehicle%252CTheft%2520of%2520Vehicle&start_date="+today+"&end_date="+today+"&days=sunday%252Cmonday%252Ctuesday%252Cwednesday%252Cthursday%252Cfriday%252Csaturday&start_time=0&end_time=23&include_sex_offenders=false&current_tab=list&shapeIds=&position_id=gxr9-3ifr-row-rfxq-b4jc__wx6q&shape_id=false"));
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

        // Find the View that shows the vr category
        Button sign_out = (Button) findViewById(R.id.sign_out);

        // Set a click listener on that View
        if (sign_out != null) {
            sign_out.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the virtual tour View is clicked on.
                @Override
                public void onClick(View view) {


                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    // ...
                                    Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(i);
                                }
                            });
                }
            });
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }

}

