package com.lincoln.jake.filevault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class NewVault extends AppCompatActivity {

    String vaultname = "", vaultkey = "", vaultcontents = ""; //globals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_vault);
    }

    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public void newCreate(View view) {
        //get relevant values from textboxes
        TextView name = (TextView) findViewById(R.id.txtVaultName);
        vaultname = name.getText().toString();
        TextView contents = (TextView) findViewById(R.id.txtContents);
        vaultcontents = contents.getText().toString();
        TextView key = (TextView) findViewById(R.id.txtVaultKey);
        vaultkey = key.getText().toString(); //initialise typed data to variables


        byte[] bytesOfKey = null; //encryption accepts bytes.
        FileOutputStream outputStream;
        File file = getFileStreamPath(vaultname);
        if (file == null && !vaultname.equals("") || !file.exists() && !vaultname.equals("")) {
            try {
                vaultcontents = vaultcontents + "^&DUMMY DATA^53.2353439^-0.55101709^&DUMMY DATA^53.2353439^-0.55101709^&DUMMY DATA^53.2353439^-0.55101709";
                while (vaultkey.length() < 32) { //32 char padding for AES-256 (32 chars = 256 bits)
                    vaultkey = vaultkey + "P";
                }
//                Toast.makeText(getApplicationContext(),vaultkey + Integer.toString(vaultkey.length()), Toast.LENGTH_LONG).show(); //DEBUG: remove before submit
                bytesOfKey = vaultkey.getBytes("UTF-8");
                vaultcontents = SecurityClass.encrypt(vaultcontents, bytesOfKey);
                outputStream = openFileOutput(vaultname, MODE_PRIVATE);
                outputStream.write(vaultcontents.getBytes("UTF-8"));
                outputStream.close();
                this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (file.exists() && !vaultname.equals("")) {
            Toast.makeText(getApplicationContext(), "A vault already exists by that name, Please change the name and try again", Toast.LENGTH_LONG).show();
        } else if (vaultname.equals("")) {
            Toast.makeText(getApplicationContext(), "Invalid Vault Name", Toast.LENGTH_LONG).show();
        }
    }

}
