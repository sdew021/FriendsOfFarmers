package com.example.sdew021.friendsofframers;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomListAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Cart> cartArrayList = new ArrayList<>();
    //  ArrayList<MyOrders> myOrdersArrayList = new ArrayList<>();
    LayoutInflater layoutInflater = null;

    public CustomListAdapter(Activity activity, ArrayList customListDataModelArray) {
        this.activity = activity;
        this.cartArrayList = customListDataModelArray;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        ImageView imageview;
        TextView cropname, cropQuantity, cropPrice;

    }

    ViewHolder viewHolder = null;


    // this method  is called each time for arraylist data size.
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View vi = view;
        final int pos = position;
        if (vi == null) {

            viewHolder = new ViewHolder();

            vi = layoutInflater.inflate(R.layout.cart_new, null);
            viewHolder.imageview = (ImageView) vi.findViewById(R.id.imageView);
            viewHolder.cropname = (TextView) vi.findViewById(R.id.cropname);
            viewHolder.cropPrice = (TextView) vi.findViewById(R.id.cropPrice);
            viewHolder.cropQuantity = (TextView) vi.findViewById(R.id.cropQuantity);

            vi.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) vi.getTag();
        }
        viewHolder.cropname.setText(cartArrayList.get(pos).getName());
        viewHolder.cropQuantity.setText("Quantity : " + cartArrayList.get(pos).getQuantity());
        viewHolder.cropPrice.setText("Price : " + cartArrayList.get(pos).getPrice());
        if(cartArrayList.get(pos).getName().toLowerCase().compareTo("wheat")==0)
            viewHolder.imageview.setImageResource(R.drawable.sugarcane);


        return vi;
    }
}