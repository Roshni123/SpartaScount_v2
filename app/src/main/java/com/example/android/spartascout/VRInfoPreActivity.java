package com.example.android.spartascout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;


public class VRInfoPreActivity extends YouTubeBaseActivity {
    String url1;
    String url2;
    Integer start1;
    Integer start2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_preinfo);
        TextView message = (TextView) findViewById(R.id.text_msg);
        message.setVisibility(View.INVISIBLE);
        Bundle b = getIntent().getExtras();
        String via_route_1 = b.getString("via_route_1");
        String via_route_2 = b.getString("via_route_2");

        RadioButton route1 = (RadioButton) findViewById(R.id.radio_library);
        RadioButton route2 = (RadioButton) findViewById(R.id.radio_engineering);

        // Handle Case when Route is not present in dropdown List
        if(via_route_1.equals("BAD")){
            System.out.println("RORORO 1");
            route1.setCompoundDrawables( null, null, null, null );
            route2.setCompoundDrawables( null, null, null, null );
            route1.setVisibility(View.INVISIBLE);
            route2.setVisibility(View.INVISIBLE);
            message.setVisibility(View.VISIBLE);
        }

        // Handle case when route is present in dropdown list
        else {
            // If route is not present. Don't display rout and hide radio button.
            if (!via_route_1.equals("")) {
                route1.setText(via_route_1);
                url1 = b.getString("url1");
                start1 = b.getInt("start1");
            } else {
                route1.setVisibility(View.INVISIBLE);
                route1.setCompoundDrawables(null, null, null, null);
            }
            // If route is not present. Don't display rout and hide radio button.
            if (!via_route_2.equals("")) {
                route2.setText(via_route_2);
                url2 = b.getString("url2");
                start2 = b.getInt("start2");
            } else {
                route2.setVisibility(View.INVISIBLE);
                route2.setCompoundDrawables(null, null, null, null);
            }
            // Handle case when route is present in dropdown list but not in db
            if (via_route_1.equals("") && via_route_2.equals("")) {
                System.out.println("RORORO 2");
                route1.setCompoundDrawables(null, null, null, null);
                route2.setCompoundDrawables(null, null, null, null);
                route1.setVisibility(View.INVISIBLE);
                route2.setVisibility(View.INVISIBLE);
                message.setText("Video is currently unavailable. Please go back and try later.");
                message.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        Button start = (RadioButton) view;
        switch(view.getId()) {
            case R.id.radio_library:
                if (checked) {
                    Intent virtualTourIntent = new Intent(VRInfoPreActivity.this, VRInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putString("url", url1);
                    b.putInt("start", start1);
                    virtualTourIntent.putExtras(b);
                    startActivity(virtualTourIntent);
                    break;
                }
            case R.id.radio_engineering:
                if (checked) {
                    Intent virtualTourIntent = new Intent(VRInfoPreActivity.this, VRInfoActivity2.class);
                    Bundle b = new Bundle();
                    b.putString("url", url2);
                    b.putInt("start", start2);
                    virtualTourIntent.putExtras(b);
                    startActivity(virtualTourIntent);
                    break;
                }
        }
    }
}
