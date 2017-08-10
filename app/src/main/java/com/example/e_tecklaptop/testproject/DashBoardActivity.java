package com.example.e_tecklaptop.testproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.e_tecklaptop.testproject.Api.ResInterface;
import com.example.e_tecklaptop.testproject.Api.SiginApi;
import com.example.e_tecklaptop.testproject.Api.SignoutApi;
import com.example.e_tecklaptop.testproject.Api.User;
import com.example.e_tecklaptop.testproject.utils.Const;
import com.example.e_tecklaptop.testproject.utils.CustomDialog;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout userList , logOut;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_dashboard);

        RegisterViews();
        setListeners();

        SharedPreferences preferences = getSharedPreferences("UserRole", Context.MODE_PRIVATE);
        String role = preferences.getString("role","");
        if(role.equals("admin")){
            userList.setVisibility(View.VISIBLE);
        }else {
            userList.setVisibility(View.GONE);
        }

        dialog = new CustomDialog(this);
    }

    private void RegisterViews(){
        userList = (RelativeLayout) findViewById(R.id.six);
        logOut = (RelativeLayout) findViewById(R.id.logout_cont);
    }

    private void setListeners(){
        userList.setOnClickListener(this);
        logOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.six:
                // to do
                Intent intent = new Intent(DashBoardActivity.this , UserListActivity.class);
                startActivity(intent);
                break;
            case R.id.logout_cont:
       //         dialog.ShowDialog();
       //         retrofitSignoutInitialization();
                SharedPreferences pref = getSharedPreferences("KeepMeLogIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor peditor = pref.edit();
                peditor.putString("email", "");
                peditor.commit();

                SharedPreferences signInPref = getSharedPreferences("checkSignIn", Context.MODE_PRIVATE);
                SharedPreferences.Editor signInPrefEditor = signInPref.edit();
                signInPrefEditor.putBoolean("signIn", false);
                signInPrefEditor.commit();

                Intent logout = new Intent(DashBoardActivity.this , SignInScreen.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
                finish();
                break;
        }
    }

    private void retrofitSignoutInitialization() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ResInterface ResInterface = retrofit.create(ResInterface.class);
        Call<SignoutApi> call = ResInterface.signoutUser();


        //asynchronous call
        call.enqueue(new Callback<SignoutApi>() {
            @Override
            public void onResponse(Call<SignoutApi> call, Response<SignoutApi> response) {
                try {
                    int code = response.code();
                    SignoutApi res = response.body();

                    String stat = res.getStatus().toString();
                    String message = res.getMessage();

                    dialog.HideDialog();
                    if (stat.equals("successful")) {

                        Intent logout = new Intent(DashBoardActivity.this , SignInScreen.class);
                        startActivity(logout);
                        finish();

                    } else {
                        Toast.makeText(DashBoardActivity.this, ""+message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    dialog.HideDialog();
                    Toast.makeText(DashBoardActivity.this, "Something went wrong , please try again", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignoutApi> call, Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Please check your connection and try again", Toast.LENGTH_LONG).show();
                dialog.HideDialog();
            }
        });

    }


}
