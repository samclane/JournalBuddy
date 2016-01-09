package com.example.samcl.journalbuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by samcl on 1/2/2016 at 10:14 PM as a part of JournalBuddy
 * Short Description:
 */
public class NewEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EditText mTitleText = (EditText) findViewById(R.id.editText2); //title
        EditText mBodyText = (EditText) findViewById(R.id.editText3); //entty body text
        switch (item.getItemId()) {
            case R.id.action_save:
                //get title of entery for file name
                String fileName = mTitleText.getText().toString();
                //get files directory
                String fileString = mBodyText.getText().toString();
                FileOutputStream outputStream;
                try {
                    //entries can only be accessed by the calling application
                    outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(fileString.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void writeToFile(String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getBaseContext().openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }
}
