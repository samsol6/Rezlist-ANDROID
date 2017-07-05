package com.example.e_tecklaptop.testproject.utils;

import android.util.Log;

import com.example.e_tecklaptop.testproject.model.MyLatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E-Teck Laptop on 6/21/2017.
 */

public class Location {

    final List<MyLatLng> myLatLng = new ArrayList<MyLatLng>();
    private int count = 0;


    public void setMyLatLng(List<MyLatLng> myLatLng) {

        MyLatLng ll = myLatLng.get(count);
        this.myLatLng.add(count,ll) ;
        count++;

    }

    public void getLocations(){


    //    return myLatLng;
    }
}
