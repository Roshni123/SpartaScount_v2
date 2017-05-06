package com.example.android.spartascout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

        copyDataBase();
//        SQLiteDatabase appdb = openOrCreateDatabase("spartascout.db",MODE_PRIVATE,null);
//        System.out.println(appdb.getPath());
//        System.out.println("ROSHNI");
//        appdb.execSQL("DROP TABLE IF EXISTS Routes");


//        appdb.execSQL("CREATE TABLE IF NOT EXISTS Routes(Source VARCHAR,Destination VARCHAR, Through VARCHAR, Start INTEGER, End INTEGER, Duration INTEGER, Url VARCHAR);");
//        appdb.execSQL("INSERT INTO Routes VALUES('Engineering Building','Boccardo Business Center', 'Via Library', 2, 60, 30, 'https://www.youtube.com/watch?v=-GDd715wJ0s&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Engineering Building','Boccardo Business Center', 'Via ATM', 5, 60, 30, 'https://www.youtube.com/watch?v=ff7mvDICXDA&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Boccardo Business Center','Engineering Building', 'Via ATM', 2, 45, 3, 'https://www.youtube.com/watch?v=V_YvlobdsYE&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Boccardo Business Center','Engineering Building', 'Via Student Union', 302, 45, 4, 'https://www.youtube.com/watch?v=C4r9SlClcmw&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Student Union','Boccardo Business Center', 'Via Library', 5, 45, 20, 'https://www.youtube.com/watch?v=V_YvlobdsYE&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Student Union','Boccardo Business Center', 'Via ATM', 7, 45, 20, 'https://www.youtube.com/watch?v=V_YvlobdsYE&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Boccardo Business Center','Student Union', 'Via ATM', 5, 45, 2, 'https://www.youtube.com/watch?v=V_YvlobdsYE&t=%ss');");
//        appdb.execSQL("INSERT INTO Routes VALUES('Boccardo Business Center','Student Union', 'Via ART Building', 302, 45, 3, 'https://www.youtube.com/watch?v=C4r9SlClcmw&t=%ss');");

        // Set a click listener on that View
        if (login != null) {
            login.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the virtual tour View is clicked on.
                @Override
                public void onClick(View view) {
                    if(userName.getText().toString().equals("") && password.getText().toString().equals(""))  {
                        Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(loginIntent);
                    }else{
                        Toast.makeText(MainActivity.this, "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    }

    private void copyDataBase()
    {
        Context context = this;
        Log.i("Database",
                "New database is being copied to device!");
        byte[] buffer = new byte[1024];
        OutputStream myOutput = null;
        int length;
        // Open your local db as the input stream
        InputStream myInput = null;
        try
        {
            myInput =context.getAssets().open("spartascout.db");
            myOutput =new FileOutputStream("/data/data/com.example.android.spartascout/databases/spartascout.db");
            while((length = myInput.read(buffer)) > 0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.i("Database",
                    "New database has been copied to device!");


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
