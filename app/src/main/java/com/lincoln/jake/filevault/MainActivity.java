package com.lincoln.jake.filevault;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void menuOpenVault(View view) { //buttons to each new screen
        Intent intent = new Intent(this, mountVault.class);
        startActivity(intent);
    }
    public void menuCreateVault(View view) {
        Intent intent = new Intent(this, NewVault.class);
        startActivity(intent);
    }
    public void menuMoreOptions(View view) {
        Intent intent = new Intent(this, MoreOptions.class);
        startActivity(intent);
    }
}
