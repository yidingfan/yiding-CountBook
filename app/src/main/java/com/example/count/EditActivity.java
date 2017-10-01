package com.example.count;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
public class EditActivity extends Activity {
    private static final String FILENAME = "file.sav";
    private int editLocation=0;
    private ArrayList<counter> counters;
    private counter editCounter;
    private EditText nameText;
    private EditText initText;
    private EditText curText;
    private EditText comText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button subButton = (Button) findViewById(R.id.submit);
        Intent intent = getIntent();
        editLocation =  intent.getIntExtra(DisplayActivity.EXTRA_MESSAGE,0);
        nameText = (EditText) findViewById(R.id.name);
        initText = (EditText) findViewById(R.id.init);
        comText = (EditText) findViewById(R.id.com);
        curText = (EditText) findViewById(R.id.current);
        subButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String name=nameText.getText().toString();
                int init=(int) Integer.valueOf(initText.getText().toString());
                int cur=(int) Integer.valueOf(curText.getText().toString());
                String com=comText.getText().toString();

                editCounter.setComment(com);
                editCounter.setInit(init);
                editCounter.setValue(cur);
                editCounter.setName(name);
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


        editCounter=counters.get(editLocation);
        nameText.setText(editCounter.getName(), TextView.BufferType.EDITABLE);
        initText.setText(Integer.toString(editCounter.getInit()), TextView.BufferType.EDITABLE);;
        comText.setText(editCounter.getComment(), TextView.BufferType.EDITABLE);;
        curText.setText(Integer.toString(editCounter.getCurr()), TextView.BufferType.EDITABLE);

    }
}