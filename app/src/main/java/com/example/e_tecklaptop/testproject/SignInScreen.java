package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.Api.ResInterface;
import com.example.e_tecklaptop.testproject.Api.SiginApi;
import com.example.e_tecklaptop.testproject.Api.User;
import com.example.e_tecklaptop.testproject.utils.Const;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInScreen extends Activity implements View.OnClickListener {


    Button siginButton;
    EditText email_input, password_input;
    String RegEx_Email = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";//"[A-Za-z][A-Za-z]*[@][A-Za-z][A-Za-z]*[.](com)";

    CustomDialog customDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  this.overridePendingTransition(R.anim.animation_enter,
        //          R.anim.animation_leave);
        setContentView(R.layout.sign_in);

        customDialog = new CustomDialog(SignInScreen.this);

        RegisterViews();
        SetListeners();


    }

    public void SignUp(View v) {
        //    Intent intent = new Intent(this , SignUpActivity.class);
        //    startActivity(intent);
        Intent intent = new Intent(SignInScreen.this, SignUpActivity.class);
        Bundle bndlanimation =
                ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation_enter, R.anim.animation_leave).toBundle();
        startActivity(intent, bndlanimation);
    }

    private void RegisterViews() {
        siginButton = (Button) findViewById(R.id.login_button);
        email_input = (EditText) findViewById(R.id.email);
        password_input = (EditText) findViewById(R.id.pass);
    }

    private void SetListeners() {
        siginButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button: {
                customDialog.ShowDialog();
                retrofitSignInInitialization();


            }
            break;
        }
    }


    private boolean Validate() {
        if (!email_input.getText().toString().matches(RegEx_Email)) {

            return false;
        } else {
            return true;
        }
    }

    private void retrofitSignInInitialization() {
        String email = "";
        String pass = "";
        if (!Validate()) {
            Toast.makeText(SignInScreen.this, "Please enter valid email", Toast.LENGTH_LONG).show();
            customDialog.HideDialog();
            return;

        } else {

            email = email_input.getText().toString();
            pass = password_input.getText().toString();
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ResInterface ResInterface = retrofit.create(ResInterface.class);
        Call<SiginApi> call = ResInterface.signInUser(pass, email);


        //asynchronous call
        call.enqueue(new Callback<SiginApi>() {
            @Override
            public void onResponse(Call<SiginApi> call, Response<SiginApi> response) {
                try {
                    int code = response.code();
                    SiginApi res = response.body();

                    String stat = res.getStatus().toString();
                    User user = res.getUser();
                    String role = user.getRole();
                    String email = user.getEmail();

                    SharedPreferences preferences = getSharedPreferences("UserRole", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("role", role);
                    editor.commit();


                    customDialog.HideDialog();
                    if (stat.equals("successful")) {
                        //       Intent intent = new Intent(SignInScreen.this , SearchActivity.class);
                        //       startActivity(intent);

                        Intent i = new Intent(SignInScreen.this, SearchActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                        SharedPreferences signInPref = getSharedPreferences("checkSignIn", Context.MODE_PRIVATE);
                        SharedPreferences.Editor signInPrefEditor = signInPref.edit();
                        signInPrefEditor.putBoolean("signIn", true);
                        signInPrefEditor.commit();

                        SharedPreferences pref = getSharedPreferences("KeepMeLogIn", Context.MODE_PRIVATE);
                        SharedPreferences.Editor peditor = pref.edit();
                        peditor.putString("email", email);
                        peditor.commit();


                    } else {
                        Toast.makeText(SignInScreen.this, "Please enter valid email or password!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    customDialog.HideDialog();
                    Toast.makeText(SignInScreen.this, "Something went wrong , please try again", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SiginApi> call, Throwable t) {
                Toast.makeText(SignInScreen.this, "Please check your connection and try again", Toast.LENGTH_LONG).show();
                customDialog.HideDialog();
            }
        });

    }

   /* @Override
    public void onResponse(final Call<CategoryJson> call, Response<CategoryJson> response) {
        int code = response.code();
        if (code == 200) {
            CategoryJson user = response.body();
            categoryList = user.getCategories();
            for (int i = 0; i < categoryList.size(); i++) {
                // categoryList.add(categoryList.get(i));
                Log.e("response", "Got the Category: " + categoryList.get(i).getCategoryName());//, Toast.LENGTH_LONG).show();
            }
            adapter = new ListViewAdapter(this, R.layout.catlistlayout, categoryList);
            listView.setAdapter(adapter);

        } else {
            //  Toast.makeText(this, "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
            Log.e("error",String.valueOf(code));
        }
    }

    @Override
    public void onFailure(Call<CategoryJson> call, Throwable t) {
        //   Toast.makeText(this, "Nope", Toast.LENGTH_LONG).show();
        Log.e("error","api error");

    }*/

    @Override
    public void onBackPressed() {
        SignInScreen.this.finish();
        super.onBackPressed();
        Intent intent = new Intent("com.example.e_tecklaptop.testproject");
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.sendBroadcast(intent);
    }
}