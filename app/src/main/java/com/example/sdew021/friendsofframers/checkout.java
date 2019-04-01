package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkout extends AppCompatActivity {
    private TextView nameView,emailView,addressView,phoneView,cropView,stockView,priceView,continueBtn;
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
        continueBtn=findViewById(R.id.continueBtn);
        mDatabaseReferenceFarmer= FirebaseDatabase.getInstance().getReference().child("Users").
                child("Farmer").child(UserFarmers.farmerId);
        Log.i("cropName, farmerid", UserFarmerActivity.clickedCropName+" "+ UserFarmers.farmerId);
        mDatabaseReferenceFarmer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameView.setText(dataSnapshot.child("name").getValue(String.class));
                addressView.setText(dataSnapshot.child("currentAdd").getValue(String.class));
                phoneView.setText(dataSnapshot.child("contact").getValue(String.class));
                emailView.setText(dataSnapshot.child("email").getValue(String.class));
                cropView.setText(UserFarmerActivity.clickedCropName);
                stockView.setText(dataSnapshot.child("crops").child(UserFarmerActivity.clickedCropName)
                        .child("stock").getValue(String.class)+"Kg");
                priceView.setText(dataSnapshot.child("crops").child(UserFarmerActivity.clickedCropName)
                        .child("price").getValue(String.class)+"per Kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(checkout.this,placeOrder.class));
            }
        });
    }
}
