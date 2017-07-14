package com.example.e_tecklaptop.testproject;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.e_tecklaptop.testproject.model.MyLatLng;
import com.example.e_tecklaptop.testproject.utils.Location;

import java.util.ArrayList;
import java.util.List;

public class FlatFeeCalculator extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    TextView minValue , maxValue , realValueTV , SellerTcTV , SellerRSTV , SellerSaveTV , BuyerTcTV , BuyerRSTv , BuyerSaveTV , totalComissionTV , tc_RsTV , tc_saveTV ;
    ImageView closeButton;
    Integer realValue , sellerTc , sellerRs , sellerSave , buyerTc , buyerRs , buyerSave , totalCom , tc_rs , tc_save ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_free_calculate);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_flat_free);

        RegisterViews();
        setListers();




   //     Log.e("latlng",String.valueOf(loc.getLocations()));

        final SeekBar sk= (SeekBar) findViewById(R.id.seekBar);

        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int position=0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
                if(position<=0)
                {
                    position=0;
                    sk.setProgress(position);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
                position = progress;

                if(position < 150000){
                    position = 150000;
                }

                if(position < 4500 ) {
                    sellerTc = 4500;
                }else{
                    sellerTc = (int) (position * 3) / 100;
                }
                if(position < 4500) {
                    buyerTc = 4500;
                }else{
                    buyerTc = (int) (position * 3) / 100;

                }

                if(position < 850000){
                    sellerRs = 3999;
                }else{
                    sellerRs = 5999;
                }
                if(position < 3750) {
                   buyerRs =  3750;

                }else{
                    buyerRs = (int) (position * 2.5) / 100;
                }

                totalCom = buyerTc + sellerTc ;

                tc_rs = sellerRs + buyerRs ;

                sellerSave = sellerTc - sellerRs ;

                buyerSave = buyerTc - buyerRs ;

                tc_save = sellerSave + buyerSave ;

                int finalValue = position;
                if(finalValue >= 3000000){
                    finalValue = 3000000;
                }

                realValueTV.setText("$ "+String.valueOf(finalValue));
                SellerTcTV.setText("$ "+String.valueOf(sellerTc));
                SellerRSTV.setText("$ "+String.valueOf(sellerRs));
                SellerSaveTV.setText("$ "+String.valueOf(sellerSave));
                BuyerTcTV.setText("$ "+String.valueOf(buyerTc));
                BuyerRSTv.setText("$ "+String.valueOf(buyerRs));
                BuyerSaveTV.setText("$ "+String.valueOf(buyerSave));
                totalComissionTV.setText("$ "+String.valueOf(totalCom));
                tc_RsTV.setText("$ "+String.valueOf(tc_rs));
                tc_saveTV.setText("$ "+String.valueOf(tc_save));
                seekBar.setMax(3000000);
            }
        });


    }

    @Override
    protected void onResume(){
        super.onResume();
        getInitialValues();
    }

    private void getInitialValues(){

        try {
            realValue = Integer.valueOf(String.valueOf(realValueTV.getText()));
            sellerTc = Integer.valueOf(String.valueOf(SellerTcTV.getText().toString()));
            sellerRs = Integer.valueOf(String.valueOf(SellerRSTV.getText().toString()));
            sellerSave = Integer.valueOf(String.valueOf(SellerSaveTV.getText().toString()));
            buyerTc = Integer.valueOf(String.valueOf(BuyerTcTV.getText().toString()));
            buyerRs = Integer.valueOf(String.valueOf(BuyerRSTv.getText().toString()));
            buyerSave = Integer.valueOf(String.valueOf(BuyerSaveTV.getText().toString()));
            totalCom = Integer.valueOf(String.valueOf(totalComissionTV.getText().toString()));
            tc_rs = Integer.valueOf(String.valueOf(tc_RsTV.getText().toString()));
            tc_save = Integer.valueOf(String.valueOf(tc_saveTV.getText().toString()));
        }catch (Exception e){
            e.getMessage();
        }

    }

    private void RegisterViews(){
        minValue = (TextView) findViewById(R.id.minValue);
        maxValue = (TextView) findViewById(R.id.maxValue);
        realValueTV = (TextView) findViewById(R.id.realValue);
        SellerTcTV = (TextView) findViewById(R.id.seller_tc);
        SellerRSTV = (TextView) findViewById(R.id.seller_rs);
        SellerSaveTV = (TextView) findViewById(R.id.seller_save);
        BuyerTcTV = (TextView) findViewById(R.id.buyer_tc);
        BuyerRSTv = (TextView) findViewById(R.id.buyer_rs);
        BuyerSaveTV = (TextView) findViewById(R.id.buyer_save);
        totalComissionTV = (TextView) findViewById(R.id.tc);
        tc_RsTV = (TextView) findViewById(R.id.tc_rezService);
        tc_saveTV = (TextView) findViewById(R.id.tc_save);
        closeButton = (ImageView) findViewById(R.id.close_button);
    }

    private void setListers(){
        closeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_button:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
