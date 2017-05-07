package com.example.android.spartascout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Set;

public class PathsActivity extends AppCompatActivity {

    private AutoCompleteTextView txtSpeechInputFrom;
    private AutoCompleteTextView txtSpeechInputTo;
    private TextView textViewPath;
    private ImageButton btnSpeak;
    private Button btnSearch;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    DataBaseHelper dbHelper;
    Graph graph = new Graph();
    HashMap<String, Integer> buildingNodes = new HashMap<>();

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

        txtSpeechInputFrom = (AutoCompleteTextView) findViewById(R.id.txtSpeechInputFrom);
        txtSpeechInputTo = (AutoCompleteTextView) findViewById(R.id.txtSpeechInputTo);

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,android.R.layout.simple_list_item_1, buildings);
        txtSpeechInputFrom.setAdapter(adapter);
        txtSpeechInputTo.setAdapter(adapter);

        textViewPath = (TextView) findViewById(R.id.textViewPath);

        btnSpeak = (ImageButton) findViewById(R.id.speak);
        btnSearch = (Button) findViewById(R.id.searchPath);

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
    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
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
    }

    // Get Paths
    private ArrayList<Path> getPaths(String source, String destination, int count) {
        ArrayList<Path> paths = new ArrayList<>();

        if (!buildingNodes.containsKey(source.toLowerCase())) {
            Log.e("test", "Source does not exist: " + source);
            txtSpeechInputFrom.setError("Source does not exist");
            return paths;
        }

        if (!buildingNodes.containsKey(destination.toLowerCase())) {
            Log.e("test", "Destination does not exist: " + destination);
            txtSpeechInputTo.setError("Destination does not exist");
            return paths;
        }

        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph,
                graph.get_vertex(buildingNodes.get(source.toLowerCase())),
                graph.get_vertex(buildingNodes.get(destination.toLowerCase()))
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
                    String userInput = result.get(0);
                    String[] tokens = userInput.split("\\s?from\\s|\\sto\\s|\\s2\\s");

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

                }
                break;
            }

        }
    }

}
