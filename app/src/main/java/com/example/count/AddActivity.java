package com.example.count;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
public class AddActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private ArrayList<counter> counters;
    private EditText nameText;
    private EditText initText;
    private EditText comText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Button finButton = (Button) findViewById(R.id.finish);
        nameText = (EditText) findViewById(R.id.name);
        initText = (EditText) findViewById(R.id.init);
        comText = (EditText) findViewById(R.id.com);
        finButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String name=nameText.getText().toString();
                int init=(int) Integer.valueOf(initText.getText().toString());
                String com=comText.getText().toString();
                counter newcounter =new counter(name,init,com);
                counters.add(newcounter);
                saveInFile();
                end();
            }
        });
    }
    private void end(){
        this.finish();
    }
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
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();
    }
}