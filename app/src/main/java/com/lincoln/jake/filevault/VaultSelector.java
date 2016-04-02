package com.lincoln.jake.filevault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;

public class VaultSelector extends AppCompatActivity {
public boolean vaults = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_selector);
        Spinner confirm = (Spinner)findViewById(R.id.spnDropdown);
        ArrayList<String> filenames = new ArrayList<String>();
        for (File file : getFilesDir().listFiles()) {
            if(!file.getName().contains("com.lincoln")) { //ignore all other files.
                filenames.add(file.getName());
                vaults = true;
            }
        }
        confirm.setAdapter((new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, filenames))); //arrayAdapter for adding to list
    }
    public void selectConfirmVault(View view) {
        if(vaults) {
            Spinner confirm = (Spinner) findViewById(R.id.spnDropdown); //declare dropdown box
            Intent intent = new Intent();
            intent.putExtra("selectedVault", confirm.getSelectedItem().toString());
            setResult(RESULT_OK, intent);
            Toast.makeText(getApplicationContext(), "Selected Vault: " + confirm.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "There are no vaults stored, Why not go create one?", Toast.LENGTH_LONG).show();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}