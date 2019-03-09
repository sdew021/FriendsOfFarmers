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
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static android.view.View.combineMeasuredStates;

public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.FarmerViewHolder> {
    private Context mCtx;
    public static List<Crop> gCropList=new ArrayList<>();
    public static List<String>gCropIds=new ArrayList<>();
    private List<Crop> cropList;
    private List<String> cropIds=new ArrayList<String>();
    private OnItemCLickListener mListner;
    private DatabaseReference mDatabseRefernce;
    private ChildEventListener mChildEventListner;

    public interface OnItemCLickListener{
        void   onItemClick(Crop item);
    }

    public void setOnItemClickListener(OnItemCLickListener listener){
        mListner=listener;
    }

    public FarmerAdapter(final Context mCtx,DatabaseReference ref) {
        this.mCtx=mCtx;
        mDatabseRefernce=ref;

        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                Crop crop=dataSnapshot.getValue(Crop.class);
                if (crop.getName().toLowerCase().compareTo("wheat") == 0) {
                    crop.Crop_image=R.drawable.wheat;

                } else if (crop.getName().toLowerCase().compareTo("corn") == 0) {
                    crop.Crop_image=R.drawable.corn;

                } else if (crop.getName().toLowerCase().compareTo("sugarcane") == 0) {
                    crop.Crop_image=R.drawable.sugarcane;

                } else {
                    crop.Crop_image=R.drawable.rice;

                }
                FarmerAdapter.gCropIds.add(dataSnapshot.getKey());
                FarmerAdapter.gCropList.add(crop);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                String cropKey= dataSnapshot.getKey();
                Crop newCrop=dataSnapshot.getValue(Crop.class);
                int cropIndex=FarmerAdapter.gCropIds.indexOf(dataSnapshot.getKey());
                if(cropIndex>-1){
                    FarmerAdapter.gCropList.set(cropIndex,newCrop);
                }
                else{
                    Log.w(TAG, "onChildChanged:unknown_child:" + cropKey);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"onChildRemoved:"+dataSnapshot.getKey());
                String cropKey=dataSnapshot.getKey();
                int cropIndex=FarmerAdapter.gCropIds.indexOf(cropKey);
                if(cropIndex>-1){
                    FarmerAdapter.gCropList.remove(cropIndex);
                    FarmerAdapter.gCropIds.remove(cropIndex);
                }
                else{
                    Log.w(TAG,"onChildRemoved:unknown_child:"+cropKey);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                Crop movedCrop=dataSnapshot.getValue(Crop.class);
                String cropKey=dataSnapshot.getKey();
                int cropIndex=cropIds.indexOf(cropKey);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(mCtx, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();

            }
        };

        ref.addChildEventListener(childEventListener);
        mChildEventListner=childEventListener;
        this.cropList=gCropList;
        this.cropIds=gCropIds;
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
        String porders="Pending Orders:"+crop.getPorders();
        farmerViewHolder.Stock.setText(stock);
        farmerViewHolder.Porders.setText(porders);
        farmerViewHolder.Name.setText(crop.getName());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public void cleanupListener(){
        if(mChildEventListner!=null){
            mDatabseRefernce.removeEventListener(mChildEventListner);
        }
    }

    public void filterList(List<Crop> filterList,List<Integer> index){
        cropList=filterList;
        List<String> newCropIds=new ArrayList<>();
        for(Integer idx:index){
            newCropIds.add(gCropIds.get(idx));
        }
        cropIds=newCropIds;
        notifyDataSetChanged();
    }
}
