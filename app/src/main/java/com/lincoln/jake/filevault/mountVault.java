package com.lincoln.jake.filevault;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.regex.Pattern;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;




public class mountVault extends AppCompatActivity {
    String selectedVault = null, VData = null, VaultData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mount_vault);
    }

    public void onDestroy() { //need to call garbage collector as keys are stored on memory
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public void getVaultData() throws IOException{
        StringBuilder datax = new StringBuilder("");
        try {
            FileInputStream fIn = openFileInput(selectedVault); //get from internal storage
            InputStreamReader isr = new InputStreamReader( fIn ) ;
            BufferedReader buffreader = new BufferedReader( isr ) ;
            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) { //keep reading while file has content
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }
            VData = datax.toString(); //put file to string
            isr.close ( ) ; //close to prevent leaks.
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
    }

    public void getSelectedVault(View view) {
        Intent intent = new Intent(this, VaultSelector.class);
        startActivityForResult(intent, 1); //get expecting data back
    }
    public void unlockMountVault(View view){
        EditText vaultkey = (EditText) findViewById(R.id.txtVaultKey);
        String key = vaultkey.getText().toString();
        try{
            getVaultData();
            while (key.length() < 32) { ////32 char padding for AES-256 (32 chars = 256 bits)
                key = key + "P";
            }
            byte[] byteArray = key.getBytes("UTF-8");
            VData = SecurityClass.decrypt(VData, byteArray);
            String[] split = VData.split(Pattern.quote("^&")); //user cant type those characters

            if(!History1.pullingData) {
                VaultData = split[0];
                    Intent i = new Intent(this, displayVault.class);
                    i.putExtra("vaultData", VaultData);
                Calendar calobj = Calendar.getInstance();
                String timeNow = calobj.getTime().toString();
                File file = new File(getFilesDir(),selectedVault);
                file.delete(); //delete existing file
                split[3] = split[2]; //move up the additional data, lose oldest data.
                split[2] = split[1];
                split[1] =  timeNow + "^" + whereAmI(); //Append time/date into history string
                String newData = VaultData + "^&" + split[1] + "^&" + split[2] + "^&" + split[3];
                String encString = SecurityClass.encrypt(newData, byteArray);
                FileOutputStream outputStream;
                outputStream = openFileOutput(selectedVault, MODE_PRIVATE);
                outputStream.write(encString.getBytes("UTF-8"));
                outputStream.close();
                Toast.makeText(getApplicationContext(), split[1], Toast.LENGTH_LONG).show();
                startActivity(i);
            }
            else{
                Intent intent = new Intent();
                intent.putExtra("VaultData", VData);
                intent.putExtra("vname", selectedVault);
                setResult(RESULT_OK, intent);
                History1.pullingData = false; //get data for history activity
                finish();
            }
        }
        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Incorrect Key", Toast.LENGTH_LONG).show(); //throw errors (only error I have been able to throw is due to having the wrong key)
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                TextView vaultname = (TextView) findViewById(R.id.lblSelectedVault);
                selectedVault = data.getStringExtra("selectedVault");
                String vtext = "Currently Selected Vault: " + selectedVault;
                vaultname.setText(vtext);
            }
        }
    }
    public String whereAmI()
    {
        try {
            // get location code
            // Acquire a reference to the system Location Manager
            final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            final LocationListener listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, listener);
            try
            {
                Thread.sleep(1000);
            }catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Use network provider to get last known location
            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            locationManager.removeUpdates(listener);
            // create a few new variable to get and store the lat/long coordinates of last known location
            double lat;
            double longi;
            // check if a last known location exists
            if (lastKnownLocation == null) {
                // if no last location is available set lat/long to zero
                lat = 53.228029;  // lat of Lincoln is 53.228029;
                longi = -0.546055; // longi of Lincoln is -0.546055;
                return(lat + "^" +longi + "^");
            } else {
                // if last location exists then get/set the lat/long
                lat = lastKnownLocation.getLatitude();
                longi = lastKnownLocation.getLongitude();
                return(lat + "^" +longi);
            }
        }catch(SecurityException e) {
            return null;
        }
        }

    }
