package com.lincoln.jake.filevault;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class displayVault extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //do at the start
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_vault);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String vaultData = extras.getString("vaultData");//get from previous activity
            TextView displayData = (TextView)findViewById(R.id.txtOutput);
            displayData.setText(vaultData); //set textview to value
        }
    }
}
