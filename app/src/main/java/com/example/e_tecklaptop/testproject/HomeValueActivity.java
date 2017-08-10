package com.example.e_tecklaptop.testproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.adapter.AddAdapter;
import com.example.e_tecklaptop.testproject.database.MyDBHandler;
import com.example.e_tecklaptop.testproject.database.PhotoDBHandler;
import com.example.e_tecklaptop.testproject.model.AddsItem;
import com.example.e_tecklaptop.testproject.model.Images;
import com.example.e_tecklaptop.testproject.model.MyLatLng;
import com.example.e_tecklaptop.testproject.utils.Const;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class HomeValueActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView profile, setting, location, calculator_button, notification_button, MenuButton;
    RelativeLayout menu;
    EditText searchInput;
    TextView FirstDescription, SecondDescription, ThirdDescription, FourDescription, FiveDescription, LaunchScanner;

    static final String username = "nate@teamwinnett.com";
    static final String password = "cloud1234";

    CustomDialog customDialog;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    String mPermission2 = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private boolean locationAccess = false;
    AddAdapter addAdapter;
    ListView listView;
    private boolean apiRun = false;

    ArrayList<AddsItem> dataItem = new ArrayList<AddsItem>();

    List<MyLatLng> myLatLngsList = new ArrayList<MyLatLng>();
    private Toolbar toolbar;
    private PhotoDBHandler photoDBHandler;
    private MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_value);
        setupUI(findViewById(R.id.mainPanel));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

   //     getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
    //    getSupportActionBar().setCustomView(R.layout.actionbar);

        RegisterView();
        setListeners();

        customDialog = new CustomDialog(HomeValueActivity.this);

        photoDBHandler = new PhotoDBHandler(HomeValueActivity.this, null, null, 2);
        dbHandler = new MyDBHandler(HomeValueActivity.this, null, null, 3);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listView = (ListView) findViewById(R.id.addlist);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddsItem item = (AddsItem) parent.getItemAtPosition(position);
                int propertyID = item.getId();
                String address = item.getAddress();
                String image = item.getImage();
                String area = item.getArea();
                String baths = item.getBaths();
                String beds = item.getBeds();
                String price = item.getPrice();
                String desc = item.getDescription();
                String lat = item.getLatitude();
                String lng = item.getLogitude();
                String style = item.getStyle();
                String property_type = item.getProperty_type();
                String country = item.getCountry();
                String mlsid = item.getMlsID();
                String lotSize = item.getLotSize();
                String built = item.getYearBuilt();
                String allImages = item.getInsertAllImages();
                ArrayList<ArrayList<String>> plist = item.getAllPhotos();
                ArrayList<Images> imagesArrayList = new ArrayList<Images>();
                imagesArrayList = photoDBHandler.findItems(propertyID);

                List<String> photoList = new ArrayList<String>();

                for (int i = 0; i < imagesArrayList.size(); i++) {
                    photoList.add(imagesArrayList.get(i).getImage());
                }
                //          ArrayList<Images> photoList = photoDBHandler.findItems();
                Set<String> set = new HashSet<String>();
                set.addAll(photoList);

                SharedPreferences preferences = getSharedPreferences("ListDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("address", address);
                editor.putString("image", image);
                editor.putString("area", area);
                editor.putString("baths", baths);
                editor.putString("beds", beds);
                editor.putString("price", price);
                editor.putString("desc", desc);
                editor.putString("lat", lat);
                editor.putString("lng", lng);
                editor.putString("style", style);
                editor.putString("property_type", property_type);
                editor.putString("country", country);
                editor.putString("mlsID", mlsid);
                editor.putString("lotSize", lotSize);
                editor.putString("built", built);
                editor.putStringSet("photoList", set);
                editor.putString("insertAllImages", allImages);
                editor.commit();

                Intent intent = new Intent(HomeValueActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        customDialog.ShowDialog();

        if (!apiRun) {

            ListApi();
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void searchInputClicked(View v) {

        searchInput.setCursorVisible(true);

        if ((searchInput.getText().toString().equals("Search here..."))) {
            searchInput.setText(" ");
        }
    }


    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
        if (searchInput.getText().toString().equals(" ") || searchInput.getText().toString().equals("")) {
            searchInput.setText("");
            searchInput.setText("Search here...");
        }
        searchInput.setCursorVisible(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu , menu);

        // or you can write one line code {  getMenuInflator().inflate(R.menu.option_menu , menu); }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.qrcode:

                Intent intent = new Intent(HomeValueActivity.this , QRCodeScanner.class);
                startActivity(intent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(HomeValueActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    private void RegisterView() {
        profile = (ImageView) findViewById(R.id.profile_button);
        setting = (ImageView) findViewById(R.id.setting_button);
        location = (ImageView) findViewById(R.id.location_button);
        notification_button = (ImageView) findViewById(R.id.bell_button);
        calculator_button = (ImageView) findViewById(R.id.calculator_button);
        searchInput = (EditText) findViewById(R.id.actionInput);
        MenuButton = (ImageView) findViewById(R.id.menuButton);
        LaunchScanner = (TextView) findViewById(R.id.launchScanner);
   //     menu = (RelativeLayout) findViewById(R.id.customMenu);
    }

    private void setListeners() {
        profile.setOnClickListener(this);
        setting.setOnClickListener(this);
        location.setOnClickListener(this);
        notification_button.setOnClickListener(this);
        calculator_button.setOnClickListener(this);
  //      MenuButton.setOnClickListener(this);
        LaunchScanner.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.profile_button:
                Intent imageIntent = new Intent(HomeValueActivity.this, DashBoardActivity.class);
                startActivity(imageIntent);
                break;
            case R.id.setting_button:
                Intent setting_intent = new Intent(HomeValueActivity.this, SupportActivity.class);
                startActivity(setting_intent);
                break;
            case R.id.location_button:

                if (checkLocationPermission()) {
                    Intent mapIntent = new Intent(HomeValueActivity.this, MapsActivity.class);
                    startActivity(mapIntent);
                }

                break;

            case R.id.bell_button:
                Intent bell_intent = new Intent(HomeValueActivity.this, NotificationSettingActivity.class);
                startActivity(bell_intent);
                break;
            case R.id.calculator_button:
                Intent cal_intent = new Intent(HomeValueActivity.this, FlatFeeCalculator.class);
                startActivity(cal_intent);

                break;
            case R.id.menuButton:
                if (menu.getVisibility() == View.GONE) {
                    menu.setVisibility(View.VISIBLE);
                } else if (menu.getVisibility() == View.VISIBLE) {
                    menu.setVisibility(View.GONE);
                }
                break;
            case R.id.launchScanner:
                Intent intent = new Intent(HomeValueActivity.this, QRCodeScanner.class);
                startActivity(intent);
                menu.setVisibility(View.GONE);
                break;

        }
    }


    private void ListApi() {
        AsyncHttpClient client = new AsyncHttpClient();
        //   client.setBasicAuth("username", "password");
        client.setBasicAuth(Const.AUTH, Const.AUTH);
        client.get("https://api.simplyrets.com/properties?limit=500&lastId=0", null, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.e("jobject", "yaha ata ha");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                        super.onSuccess(statusCode, headers, response);


                        String roof, bath, beds, stories, area, address, photo, lat, lng , description;
                        String style , property_type , community , country , mlsId , lot_size , built;

                        MapsActivity location = new MapsActivity();
                        int y = 100;
                        for (int i = 0; i <= response.length(); i++) {

                            JSONObject firstEvent = null;
                            try {
                                firstEvent = response.getJSONObject(i);
                                JSONObject data = firstEvent.getJSONObject("property");
                                JSONObject addressObj = firstEvent.getJSONObject("address");
                                JSONArray photoObj = firstEvent.getJSONArray("photos");
                                JSONObject latlngObj = firstEvent.getJSONObject("geo");


                                int price = firstEvent.getInt("listPrice");
                                roof = data.getString("roof");
                                bath = data.getString("bathsFull");
                                beds = data.getString("bedrooms");
                                stories = data.getString("stories");
                                area = data.getString("area");
                                description = data.getString("lotDescription");
                                address = addressObj.getString("full");
                                photo = photoObj.getString(1);
                                lat = latlngObj.getString("lat");
                                lng = latlngObj.getString("lng");
                                style = data.getString("style");
                                property_type = data.getString("type");
                                built = data.getString("yearBuilt");
                                lot_size = data.getString("lotSize");
                                country = addressObj.getString("country");
                                mlsId = (String) firstEvent.getString("mlsId");


                                ArrayList<String> allPhotos = new ArrayList<String>();

                                MyLatLng myLatLng = new MyLatLng();
                                myLatLng.setLat(Double.valueOf(lat));
                                myLatLng.setLng(Double.valueOf(lng));

                                SharedPreferences preferences = getSharedPreferences("MyLocations", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(String.valueOf(i), lat);
                                editor.putString(String.valueOf(y), lng);
                                editor.commit();
                                y++;
                                myLatLngsList.add(myLatLng);

                                //             location.setMyLatLng(myLatLngsList);


                                SharedPreferences checkPref = getSharedPreferences("MinMax", Context.MODE_PRIVATE);
                                int minPrice = Integer.valueOf(checkPref.getString("min", ""));
                                int maxPrice = Integer.valueOf(checkPref.getString("max", ""));
                                String areaCheck = checkPref.getString("areaSqFt", "");
                                String bedCheck = checkPref.getString("beds", "");

                                int sqFeet= 0 , areaSqFt =0 , sqFeetMin = 0, sqFeetMax = 0 ;
                                if(!(areaCheck.equals(""))) {
                                    sqFeet = Integer.parseInt(areaCheck);
                                    areaSqFt = Integer.parseInt(area);
                                    sqFeetMin = sqFeet - 100;
                                    sqFeetMax = sqFeet + 100;
                                }

                                if (!(areaCheck.equals("")) && (bedCheck.equals(""))) {
                                    if (price >= minPrice && price <= maxPrice) {
                                        if (areaSqFt > sqFeetMin && areaSqFt < sqFeetMax) {
                                            AddsItem item = new AddsItem();
                                            item.setDescription(description);
                                            item.setBeds(beds);
                                            item.setBaths(bath);
                                            item.setArea(area);
                                            item.setAddress(address);
                                            item.setImage(photo);
                                            item.setPrice(String.valueOf(price));
                                            item.setLatitude(lat);
                                            item.setLogitude(lng);
                                            item.setStyle(style);
                                            item.setProperty_type(property_type);
                                            item.setMlsID(mlsId);
                                            item.setCountry(country);
                                            item.setYearBuilt(built);
                                            item.setLotSize(lot_size);
                                            dataItem.add(item);

                                            ArrayList<AddsItem> propertyData = dbHandler.findItems();

                                            int pID = propertyData.get(propertyData.size()-1).getId();

                                            for (int z = 0; z < photoObj.length(); z++) {

                                                allPhotos.add(photoObj.getString(z));
                                                Images images = new Images();
                                                images.setImage(photoObj.getString(z));
                                                images.setPropertyID(pID);
                                                //      allPhotos.add(images);
                                                photoDBHandler.addImage(images);

                                            }
                                        }
                                    }
                                } else if (!(bedCheck.equals("")) && (areaCheck.equals(""))) {
                                    if (price >= minPrice && price <= maxPrice && beds.equals(bedCheck)) {
                                        AddsItem item = new AddsItem();
                                        item.setDescription(description);
                                        item.setBeds(beds);
                                        item.setBaths(bath);
                                        item.setArea(area);
                                        item.setAddress(address);
                                        item.setImage(photo);
                                        item.setPrice(String.valueOf(price));
                                        item.setLatitude(lat);
                                        item.setLogitude(lng);
                                        item.setStyle(style);
                                        item.setProperty_type(property_type);
                                        item.setMlsID(mlsId);
                                        item.setCountry(country);
                                        item.setYearBuilt(built);
                                        item.setLotSize(lot_size);
                                        dataItem.add(item);

                                        ArrayList<AddsItem> propertyData = dbHandler.findItems();

                                        int pID = propertyData.get(propertyData.size()-1).getId();

                                        for (int z = 0; z < photoObj.length(); z++) {

                                            allPhotos.add(photoObj.getString(z));
                                            Images images = new Images();
                                            images.setImage(photoObj.getString(z));
                                            images.setPropertyID(pID);
                                            //      allPhotos.add(images);
                                            photoDBHandler.addImage(images);

                                        }
                                    }
                                } else if (!(bedCheck.equals("")) && !(areaCheck.equals(""))) {
                                    if (price >= minPrice && price <= maxPrice && beds.equals(bedCheck)) {
                                        if (areaSqFt > sqFeetMin && areaSqFt < sqFeetMax) {
                                            AddsItem item = new AddsItem();
                                            item.setDescription(description);
                                            item.setBeds(beds);
                                            item.setBaths(bath);
                                            item.setArea(area);
                                            item.setAddress(address);
                                            item.setImage(photo);
                                            item.setPrice(String.valueOf(price));
                                            item.setLatitude(lat);
                                            item.setLogitude(lng);
                                            item.setStyle(style);
                                            item.setProperty_type(property_type);
                                            item.setMlsID(mlsId);
                                            item.setCountry(country);
                                            item.setYearBuilt(built);
                                            item.setLotSize(lot_size);
                                            dataItem.add(item);

                                            ArrayList<AddsItem> propertyData = dbHandler.findItems();

                                            int pID = propertyData.get(propertyData.size()-1).getId();

                                            for (int z = 0; z < photoObj.length(); z++) {

                                                allPhotos.add(photoObj.getString(z));
                                                Images images = new Images();
                                                images.setImage(photoObj.getString(z));
                                                images.setPropertyID(pID);
                                                //      allPhotos.add(images);
                                                photoDBHandler.addImage(images);

                                            }
                                        }
                                    }
                                } else if ((areaCheck.equals("")) && (bedCheck.equals("")) && price >= minPrice && price <= maxPrice) {
                                    AddsItem item = new AddsItem();
                               //     item.setDescription(beds + " beds - " + bath + " baths - " + area + " SqFt");
                                    AddsItem addsItem = new AddsItem();
                                    addsItem.setDescription(description);
                                    addsItem.setBeds(beds);
                                    addsItem.setBaths(bath);
                                    addsItem.setArea(area);
                                    addsItem.setAddress(address);
                                    addsItem.setImage(photo);
                                    addsItem.setPrice(String.valueOf(price));
                                    addsItem.setLatitude(lat);
                                    addsItem.setLogitude(lng);
                                    addsItem.setStyle(style);
                                    addsItem.setProperty_type(property_type);
                                    addsItem.setMlsID(mlsId);
                                    addsItem.setCountry(country);
                                    addsItem.setYearBuilt(built);
                                    addsItem.setLotSize(lot_size);
                                    dataItem.add(addsItem);

                                    ArrayList<AddsItem> propertyData = dbHandler.findItems();

                                    int pID = propertyData.get(propertyData.size()-1).getId();

                                    for (int z = 0; z < photoObj.length(); z++) {

                                        allPhotos.add(photoObj.getString(z));
                                        Images images = new Images();
                                        images.setImage(photoObj.getString(z));
                                        images.setPropertyID(pID);
                                        photoDBHandler.addImage(images);

                                    }
                                }

                                Log.e("object", firstEvent.toString());
                                Log.e("data", data.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        addAdapter = new AddAdapter(HomeValueActivity.this, dataItem);
                        listView.setAdapter(addAdapter);
                        if (dataItem.isEmpty()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeValueActivity.this);
                            builder.setTitle("Sorry!");
                            builder.setMessage("The house you are looking for, with such information is not available.");
                            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        searchInput.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                addAdapter.getFilter().filter(s.toString());

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                        customDialog.HideDialog();
                        apiRun = true;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("fail object", "yaha ata ha");
                        Toast.makeText(HomeValueActivity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e("fail aray", "yaha ata ha");
                        Toast.makeText(HomeValueActivity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e("fail", "yaha ata ha");
                        Toast.makeText(HomeValueActivity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        super.onSuccess(statusCode, headers, responseString);
                        Log.e("responseString", "yaha ata ha");
                        Toast.makeText(HomeValueActivity.this, "Something went wrong!!", Toast.LENGTH_LONG).show();
                        customDialog.HideDialog();
                    }
                }


        );
    }

    public static String convertArrayToString(ArrayList<String> array){
        String str = "" , strSeparator="";

        for (int i = 0;i<array.size(); i++) {
            str = str+array.get(i);
            // Do not append comma at the end of last element
            if(i<array.size()-1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {


                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                //Toast.makeText(this,"Inside ELSE Permission Check",Toast.LENGTH_SHORT).show();
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent map = new Intent(HomeValueActivity.this, MapsActivity.class);
                    startActivity(map);
                } else {

                    Toast.makeText(HomeValueActivity.this, "Sorry!!! Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }




}


