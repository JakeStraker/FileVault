package com.lincoln.jake.filevault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Wipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wipe);
        TextView confirm = (TextView)findViewById(R.id.txtWarningConfirm);
        confirm.setSelectAllOnFocus(true);
    }
    public void clearVaults(View view){
        String userTyped = "";
        TextView confirm = (TextView)findViewById(R.id.txtWarningConfirm);
        userTyped = confirm.getText().toString();
        if(userTyped.equals("DELVAULTSnow")) { //for all files in the internal storage directory, delete the file.
            for (File file : getFilesDir().listFiles()) file.delete();
            Toast.makeText(getApplicationContext(), "Vaults Cleared", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Error: Incorrect code", Toast.LENGTH_LONG).show();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
