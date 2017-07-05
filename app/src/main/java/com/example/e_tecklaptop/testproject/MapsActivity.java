package com.example.e_tecklaptop.testproject;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.adapter.AddAdapter;
import com.example.e_tecklaptop.testproject.model.AddsItem;
import com.example.e_tecklaptop.testproject.model.MyLatLng;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;
import com.example.e_tecklaptop.testproject.utils.GPSTracker;
import com.example.e_tecklaptop.testproject.utils.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , View.OnClickListener {

    private GoogleMap mMap;
    Button button;
    double latitude;
    double longitude;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    List<MyLatLng> myLatLngsList = new ArrayList<MyLatLng>();
    private int count = 0;
    CustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        button = (Button) findViewById(R.id.getLocation);
        button.setOnClickListener(this);
        customDialog = new CustomDialog(this);
        getLatLong();






        // show location button click event



        // create class object
        gps = new GPSTracker(MapsActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
        /*    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
   //         gps.showSettingsAlert();

        }

    }




    @Override
    protected void onResume() {
        super.onResume();

    }



    private void getLatLong() {
        AsyncHttpClient client = new AsyncHttpClient();
        //   client.setBasicAuth("username", "password");
        client.setBasicAuth("simplyrets", "simplyrets");
        client.get("https://api.simplyrets.com/properties?limit=500&lastId=0", null, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("jobject", "yaha ata ha");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        super.onSuccess(statusCode, headers, response);


                        String  lat , lng , area ;


                        int y = 100 ;
                        for (int i = 0; i <= response.length(); i++) {

                            JSONObject firstEvent = null;
                            try {
                                firstEvent = response.getJSONObject(i);

                                JSONObject latlngObj = firstEvent.getJSONObject("geo");
                                JSONObject addressObj = firstEvent.getJSONObject("address");


                                lat = latlngObj.getString("lat");
                                lng = latlngObj.getString("lng");
                                area = addressObj.getString("full");

                                MyLatLng myLatLng = new MyLatLng();
                                myLatLng.setLat(Double.valueOf(lat));
                                myLatLng.setLng(Double.valueOf(lng));
                                myLatLng.setArea(area);

                                myLatLngsList.add(myLatLng);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }




                 //       customDialog.HideDialog();
                 //       apiRun = true ;
                        MultipleMarkers();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("fail object", "yaha ata ha");
                        Toast.makeText(MapsActivity.this,"Something went wrong!!",Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("fail aray", "yaha ata ha");
                        Toast.makeText(MapsActivity.this,"Something went wrong!!",Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("fail", "yaha ata ha");
                        Toast.makeText(MapsActivity.this,"Something went wrong!!",Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        Log.e("responseString", "yaha ata ha");
                        Toast.makeText(MapsActivity.this,"Something went wrong!!",Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }
                }


        );
    }

    private void MultipleMarkers() {



        for (int i = 0; i < myLatLngsList.size(); i++) {

            createMarker(myLatLngsList.get(i).getLat(),myLatLngsList.get(i).getLng() , myLatLngsList.get(i).getArea());

        }

    }



    protected void createMarker(double latitude, double longitude , String area) {

     // to do :  https://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
     mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f).title(area));
        LatLng latlng = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,
                9)); // 17

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng florida_miami = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(florida_miami).title("My Location"));
       /* mMap.moveCamera(CameraUpdateFactory.newLatLng(florida_miami));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(florida_miami,
                17));*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.getLocation:
     //           getCurrentLocation();
                break;
        }
    }
}
