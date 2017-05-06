package com.example.android.spartascout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener  {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //**************************************************************************************************************
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        //Google Sign In

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        copyDataBase();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

        private void signIn() {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();

            Intent loginIntent = new Intent(MainActivity.this, HomeActivity.class);
            loginIntent.putExtra("NAME", personName);
            startActivity(loginIntent);
        } else {
            Toast.makeText(MainActivity.this, "Username or Password incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }



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


