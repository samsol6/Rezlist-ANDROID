package com.example.e_tecklaptop.testproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends Activity {

    private ZXingScannerView zXingScannerView;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    TextView detailInfomation;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    AlertDialog alertDialog;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        detailInfomation = (TextView) findViewById(R.id.detailstext);
        preferences = getSharedPreferences("QR", Context.MODE_PRIVATE);
        editor = preferences.edit();

         if(checkLocationPermission()) {

            zXingScannerView = new ZXingScannerView(this);
            zXingScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
            zXingScannerView.setResultHandler(new ZxingScannerResultHangler());
            setContentView(zXingScannerView);
            zXingScannerView.startCamera();

        }
    }

    public void Scane(View view){

       /* if(checkLocationPermission()) {

            zXingScannerView = new ZXingScannerView(this);
            zXingScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
            zXingScannerView.setResultHandler(new ZxingScannerResultHangler());
            setContentView(zXingScannerView);
            zXingScannerView.startCamera();

        }*/


    }

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.CAMERA)) {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);


            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
            return false;
        } else {
            return true;
        }

    }
    public void bgClick(View v){
        alertDialog.dismiss();
        Intent in = new Intent(QRCodeActivity.this , SearchActivity.class);
        startActivity(in);
    }

   /* @Override
    public void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();
    }*/

    class ZxingScannerResultHangler implements ZXingScannerView.ResultHandler{

        @Override
        public void handleResult(Result result) {
            String data = result.getText();
            editor.putString("QRdata",data);
            editor.commit();
    //        Toast.makeText(QRCodeActivity.this , data , Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_qrcode);
            zXingScannerView.stopCamera();
          
            showAlert();
        }

        public void showAlert(){
            preferences = getSharedPreferences("QR", Context.MODE_PRIVATE);
            String details =  preferences.getString("QRdata","");
            dialog = new AlertDialog.Builder(QRCodeActivity.this);
            dialog.setMessage(details);
            alertDialog = dialog.create();
            alertDialog.show();
       //   detailInfomation.setText(details);
        }
    }

    @Override
    public void onBackPressed() {
        alertDialog.dismiss();
        super.onBackPressed();
        Intent in = new Intent(QRCodeActivity.this , SearchActivity.class);
        startActivity(in);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {

        Log.i("SOSTAG","Inside Request Permission Result");
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA);{
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                zXingScannerView = new ZXingScannerView(this);
                zXingScannerView.setResultHandler(new ZxingScannerResultHangler());
                setContentView(zXingScannerView);
                zXingScannerView.startCamera();
            } else {

                Toast.makeText(QRCodeActivity.this , "Sorry!!! Permission Denied", Toast.LENGTH_SHORT).show();

            }
        }
    }


}
