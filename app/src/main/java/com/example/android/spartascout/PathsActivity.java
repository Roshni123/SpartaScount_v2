package com.example.android.spartascout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spartascout.pathsControl.YenTopKShortestPathsAlg;
import com.example.android.spartascout.pathsModel.Graph;
import com.example.android.spartascout.pathsModel.Path;
import com.example.android.spartascout.pathsModel.Vertex;
import com.example.android.spartascout.pathsModel.abstracts.BaseVertex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class PathsActivity extends AppCompatActivity {

    private AutoCompleteTextView txtSpeechInputFrom;
    private AutoCompleteTextView txtSpeechInputTo;
    private TextView textViewPath;
    private ImageButton btnSpeak;
    private Button btnSearch;
    private ImageView imageViewSJSU;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    DataBaseHelper dbHelper;
    Graph graph = new Graph();
    HashMap<String, Integer> buildingNodes = new HashMap<>();
    HashMap<Integer, ArrayList<Float>> nodeCoordinates = new HashMap<>();

    private int fieldImgXY[] = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paths);


        dbHelper = new DataBaseHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            throw new Error("Unable to create db");
        }

        try {
            dbHelper.openDataBase();
        } catch (SQLException se) {
            throw se;
        }

        int maxFrom = dbHelper.getMaxNode("node1");
        int maxTo = dbHelper.getMaxNode("node2");
        int maxNode;

        if (maxFrom > maxTo) {
            maxNode = maxFrom;
        } else {
            maxNode = maxTo;
        }

        Cursor c = dbHelper.getEdges();

        for (int i = 0; i < maxNode + 1; ++i) {
            BaseVertex vertex = new Vertex();
            graph._vertex_list.add(vertex);
            graph._id_vertex_index.put(vertex.get_id(), vertex);
        }

        if (c.moveToFirst()) {
            do {
                String edgeNo = c.getString(c.getColumnIndex("edge_no"));
                int node1 = c.getInt(c.getColumnIndex("node1"));
                int node2 = c.getInt(c.getColumnIndex("node2"));
                double distance = c.getDouble(c.getColumnIndex("distance"));
                graph.add_edge(node1, node2, distance);
                graph.add_edge(node2, node1, distance);
            } while (c.moveToNext());
        }

        buildingNodes = dbHelper.getBuildingNodes();
        Set<String> keys = buildingNodes.keySet();
        String [] buildings = keys.toArray(new String[keys.size()]);

        nodeCoordinates = dbHelper.getNodeCoordinates();

        txtSpeechInputFrom = (AutoCompleteTextView) findViewById(R.id.txtSpeechInputFrom);
        txtSpeechInputTo = (AutoCompleteTextView) findViewById(R.id.txtSpeechInputTo);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, buildings);
        txtSpeechInputFrom.setAdapter(adapter);
        txtSpeechInputTo.setAdapter(adapter);

        textViewPath = (TextView) findViewById(R.id.textViewPath);

        btnSpeak = (ImageButton) findViewById(R.id.speak);
        btnSearch = (Button) findViewById(R.id.searchPath);
        imageViewSJSU = (ImageView) findViewById(R.id.imageViewSJSU);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchPath();
            }
        });

        imageViewSJSU.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.d("Touch coordinates : ",
                            String.valueOf(event.getX()) + "x" + String.valueOf(event.getY()));
                }
                return true;
            }
        });
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! your device doesn't support speech input",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // Search Path
    private void searchPath() {
        String source = txtSpeechInputFrom.getText().toString();
        String destination = txtSpeechInputTo.getText().toString();

        ArrayList<Path> paths = getPaths(source, destination, 10);

        for (int i = 0; i < paths.size(); i++)
        {
            String p = paths.get(i).toString();
            if (i == 0) {
                textViewPath.setText(p);
            }
            Log.i("Paths", "Path "+i+++" : "+p);
        }

        drawPaths(paths);
    }

    private void drawPaths(ArrayList<Path> paths){
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.campus_map, myOptions);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);


        Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);


        Canvas canvas = new Canvas(mutableBitmap);

        Path best = paths.get(0);
        List<BaseVertex> vertices =  best.get_vertices();

        for (int i = 0; i < vertices.size() - 1; i++) {
            int node1 = vertices.get(i).get_id();
            float x1 = nodeCoordinates.get(node1).get(0);
            float y1 = (float) (nodeCoordinates.get(node1).get(1) - 1171.0);

            int node2 = vertices.get(i+1).get_id();
            float x2 = nodeCoordinates.get(node2).get(0);
            float y2 = (float) (nodeCoordinates.get(node2).get(1) - 1171.0);

            canvas.drawLine(x1, y1, x2, y2, paint);
        }

//        canvas.drawLine(100, 100, 500, 100, paint);

        ImageView imageView = (ImageView)findViewById(R.id.imageViewSJSU);
        imageView.setAdjustViewBounds(true);
        imageView.setImageBitmap(mutableBitmap);
    }

    // Get Paths
    private ArrayList<Path> getPaths(String source, String destination, int count) {
        ArrayList<Path> paths = new ArrayList<>();

        if (!buildingNodes.containsKey(source)) {
            Log.e("test", "Source does not exist: " + source);
            txtSpeechInputFrom.setError("Source does not exist");
            return paths;
        }

        if (!buildingNodes.containsKey(destination)) {
            Log.e("test", "Destination does not exist: " + destination);
            txtSpeechInputTo.setError("Destination does not exist");
            return paths;
        }

        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph,
                graph.get_vertex(buildingNodes.get(source)),
                graph.get_vertex(buildingNodes.get(destination))
        );

        int i = 0;
        while(yenAlg.has_next() && i < count)
        {
            paths.add(yenAlg.next());
            i += 1;
        }

        return paths;
    }


    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.i("test", result.get(0));

//                    String userInput = "from engineering building to mlk library";
                    String userInput = result.get(0).toLowerCase();
                    String[] tokens = userInput.split("\\s?from\\s|\\sto\\s|\\sii\\s|\\s2\\s");

                    String source = "";
                    String destination = "";

                    if (tokens.length == 3) {
                        source = tokens[1];
                        destination = tokens[2];
                    } else if (tokens.length == 2) {
                        source = tokens[0];
                        destination = tokens[1];
                    }

                    ArrayList<Path> paths = getPaths(source, destination, 10);

                    for (int i = 0; i < paths.size(); i++)
                    {
                        String p = paths.get(i).toString();
                        if (i == 0) {
                            textViewPath.setText(p);
                        }
                        Log.i("Paths", "Path "+i+++" : "+p);
                    }

                    txtSpeechInputFrom.setText(source);
                    txtSpeechInputTo.setText(destination);

                    drawPaths(paths);

                }
                break;
            }

        }
    }

}
