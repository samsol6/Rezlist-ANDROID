package com.example.e_tecklaptop.testproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_tecklaptop.testproject.R;
import com.example.e_tecklaptop.testproject.model.AddsItem;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class AddAdapter extends BaseAdapter {

    List<AddsItem> itemsList;
    LayoutInflater inflater;
    Context context;

    public AddAdapter(Context context, List<AddsItem> itemsList) {
        this.itemsList = itemsList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemsList.indexOf(itemsList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.add_adapter, null);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.description = (TextView) convertView.findViewById(R.id.hdesc);
            holder.addImage = (ImageView) convertView.findViewById(R.id.AddImage);
            holder.addImage.setColorFilter(Color.BLACK , PorterDuff.Mode.LIGHTEN);

            convertView.setTag(holder);  // set all the views in convert view through tag.
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        AddsItem item = new AddsItem();
        item = (AddsItem) getItem(position);
        holder.address.setText(item.getTitle());
        holder.description.setText(item.getDescription());


        Picasso.with(context)
                .load(item.getImage()).fit()
                .into(holder.addImage);



        return convertView;

    }

    class ViewHolder {
        TextView address;
        TextView description;
        ImageView addImage;
    }
}
