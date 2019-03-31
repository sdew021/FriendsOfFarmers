package com.example.sdew021.friendsofframers;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class fname extends RecyclerView.Adapter<fname.MyViewHolder> {

    private List<PankajFarmerDetails> farmerList;
    private Context context;
    private StorageReference mStorageReference;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name_view;
        public ImageView farmerImageView;

        public MyViewHolder(View view) {
            super(view);
            name_view = (TextView) view.findViewById(R.id.name);
            farmerImageView=view.findViewById(R.id.farmer_image);


        }
    }


    public fname(List<PankajFarmerDetails> moviesList,Context context1) {
        this.farmerList = moviesList;
        this.context=context1;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fnames, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name_view.setText(farmerList.get(position).name);
        holder.name_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PankajFarmers.farmerId=farmerList.get(position).farmerId;
                context.startActivity(new Intent(context,PankajFarmerActivity.class));
            }
        });
        mStorageReference= FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://friends-of-farmers.appspot.com/Farmer_images/");
        mStorageReference.child("profilePic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(holder.farmerImageView);
            }
        });
    }

        @Override
    public int getItemCount() {
        return farmerList.size();
    }
}