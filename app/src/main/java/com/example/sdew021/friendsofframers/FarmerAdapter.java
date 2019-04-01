/*
 *   Contributed by Prateek Sahu
 *   17CO130
 */


package com.example.sdew021.friendsofframers;


import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.combineMeasuredStates;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder> {
    private Context mCtx;
    public static Map<String,Crop> gCropIdValue=new LinkedHashMap<>();
    private List<Crop> cropList;
    private OnItemCLickListener mListner;

    public interface OnItemCLickListener{
        void   onItemClick(Crop item);
    }

    public void setOnItemClickListener(OnItemCLickListener listener){
        mListner=listener;
    }

    public FarmerAdapter(final Context mCtx,  List<Crop> cropList) {
        this.mCtx=mCtx;
        this.cropList=cropList;
        Log.w(TAG, "Adapter created cropList size:"+cropList.size());
    }

    public class FarmerViewHolder extends RecyclerView.ViewHolder{
        public ImageView CropImage;
        public TextView Name,Stock,Porders;

        public FarmerViewHolder(View itemView, final OnItemCLickListener listener) {
            super(itemView);
            Log.i("Info", "FarmerViewHolder called");
            CropImage = (ImageView) itemView.findViewById(R.id.crop_image);
            Name = (TextView) itemView.findViewById(R.id.name);
            Stock = (TextView) itemView.findViewById(R.id.stock);
            Porders = (TextView) itemView.findViewById(R.id.porders);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(cropList.get(position));
                        }
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("Info","onCreateViewHolder called") ;
        View v =  LayoutInflater.from(mCtx).inflate(R.layout.custom_layout, viewGroup, false);
        FarmerViewHolder farmerViewHolder=new FarmerViewHolder(v,mListner);
        return farmerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerViewHolder farmerViewHolder, int i) {
        Log.i("Info","onBindViewholder called");
        Crop crop=cropList.get(i);
        farmerViewHolder.CropImage.setImageResource(crop.Crop_image);
        String stock="Stock Available:"+crop.getStock();
        String porders="Pending Orders:"+crop.getPendingOrders();
        farmerViewHolder.Stock.setText(stock+" kg");
        farmerViewHolder.Porders.setText(porders);
        farmerViewHolder.Name.setText(crop.getName());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }


    public void filterList( Map<String,Crop> filteredMap){
        cropList=new ArrayList<>();
//        Log.i("Info","filterList called CropList Size:"+cropList.size());
        for(Map.Entry mapElement:filteredMap.entrySet()){
            Crop crop=(Crop)mapElement.getValue();
            cropList.add(crop);
//            Log.w(TAG,"filter Crop:"+crop.getName());
        }
//        Log.i("Info","filterList called filterMap Size:"+filteredMap.size());
        notifyDataSetChanged();
    }
}
