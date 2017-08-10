package com.example.e_tecklaptop.testproject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.e_tecklaptop.testproject.adapter.GalleryAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GalleryActvity extends Activity {

    List<String> list = new ArrayList<>();
    private String allImages;
    public static String strSeparator = "__,__";
    List<String> photoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_actvity);

        SharedPreferences preferences = getSharedPreferences("ListDetails", Context.MODE_PRIVATE);
        Set<String> set = preferences.getStringSet("photoList", null);
        allImages = preferences.getString("insertAllImages","");
   //     photoList = convertStringToArray(allImages);

        for (Iterator<String> it = set.iterator(); it.hasNext();){

            String photo = it.next();
            list.add(photo);

        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        GalleryAdapter galleryAdapter = new GalleryAdapter(this , list);
        viewPager.setAdapter(galleryAdapter);
    }

/*    public static List<String> convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        for(int i=0; i< arr.length ; i++){
            list.add(arr[i]);
        }
        return list;
    }*/
}
