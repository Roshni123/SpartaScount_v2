package com.example.android.spartascout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class DataBaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.android.spartascout/databases/";

    private static String DB_NAME = "spartascout.db";
    public static final String TABLE_EDGES = "RouteEdges";
    public static final String TABLE_BUILDINGs = "Buildings";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 3);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

//        this.getReadableDatabase();
        SQLiteDatabase db = this.getWritableDatabase();

        try {

            copyDataBase();
            onCreate(db);

        } catch (IOException e) {

            throw new Error("Error copying database");

        }
    }


    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;

            File DBFolder = new File(DB_PATH);
            if (!DBFolder.exists()) DBFolder.mkdir();

            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public Cursor getEdges() throws SQLException {
        String myDbPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY);
        String selectQuery = "SELECT  * FROM " + TABLE_EDGES;
        Cursor c = myDataBase.rawQuery(selectQuery, null);
        return c;
    }

    public int getMaxNode(String column_name) {// use the data type of the column
        String myDbPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = myDataBase.query(TABLE_EDGES, new String[]{"MAX(" +column_name + ") AS MAX"}, null, null, null, null, null);
        cursor.moveToFirst(); // to move the cursor to first record
        int index = cursor.getColumnIndex("MAX");
        int data = cursor.getInt(index);// use the data type of the column or use String itself you can parse it
        myDataBase.close();
        return data;
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public HashMap<String, Integer> getBuildingNodes() {
        HashMap<String, Integer> buildingNodes = new HashMap<>();
        String myDbPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myDbPath, null, SQLiteDatabase.OPEN_READONLY);
        String selectQuery = "SELECT  * FROM " + TABLE_BUILDINGs;
        Cursor c = myDataBase.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                String buildingName = c.getString(c.getColumnIndex("building_name"));
                int node_id = c.getInt(c.getColumnIndex("node_id"));
                buildingNodes.put(buildingName, node_id);
            } while (c.moveToNext());
        }
        return buildingNodes;
    }
}
