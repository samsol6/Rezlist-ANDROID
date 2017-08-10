package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.Api.ResInterface;
import com.example.e_tecklaptop.testproject.Api.SendEmailApi;
import com.example.e_tecklaptop.testproject.Api.SiginApi;
import com.example.e_tecklaptop.testproject.Api.User;
import com.example.e_tecklaptop.testproject.utils.Const;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListActivity extends Activity implements View.OnClickListener {

    ImageView imageView;
    CustomDialog customDialog;
    String RegEx_Email =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" ;
    EditText email_input;
    private Button emailSendButton;
    Dialog dialog;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        RegisterViews();
        setListeners();
        customDialog  = new CustomDialog(UserListActivity.this);
    }

    private void RegisterViews(){
        imageView = (ImageView) findViewById(R.id.file);
    }

    private void showEmailDialog(){
        dialog = new Dialog(UserListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.send_email_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        email_input = (EditText) dialog.findViewById(R.id.sendEmail_input);
        emailSendButton = (Button) dialog.findViewById(R.id.sendEmail_button);
        emailSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email_input.getText().toString().matches(RegEx_Email)){

                    Toast.makeText(UserListActivity.this,"Please enter valid email",Toast.LENGTH_LONG).show();

                }else{
                    email = email_input.getText().toString();
                    sendEmail();
                    customDialog.ShowDialog();

                }
            }
        });
        dialog.show();
    }

    private void setListeners(){
        imageView.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file:
                /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                startActivity(emailIntent);*/
                showEmailDialog();
                break;
        }
    }

    private void sendEmail(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ResInterface ResInterface = retrofit.create(ResInterface.class);
        Call<SendEmailApi> call = ResInterface.sendEmail(email);


        //asynchronous call
        call.enqueue(new Callback<SendEmailApi>() {
            @Override
            public void onResponse(Call<SendEmailApi> call, Response<SendEmailApi> response) {
                int code = response.code();
                SendEmailApi res = response.body();

                String result =  res.getResult().toString();
                dialog.dismiss();
                customDialog.HideDialog();
                if(result.equals("Successful")){

                    Toast.makeText(UserListActivity.this , "your email has been sent!" ,Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(UserListActivity.this , "Something went wrong, Email sending failed!" ,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SendEmailApi> call, Throwable t) {
                Toast.makeText(UserListActivity.this , "Something went wrong , please check your connection and try again" ,Toast.LENGTH_LONG).show();
                customDialog.HideDialog();
            }
        });

    }
}
