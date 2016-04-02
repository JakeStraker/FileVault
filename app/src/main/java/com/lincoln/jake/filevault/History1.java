package com.lincoln.jake.filevault;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class History1 extends AppCompatActivity {
    static boolean pullingData = false;
    public String Data = null, name = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history1);

    }
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    public void getSelectedVault(View view) {
        pullingData = true;
        Intent intent = new Intent(this, mountVault.class);
        startActivityForResult(intent, 1);
    }
    public void historyOpen(){

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Data = data.getStringExtra("VaultData"); //set globals from mount vault form
                name = data.getStringExtra("vname");
                TextView vaultname = (TextView)findViewById(R.id.lblHistory);
                name = "Selected Vault: " + name; //write out what vault is currently being used
                vaultname.setText(name);
//                Toast.makeText(getApplicationContext(), "Data out: " + Data , Toast.LENGTH_LONG).show(); //debug toast
                String[] split = Data.split(Pattern.quote("^&")); //user cannot enter those characters into a vault
                String[] lastThree = new String[3]; //get arrays ready to be put into listview
                lastThree[0] = parseHistoryData(split[1]);
                lastThree[1] = parseHistoryData(split[2]);
                lastThree[2] = parseHistoryData(split[3]);
                ArrayList<String> listLastThree = new ArrayList<String>(Arrays.asList(lastThree));
                ListView historyList = (ListView)findViewById(R.id.lstHistoryEvents);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listLastThree);
                historyList.setAdapter(arrayAdapter); //put items into list view
                historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) { //capture which item in listview was clicked
                        String item = ((TextView) view).getText().toString();
                        Intent i = new Intent(History1.this, History2.class);
                        i.putExtra("itemdata",item); //pass to next activity
                        startActivity(i);
                    }
                });

            }
        }
    }
    public String parseHistoryData(String input)
    {
        String output = null;
        String[] split = input.split(Pattern.quote("^"));
        output = "Vault was opened on " + split[0] + " at Latitude: ^" + split[1] + "^ and Longitude: ^" + split[2] + "^ Tap for More Info";
        return output;
    }
}
