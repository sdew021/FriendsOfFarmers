package com.example.sdew021.friendsofframers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkout extends AppCompatActivity {
    private TextView nameView,emailView,addressView,phoneView,cropView,stockView,priceView;
    private DatabaseReference mDatabaserefrenceCrop,mDatabaseReferenceFarmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        nameView=findViewById(R.id.nameView);
        addressView=findViewById(R.id.addressView);
        phoneView=findViewById(R.id.phoneView);
        cropView=findViewById(R.id.cropView);
        stockView=findViewById(R.id.stockView);
        priceView=findViewById(R.id.priceView);
        emailView=findViewById(R.id.emailView);
        mDatabaseReferenceFarmer= FirebaseDatabase.getInstance().getReference().child("Users").
                child("Farmer").child(PankajFarmers.farmerId);
        Log.i("cropName, farmerid",PankajFarmerActivity.clickedCropName+" "+PankajFarmers.farmerId);
        mDatabaseReferenceFarmer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameView.setText(dataSnapshot.child("name").getValue(String.class));
                addressView.setText(dataSnapshot.child("currentAdd").getValue(String.class));
                phoneView.setText(dataSnapshot.child("contact").getValue(String.class));
                emailView.setText(dataSnapshot.child("email").getValue(String.class));
                cropView.setText(PankajFarmerActivity.clickedCropName);
                stockView.setText(dataSnapshot.child("crops").child(PankajFarmerActivity.clickedCropName)
                        .child("stock").getValue(String.class));
                priceView.setText(dataSnapshot.child("crops").child(PankajFarmerActivity.clickedCropName)
                        .child("price").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
