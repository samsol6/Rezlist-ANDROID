package com.example.e_tecklaptop.testproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CalculateActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView closeButton;
    EditText minPrice_input , maxPrice_input , areaTv_input , bed_input;
    Button homeValueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_acitivty);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_flat_free);

        RegisterViews();
        setListers();

    }

    private void RegisterViews(){

        closeButton = (ImageView) findViewById(R.id.close_button);
        minPrice_input = (EditText) findViewById(R.id.minPriceTv_input);
        maxPrice_input = (EditText) findViewById(R.id.maxPriceTv_input);
        areaTv_input = (EditText) findViewById(R.id.areaTv_input);
        bed_input = (EditText) findViewById(R.id.bed_input);
        homeValueButton = (Button) findViewById(R.id.homeValueButton);
    }

    private void setListers(){
        closeButton.setOnClickListener(this);
        homeValueButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_button:
                onBackPressed();
                break;
            case R.id.homeValueButton:
                if(minPrice_input.getText().toString().equals("")){
                    minPrice_input.setError("Please enter Min Price");
                }else if(maxPrice_input.getText().toString().equals("")){
                    maxPrice_input.setError("Please enter Max Price");
                }else if(!(maxPrice_input.getText().toString().equals("")) && !(minPrice_input.getText().toString().equals(""))) {
                    SharedPreferences preferences = getSharedPreferences("MinMax", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("min", minPrice_input.getText().toString());
                    editor.putString("max", maxPrice_input.getText().toString());
                    editor.putString("beds", bed_input.getText().toString());
                    editor.putString("areaSqFt", areaTv_input.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(CalculateActivity.this, HomeValueActivity.class);
                    startActivity(intent);
                }else{
                    minPrice_input.setError("Please enter Min Price");
                    maxPrice_input.setError("Please enter Max Price");
                 //   Toast.makeText(CalculateActivity.this , "Please enter both Min and Max Prices" , Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
