package com.example.e_tecklaptop.testproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import java.io.File;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout userList;

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
    }

    private void RegisterViews(){
        userList = (RelativeLayout) findViewById(R.id.six);
    }

    private void setListeners(){
        userList.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.six:
                // to do
                Intent intent = new Intent(DashBoardActivity.this , UserListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
