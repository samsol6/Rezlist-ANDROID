package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_tecklaptop.testproject.database.MyDBHandler;
import com.example.e_tecklaptop.testproject.database.PhotoDBHandler;
import com.example.e_tecklaptop.testproject.utils.ClosingService;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;
    MyDBHandler dbHandler;
    PhotoDBHandler photoDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        /*imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.sign_in);*/
        dbHandler = new MyDBHandler(this, null, null, 3);
        photoDBHandler = new PhotoDBHandler(this, null, null, 2);


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
                    ClearData();
                    Intent i = new Intent(SplashScreen.this, SignInScreen.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }else{
                    ClearData();
                    Intent i = new Intent(SplashScreen.this, SearchActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                }
       //         startService(new Intent(getBaseContext(), ClosingService.class));

                // close this activity
         //       finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void ClearData(){
        SharedPreferences ApiPref = getSharedPreferences("ApiPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ApiPref.edit();
        editor.putBoolean("ApiRun",false);
        editor.commit();
        dbHandler.clearDatabase();
        photoDBHandler.clearDatabase();
    }

}
