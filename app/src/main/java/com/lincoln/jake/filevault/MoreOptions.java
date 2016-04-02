package com.lincoln.jake.filevault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MoreOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void moWipeVaults(View view) {
        Intent intent = new Intent(this, Wipe.class); //buttons to each new screen
        startActivity(intent);
    }
    public void moBack(View view) {
        this.finish();
    }

    public void moVaultHistory(View view) {
        Intent intent = new Intent(this, History1.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}
