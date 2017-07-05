package com.example.e_tecklaptop.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by E-Teck Laptop on 6/5/2017.
 */

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView profile, setting, location , search , notification_button , calculator_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_screen);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_support);

        RegisterView();
        setListeners();
    }


    private void RegisterView() {
        profile = (ImageView) findViewById(R.id.profile_button);
        search = (ImageView) findViewById(R.id.search_button);
        notification_button = (ImageView) findViewById(R.id.bell_button);
        calculator_button = (ImageView) findViewById(R.id.calculator_button);
        //       setting = (ImageView) findViewById(R.id.setting_button);


    }

    private void setListeners() {
        profile.setOnClickListener(this);
        search.setOnClickListener(this);
        notification_button.setOnClickListener(this);
        calculator_button.setOnClickListener(this);
        //       setting.setOnClickListener(this);
        //       location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.profile_button:
                Intent imageIntent = new Intent(SupportActivity.this, DashBoardActivity.class);
                startActivity(imageIntent);
                break;
            case R.id.search_button:
                Intent setting_intent = new Intent(SupportActivity.this, SearchActivity.class);
                startActivity(setting_intent);
                break;
            case R.id.bell_button:
                Intent bell_intent = new Intent(SupportActivity.this, NotificationSettingActivity.class);
                startActivity(bell_intent);
                break;
            case R.id.calculator_button:
                Intent cal_intent = new Intent(SupportActivity.this, CalculateActivity.class);
                startActivity(cal_intent);
                break;

        }
    }
}
