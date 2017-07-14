package com.example.e_tecklaptop.testproject.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_tecklaptop.testproject.R;
import com.example.e_tecklaptop.testproject.model.AddsItem;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddAdapter extends BaseAdapter implements Filterable{

    LayoutInflater inflater;
    Context context;
    ArrayList<AddsItem> itemsList;
    private ArrayList<AddsItem> arraylist;
    private ValueFilter  valueFilter;


    public AddAdapter(Context context, ArrayList<AddsItem> itemsList) {
        this.itemsList = itemsList;
        arraylist =  itemsList;
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
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.addImage.setColorFilter(Color.BLACK , PorterDuff.Mode.LIGHTEN);

            convertView.setTag(holder);  // set all the views in convert view through tag.
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        AddsItem item = new AddsItem();
        item = (AddsItem) getItem(position);
        holder.address.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf(item.getPrice()) + " $");

        Picasso.with(context)
                .load(item.getImage()).fit()
                .into(holder.addImage);



        return convertView;

    }


    @Override
    public Filter getFilter() {
        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }
    private class ValueFilter extends Filter {

        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results=new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<AddsItem> filterList=new ArrayList<AddsItem>();
                for(int i=0;i<arraylist.size();i++){
                    if((arraylist.get(i).getTitle().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        AddsItem item = new AddsItem();
                        item.setTitle(arraylist.get(i).getTitle());
                        item.setDescription(arraylist.get(i).getDescription());
                        item.setImage(arraylist.get(i).getImage());
                        item.setPrice(arraylist.get(i).getPrice());
                        filterList.add(item);
                    }
                }
                results.count=filterList.size();
                results.values=filterList;
            }else{
                results.count=arraylist.size();
                results.values=arraylist;
            }
            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            itemsList=(ArrayList<AddsItem>) results.values;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView address;
        TextView description;
        ImageView addImage;
        TextView price;
    }
}
