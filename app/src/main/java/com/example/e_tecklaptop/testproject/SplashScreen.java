package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /*imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.sign_in);*/


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing sign_in screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                SharedPreferences pref = getSharedPreferences("KeepMeLogIn", Context.MODE_PRIVATE);
                String check = pref.getString("email", "");
                if(check.equals("")){
                    Intent i = new Intent(SplashScreen.this, SignInScreen.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(SplashScreen.this, SearchActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
