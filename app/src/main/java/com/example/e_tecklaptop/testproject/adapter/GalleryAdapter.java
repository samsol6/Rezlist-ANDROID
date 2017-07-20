package com.example.e_tecklaptop.testproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.e_tecklaptop.testproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by E-Teck Laptop on 7/19/2017.
 */

public class GalleryAdapter extends PagerAdapter {

    Context mcontext;
    LayoutInflater inflater;
    List<String> mlist;

    public GalleryAdapter(Context context , List<String> list){
        inflater = LayoutInflater.from(context);
        mcontext = context;
        mlist = list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.pager_adapter , container , false);
        ImageView  imageView = (ImageView) view.findViewById(R.id.galleryImage);

        Picasso.with(mcontext)
                .load(mlist.get(position)).fit()
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
