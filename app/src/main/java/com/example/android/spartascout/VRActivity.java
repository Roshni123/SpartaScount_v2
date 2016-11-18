package com.example.android.spartascout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class VRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);


        // Find the View that shows the two D map category
        Button submitLocation = (Button) findViewById(R.id.submitLocation);
        AutoCompleteTextView source = (AutoCompleteTextView) findViewById(R.id.source);
        AutoCompleteTextView destination = (AutoCompleteTextView) findViewById(R.id.destination);
        String[] landmarks = getResources().getStringArray(R.array.landmarks);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,landmarks);
        source.setAdapter(adapter);
        destination.setAdapter(adapter);


        // Set a click listener on that View
        if (submitLocation != null) {
            submitLocation.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the campus news View is clicked on.
                @Override
                public void onClick(View view) {
                    Intent virtualTourIntent = new Intent(VRActivity.this, VRInfoActivity.class);
                    startActivity(virtualTourIntent);
                }
            });
        }

        //
    }
}
