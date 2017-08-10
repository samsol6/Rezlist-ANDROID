package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailsActivity extends Activity implements View.OnClickListener {


    String address ,image ,area ,baths ,beds ,price , desc , styleS , propertyS , communityS , countryS , mlsS , lot_sizeS , allImages ;
    RelativeLayout StreetView , gallery;
    private ImageView imageTv , mapView;
    private TextView addressTv , areaTv , bathsTv , bedsTv , priceTv , house_address , house_description;
    private TextView Style , property , community , country , MLS , lot_size ;
    private String builtS;
    private TextView built;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        RegisterViews();
        setListeners();
        getData();

    }

    private void RegisterViews() {
        imageTv = (ImageView) findViewById(R.id.itemImage);
        addressTv = (TextView) findViewById(R.id.det_address);
        areaTv = (TextView) findViewById(R.id.det_sqFt);
        bathsTv = (TextView) findViewById(R.id.det_bath);
        bedsTv = (TextView) findViewById(R.id.det_beds);
        priceTv = (TextView) findViewById(R.id.det_price);
        house_address = (TextView) findViewById(R.id.house_address);
        house_description = (TextView) findViewById(R.id.house_description);
        StreetView = (RelativeLayout) findViewById(R.id.street_view);
        mapView = (ImageView) findViewById(R.id.mapView);
        gallery = (RelativeLayout) findViewById(R.id.gallery);
        Style = (TextView) findViewById(R.id.style_value);
        property = (TextView) findViewById(R.id.pro_value);
        community = (TextView) findViewById(R.id.com_value);
        country = (TextView) findViewById(R.id.con_value);
        MLS = (TextView) findViewById(R.id.mls_value);
        lot_size = (TextView) findViewById(R.id.lot_value);
        built = (TextView) findViewById(R.id.year_built);
    }

    private void setListeners() {
        StreetView.setOnClickListener(this);
        mapView.setOnClickListener(this);
        gallery.setOnClickListener(this);
    }

    private void getData(){
        SharedPreferences preferences = getSharedPreferences("ListDetails", Context.MODE_PRIVATE);
        address =  preferences.getString("address","");
        image = preferences.getString("image","");
        area = preferences.getString("area","");
        baths = preferences.getString("baths","");
        beds = preferences.getString("beds","");
        price = preferences.getString("price","");
        desc = preferences.getString("desc","");
        styleS =  preferences.getString("style","");
        propertyS = preferences.getString("property_type","");
        countryS =  preferences.getString("country","");
        mlsS =  preferences.getString("mlsID","");
        lot_sizeS =  preferences.getString("lotSize","");
        builtS = preferences.getString("built","");


        addressTv.setText(address);
        areaTv.setText(area);
        bathsTv.setText(baths);
        bedsTv.setText(beds);
        priceTv.setText("$"+price);
        house_address.setText(address);
        house_description.setText(desc);
        Style.setText(styleS);
        property.setText(propertyS);
        country.setText(countryS);
        MLS.setText(mlsS);
        built.setText(builtS);
        lot_size.setText(lot_sizeS);

        Picasso.with(DetailsActivity.this)
                .load(image).fit()
                .into(imageTv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.street_view:
                Intent intent = new Intent(DetailsActivity.this , ShowHouseOnMap.class);
                startActivity(intent);
                break;
            case R.id.mapView:
                Intent map = new Intent(DetailsActivity.this , ShowHouseOnMap.class);
                startActivity(map);
                break;
            case R.id.gallery:
                Intent gallery = new Intent(DetailsActivity.this , GalleryActvity.class);
                startActivity(gallery);
                break;

        }
    }
}
