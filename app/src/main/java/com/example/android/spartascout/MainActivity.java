package com.example.android.spartascout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Get the user name and password
        final EditText userName=(EditText)findViewById(R.id.username);
        final EditText password=(EditText)findViewById(R.id.password);

        // Find the View that shows the login button
        Button login = (Button) findViewById(R.id.login);



        // Set a click listener on that View
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the virtual tour View is clicked on.
                @Override
                public void onClick(View view) {
                    if(userName.getText().toString().equals("sjsu") && password.getText().toString().equals("sjsu"))  {
                        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(loginIntent);
                    }else{
                        Toast.makeText(MainActivity.this, "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }
}
