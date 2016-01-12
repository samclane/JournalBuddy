package com.example.samcl.journalbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by samcl on 1/2/2016 at 10:14 PM as a part of JournalBuddy
 * Short Description:
 */
public class NewEntry extends AppCompatActivity {

    EditText mTitleText;
    EditText mBodyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_entry);
        mTitleText = (EditText) findViewById(R.id.editText2); //title
        mBodyText = (EditText) findViewById(R.id.editText3); //entty body text
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        // Get the extras (if there are any)
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("ENTRY")) {
                mTitleText.setText(extras.getString("TITLE"));
                mBodyText.setText(extras.getString("ENTRY"));
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                onActionSave();
            case R.id.action_delete:
                onActionDelete();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onActionSave() {
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
            Toast.makeText(this, fileName + " has been saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR Saving " + fileName, Toast.LENGTH_LONG).show();
        }
    }

    public void onActionDelete() {
        final String fileName = mTitleText.getText().toString();
        //TODO:IMPORTANT:Add confirmation popup.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteFile(fileName);
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        MainActivity.removeEntry(fileName);
        dialog.show();
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
