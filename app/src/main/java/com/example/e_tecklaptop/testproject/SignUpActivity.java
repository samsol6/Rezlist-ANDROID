package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.Api.ResInterface;
import com.example.e_tecklaptop.testproject.Api.SiginApi;
import com.example.e_tecklaptop.testproject.Api.SignUpApi;
import com.example.e_tecklaptop.testproject.utils.Const;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends Activity {

    EditText Email, Password, CPassword;

    String RegEx_Email = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//    String RegEx_Email = "[A-Za-z][A-Za-z]*[@][A-Za-z][A-Za-z]*[.](com)";

    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    this.overridePendingTransition(R.anim.animation_leave,
        //            R.anim.animation_enter);
        setContentView(R.layout.activity_sign_up);

        RegisterView();

        customDialog = new CustomDialog(SignUpActivity.this);


    }

    private void RegisterView() {
        Email = (EditText) findViewById(R.id.Remail);
        Password = (EditText) findViewById(R.id.Rpass);
        CPassword = (EditText) findViewById(R.id.cPass);
    }

    private boolean Validate() {
        if (!Email.getText().toString().matches(RegEx_Email)) {

            return false;
        } else {
            return true;
        }
    }

/*    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SignInScreen.class);
        Bundle bndlanimation =
        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_enter, R.anim.animation_leave).toBundle();
        startActivity(intent, bndlanimation);
    }*/

    private void retrofitSignUpInitialization() {
        String email = "";
        String pass = "";
        String cpass = "";
        if (!Validate()) {
            Toast.makeText(SignUpActivity.this, "Please enter valid email", Toast.LENGTH_LONG).show();
            customDialog.HideDialog();
            return;

        } else {

            email = Email.getText().toString();
            pass = Password.getText().toString();
            cpass = CPassword.getText().toString();

        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ResInterface ResInterface = retrofit.create(ResInterface.class);
        Call<SignUpApi> call = ResInterface.signupUser(email, pass, cpass);

        //asynchronous call
        call.enqueue(new Callback<SignUpApi>() {
            @Override
            public void onResponse(Call<SignUpApi> call, Response<SignUpApi> response) {
                int code = response.code();
                SignUpApi res = new SignUpApi();
                res = response.body();
                String stat = res.getStatus().toString();
                Log.e("status", stat.toString());
                customDialog.HideDialog();

                if (stat.equals("successful")) {
                    Intent intent = new Intent(SignUpActivity.this, SignInScreen.class);
                    startActivity(intent);
                    Toast.makeText(SignUpActivity.this, "The user has been created successfully!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Please enter valid email or password!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignUpApi> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                customDialog.HideDialog();
            }
        });

    }


    public void createUser(View view) {
        customDialog.ShowDialog();
        retrofitSignUpInitialization();
    }
}
