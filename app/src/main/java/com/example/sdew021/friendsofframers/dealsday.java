package com.example.sdew021.friendsofframers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dealsday extends AppCompatActivity {


    private ImageView sugarcane,Dal,Wheat;
    private DatabaseReference mDatabaserefernce;
    private double minPrice;
    private String cropName,farmerName,cropPrice,stock;
    private int rating;
    private TextView cropNameView,farmername,rate,croprice,stoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        minPrice=Double.MAX_VALUE;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaydeals);
        sugarcane=findViewById(R.id.sugarcaneimage);
        cropNameView=findViewById(R.id.userAddress);
        farmername=findViewById(R.id.farmername);
        rate=findViewById(R.id.farmerrating);
        croprice=findViewById(R.id.price);
        stoc=findViewById(R.id.farmerstock);

        mDatabaserefernce= FirebaseDatabase.getInstance().getReference().child("User").child("Farmer");
        mDatabaserefernce.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    DataSnapshot cropSnapshot=dataSnapshot1.child("crops");
                    for(DataSnapshot cropSnapshot1:cropSnapshot.getChildren()){
                        double price=Double.parseDouble(cropSnapshot1.child("price").getValue(String.class));
                        if(price<=minPrice){
                            cropName=cropSnapshot.child("name").getValue(String.class);
                            farmerName=dataSnapshot1.child("name").getValue(String.class);
                            cropPrice=Double.toString(price);
                            minPrice=price;
                            stock=cropSnapshot1.child("stock").getValue(String.class);
                            rating=dataSnapshot1.child("rating").getValue(Integer.class);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        cropNameView.setText(cropName);
        croprice.setText(cropPrice);
        rate.setText(Integer.toString(rating));
        farmername.setText(farmerName);
        stoc.setText(stock);


        sugarcane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dealsday.this,farmerActivity.class));
            }
        });

    }

}
