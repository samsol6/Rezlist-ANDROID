package com.example.e_tecklaptop.testproject.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

import java.util.ArrayList;

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
       //     holder.description = (TextView) convertView.findViewById(R.id.hdesc);
            holder.addImage = (ImageView) convertView.findViewById(R.id.AddImage);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.beds = (TextView) convertView.findViewById(R.id.beds);
            holder.baths = (TextView) convertView.findViewById(R.id.bath);
            holder.area = (TextView) convertView.findViewById(R.id.sqFt);
            holder.addImage.setColorFilter(Color.BLACK , PorterDuff.Mode.LIGHTEN);

            convertView.setTag(holder);  // set all the views in convert view through tag.
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        AddsItem item = new AddsItem();
        item = (AddsItem) getItem(position);
        holder.address.setText(item.getAddress());
   //     holder.description.setText(item.getDescription());
        holder.price.setText(String.valueOf("$"+item.getPrice()));
        holder.beds.setText(item.getBeds());
        holder.baths.setText(item.getBaths());
        holder.area.setText(item.getArea());

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
                    if((arraylist.get(i).getAddress().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        AddsItem item = new AddsItem();
                        item.setAddress(arraylist.get(i).getAddress());
                        item.setDescription(arraylist.get(i).getDescription());
                        item.setImage(arraylist.get(i).getImage());
                        item.setPrice(arraylist.get(i).getPrice());
                        item.setBeds(arraylist.get(i).getBeds());
                        item.setBaths(arraylist.get(i).getBaths());
                        item.setArea(arraylist.get(i).getArea());
                        item.setStyle(arraylist.get(i).getStyle());
                        item.setProperty_type(arraylist.get(i).getProperty_type());
                        item.setCountry(arraylist.get(i).getCountry());
                        item.setMlsID(arraylist.get(i).getMlsID());
                        item.setYearBuilt(arraylist.get(i).getYearBuilt());
                        item.setLotSize(arraylist.get(i).getLotSize());
                        item.setLatitude(arraylist.get(i).getLatitude());
                        item.setLogitude(arraylist.get(i).getLogitude());
                        item.setAllPhotos(arraylist.get(i).getAllPhotos());
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
        TextView beds;
        TextView baths;
        TextView area;
    }
}
