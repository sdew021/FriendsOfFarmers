package com.example.sdew021.friendsofframers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private Context mCtx;
    private List<User> userList=new ArrayList<>();
    public static Map<String,User> gUserIdValue=new LinkedHashMap<>();

    public UserAdapter(Context mCtx) {
        this.mCtx=mCtx;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public TextView contact;
        public TextView address;
        public TextView name;
        public TextView price;
        public TextView delivery;
        public TextView quantity;

        public UserViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            contact=(TextView) itemView.findViewById(R.id.contact);
            address=(TextView) itemView.findViewById(R.id.address);
            price=(TextView) itemView.findViewById(R.id.price);
            delivery=(TextView) itemView.findViewById(R.id.delivery);
            quantity=(TextView) itemView.findViewById(R.id.quantity);
        }
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("Info","onCreateViewHolder called") ;
        View v =  LayoutInflater.from(mCtx).inflate(R.layout.custom_layout2, viewGroup, false);
        UserViewHolder UserViewHolder=new UserViewHolder(v);
        return UserViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder UserViewHolder, int i) {
        User user=userList.get(i);
        UserViewHolder.contact.setText(user.getContact());
        UserViewHolder.name.setText(user.getName());
        UserViewHolder.quantity.setText(user.getQuantity());
        UserViewHolder.price.setText(user.getPrice());
        UserViewHolder.delivery.setText(user.getDelivery());
        UserViewHolder.address.setText(user.getAddress());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void filterList( Map<String,User> filteredMap){
        userList=new ArrayList<>();
//        Log.i("Info","filterList called CropList Size:"+cropList.size());
        for(Map.Entry mapElement:filteredMap.entrySet()){
            User user=(User)mapElement.getValue();
            userList.add(user);
//            Log.w(TAG,"filter Crop:"+crop.getName());
        }
//        Log.i("Info","filterList called filterMap Size:"+filteredMap.size());
        notifyDataSetChanged();
    }

}