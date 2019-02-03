package com.example.sdew021.friendsofframers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder> {
    private Context mCtx;
    private List<Crop> cropList;
    private OnItemCLickListener mListner;

    public interface OnItemCLickListener{
        void   onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemCLickListener listener){
        mListner=listener;
    }

    public FarmerAdapter(Context mCtx,List<Crop> cropList) {
        this.mCtx=mCtx;
        this.cropList = cropList;
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
                            listener.onItemClick(position);
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
        farmerViewHolder.CropImage.setImageResource(crop.getCrop_image());
        farmerViewHolder.Stock.setText(crop.getStock());
        farmerViewHolder.Name.setText(crop.getName());
        farmerViewHolder.Porders.setText(crop.getPorders());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void filterList(List<Crop> filterList){
        cropList=filterList;
        notifyDataSetChanged();
    }
}
