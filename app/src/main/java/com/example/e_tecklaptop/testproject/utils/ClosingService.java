package com.example.e_tecklaptop.testproject.utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.e_tecklaptop.testproject.SearchActivity;
import com.example.e_tecklaptop.testproject.database.MyDBHandler;
import com.example.e_tecklaptop.testproject.database.PhotoDBHandler;

/**
 * Created by E-Teck Laptop on 7/27/2017.
 */

public class ClosingService extends Service {

    private MyDBHandler dbHandler;
    private PhotoDBHandler photoDBHandler;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        Log.e("appclose", " you app is closed");
        SharedPreferences ApiPref = getSharedPreferences("ApiPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ApiPref.edit();
        editor.putBoolean("ApiRun",false);
        editor.commit();
        dbHandler = new MyDBHandler(ClosingService.this, null, null, 1);
        photoDBHandler = new PhotoDBHandler(ClosingService.this, null, null, 1);
        dbHandler.clearDatabase();
        photoDBHandler.clearDatabase();
        stopSelf();
    }

}
