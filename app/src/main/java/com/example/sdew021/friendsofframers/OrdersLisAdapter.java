package com.example.sdew021.friendsofframers;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;

class OrdersLisAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<MyOrders> myOrdersArrayList = new ArrayList<>();
//    ArrayList<MyOrders> myOrdersArrayList = new ArrayList<>();
    LayoutInflater layoutInflater = null;

    public OrdersLisAdapter(Activity activity, ArrayList customListDataModelArray) {
        this.activity = activity;
        this.myOrdersArrayList = customListDataModelArray;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return myOrdersArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return myOrdersArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        ImageView imageview;
        TextView cropname, cropQuantity, cropPrice, cropAddress;

    }

    ViewHolder viewHolder = null;


    // this method  is called each time for arraylist data size.
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View vi = view;
        final int pos = position;
        if (vi == null) {

            viewHolder = new ViewHolder();

            vi = layoutInflater.inflate(R.layout.orders_new, null);
            viewHolder.cropname = (TextView) vi.findViewById(R.id.cropname);
            viewHolder.cropPrice = (TextView) vi.findViewById(R.id.cropPrice);
            viewHolder.cropQuantity = (TextView) vi.findViewById(R.id.cropquantity);
            viewHolder.cropAddress = (TextView) vi.findViewById(R.id.address);

            vi.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) vi.getTag();

//        Picasso.with(activity)
//                .load(cartArrayList.get(pos).getImage())
//                .placeholder(activity.getDrawable(R.drawable.cauli))
//                .error(activity.getDrawable(R.drawable.cauli))
//                .into(viewHolder.imageview);
            viewHolder.cropname.setText("Name: " + myOrdersArrayList.get(pos).getCropname());
            viewHolder.cropQuantity.setText("Quantity : " + myOrdersArrayList.get(pos).getQuantity());
            viewHolder.cropPrice.setText("Price : " + myOrdersArrayList.get(pos).getCropPrice());
            viewHolder.cropAddress.setText("Shipping Address : " + myOrdersArrayList.get(pos).getShippingAddress());

        }
        return vi;
    }
}