package com.example.android.spartascout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.Arrays;

public class VRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr);


        // Find the View that shows the two D map category
        Button submitLocation = (Button) findViewById(R.id.submitLocation);
        final AutoCompleteTextView source = (AutoCompleteTextView) findViewById(R.id.source);
        final AutoCompleteTextView destination = (AutoCompleteTextView) findViewById(R.id.destination);
        final String[] landmarks = getResources().getStringArray(R.array.landmarks);
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
                    Intent virtualTourIntent = new Intent(VRActivity.this, VRInfoPreActivity.class);
                    Bundle b = new Bundle();
                    SQLiteDatabase appdb = openOrCreateDatabase("spartascout.db",MODE_PRIVATE,null);
                    if(Arrays.toString(landmarks).contains(source.getText()) && Arrays.toString(landmarks).contains(destination.getText())) {
                        String qry = String.format("Select * from Routes where Source='%s' and Destination='%s'", source.getText(), destination.getText());
                        System.out.println(qry);
                        Cursor resultSet = appdb.rawQuery(qry, null);
                        System.out.println(resultSet.getCount());
                        System.out.println("ROSHNI.....................");
                        if (resultSet.getCount() == 2) {
                            resultSet.moveToFirst();
                            String via1 = resultSet.getString(2);
                            String duration1 = resultSet.getString(5);
                            String url1 = resultSet.getString(6);
                            Integer start1 = resultSet.getInt(3);
                            b.putString("via_route_1", String.format("    %s     %s miles", via1, duration1));
                            b.putString("url1", url1);
                            b.putInt("start1", start1);
                            resultSet.moveToNext();
                            String via2 = resultSet.getString(2);
                            String duration2 = resultSet.getString(5);
                            String url2 = resultSet.getString(6);
                            Integer start2 = resultSet.getInt(3);
                            b.putString("via_route_2", String.format("    %s     %s miles", via2, duration2));
                            b.putString("url2", url2);
                            b.putInt("start2", start2);
                        } else if (resultSet.getCount() == 1) {
                            resultSet.moveToFirst();
                            String via1 = resultSet.getString(2);
                            String duration1 = resultSet.getString(5);
                            String url1 = resultSet.getString(6);
                            Integer start1 = resultSet.getInt(3);
                            b.putString("via_route_1", String.format("    %s     %s miles", via1, duration1));
                            b.putString("url1", url1);
                            b.putInt("start1", start1);
                            b.putString("via_route_2", "");
                        } else {
                            b.putString("via_route_1", "");
                            b.putString("via_route_2", "");
                        }

                    }
                    else {
                        b.putString("via_route_1", String.format("BAD"));
                        b.putString("via_route_2", String.format("BAD"));
                    }
                    virtualTourIntent.putExtras(b);
                    startActivity(virtualTourIntent);
                }
            });
        }

        //
    }
}
