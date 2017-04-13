package com.example.eric.bazaar;

import android.content.Intent;
import android.location.Location;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class postItem extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final String USER_AGENT = "Mozilla/5.0";
    EditText edtname,edtdesc,edtprice;
    String type;
    GoogleApiClient mGoogleApiClient;
    String lat;
    String lng;
    Location mLastLocation;
    LatLng pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item);
        edtname = (EditText) findViewById(R.id.name);
        edtdesc = (EditText) findViewById(R.id.desc);
        edtprice = (EditText) findViewById(R.id.price);



        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void postBuy(View view) throws IOException {
        type="Sell";
        post(view);
    }

    public void postSell(View view) throws IOException {
        type="Buy";
        post(view);
    }

    public void post(View view) throws IOException {
        String s=edtname.getText().toString();
        String t=edtprice.getText().toString();
        String e=edtdesc.getText().toString();

        String k=((MyApplication) this.getApplication()).getuName();
        String r=((MyApplication) this.getApplication()).getRating();
        lat=Double.toString(pos.latitude);
        lng=Double.toString(pos.longitude);

        String USER_AGENT = "Mozilla/5.0";

        String url = "http://cis-linux2.temple.edu/~tud17750/bazaar/postItem.php?inputName="+s+"&inputDescription="+e+"&inputPrice="+t+"&seller="+k+"&type="+type+"&rating="+r+"&lat="+lat+"&lng="+lng;
        url=url.replaceAll(" ", "_");
        URL obj = new URL(url);
        HttpURLConnection con;
        con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        System.out.println("Response Code : " + responseCode);

        Intent intent = new Intent(this, menu.class);
        startActivity(intent);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            pos= new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
