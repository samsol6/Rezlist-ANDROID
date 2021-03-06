package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeScanner extends Activity {

    private Button scanBtn;

    private TextView tvScanFormat, tvScanContent;

    private RelativeLayout llSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        scanBtn = (Button) findViewById(R.id.scan_button);

        tvScanFormat = (TextView) findViewById(R.id.format);

        tvScanContent = (TextView) findViewById(R.id.content);

        llSearch = (RelativeLayout) findViewById(R.id.llSearch);

      //  scanBtn.setOnClickListener(this);

        Scan();
    }

    private void Scan(){
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setPrompt("Scan a barcode or QRcode");

        integrator.setOrientationLocked(false);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);

        integrator.setBarcodeImageEnabled(true);

        integrator.initiateScan();

    }

       public void Rescan(View v) {

        // llSearch.setVisibility(View.GONE);

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setPrompt("Scan a barcode or QRcode");
        integrator.setOrientationLocked(false);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setBarcodeImageEnabled(true);

        integrator.initiateScan();

//        Use this for more customization

//        IntentIntegrator integrator = new IntentIntegrator(this);



//        integrator.setPrompt("Scan a barcode");

//        integrator.setCameraId(0);  // Use a specific camera of the device

//        integrator.setBeepEnabled(false);

//        integrator.setBarcodeImageEnabled(true);

//        integrator.initiateScan();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {

            if (result.getContents() == null) {

                //      llSearch.setVisibility(View.GONE);

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {

                //           llSearch.setVisibility(View.VISIBLE);

                tvScanContent.setText(result.getContents());


                tvScanFormat.setText(result.getFormatName());

            }

        } else {

            super.onActivityResult(requestCode, resultCode, data);

        }

    }

}