package com.example.android.spartascout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;

public class TwoDMapActivity extends AppCompatActivity {
    String mode ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dmap);

        // Find the View that shows the campus news category
        Button submit = (Button) findViewById(R.id.submitLocation);



        // Set a click listener on that View
        if (submit != null ) {
            submit.setOnClickListener(new View.OnClickListener() {

                final EditText location=(EditText)findViewById(R.id.Location);


                @Override
                public void onClick(View view) {

                    // The code in this method will be executed when the campus news View is clicked on.
                    RadioGroup rMode = (RadioGroup) findViewById(R.id.radioMode);
                    // get selected radio button from radioGroup
                    int selectedId = rMode.getCheckedRadioButtonId();

                    // find the radiobutton by returned id
                    RadioButton radioSelectedMode = (RadioButton) findViewById(selectedId);

                    String mode = "";
                    if(radioSelectedMode.getText().equals("Walk")) {
                        mode = "w";
                    } else if(radioSelectedMode.getText().equals("Bicycle")) {
                        mode = "b";
                    } else if(radioSelectedMode.getText().equals("Car")) {
                        mode = "d";
                    }



                  if(location.getText().toString().length()>0){
                      String uri = String.format(Locale.ENGLISH, "google.navigation:q=%s,+San Jose+USA&mode=%s",location.getText().toString(),mode);
                      Uri gmmIntentUri = Uri.parse(uri);
                      Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                      mapIntent.setPackage("com.google.android.apps.maps");
                      startActivity(mapIntent);
                  }else{
                      Toast.makeText(TwoDMapActivity.this, "Please enter destination!", Toast.LENGTH_SHORT).show();
                  }

                }
            });
        }

    }
}
