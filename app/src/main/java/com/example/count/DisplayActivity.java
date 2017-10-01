package com.example.count;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Main activity
 */
public class DisplayActivity extends Activity {
    private static final String FILENAME = "file.sav";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private EditText bodyText;
    private ListView counterList;
    private int editLocation=-1;
    private ArrayList<counter> counters;
    private ArrayAdapter<counter> adapter;
    private counter editCounter;

    /**
     * constructor of main activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Button addButton = (Button) findViewById(R.id.add);
        final Button editButton = (Button) findViewById(R.id.edit);
        Button incButton = (Button) findViewById(R.id.increase);
        Button decButton = (Button) findViewById(R.id.decrease);
        Button delButton = (Button) findViewById(R.id.delete);
        Button resButton = (Button) findViewById(R.id.reset);


        counterList= (ListView) findViewById(R.id.counterList);
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Do something in response to the click
                editCounter=counters.get(position);
                editLocation=position;
            }
        };
        counterList.setOnItemClickListener(mMessageClickedHandler);

        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                add_intent(v);
                loadFromFile();
                adapter.notifyDataSetChanged();
            }

        });
        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    edit_intent();
                    loadFromFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        incButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);

                    editCounter.increase();
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }

        });
        decButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    editCounter.decrease();
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });
        delButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    counters.remove(editLocation);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });
        resButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);

                    int init=editCounter.getInit();
                    editCounter.setValue(init);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });

    }

    /**
     * intent method to switch edit activity
     */
    public void edit_intent(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(EXTRA_MESSAGE, editLocation);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * intent method to switch add activity
     * @param view
     */
    public void add_intent(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * load the old file if exist
     */
    private void loadFromFile() {
        try {
            FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();


            Type listType =new TypeToken<ArrayList<counter>>(){}.getType();
            counters = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            counters= new ArrayList<counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * save the current counters into file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);//goes stream based on filename
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos)); //create buffer writer
            Gson gson = new Gson();
            gson.toJson(counters,out);//convert java object to json string & save in output
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * start the activity and create the adapter
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<counter>(this,
                R.layout.list_item, counters);//adapter converts tweet to string
        counterList.setAdapter(adapter);
    }

}
