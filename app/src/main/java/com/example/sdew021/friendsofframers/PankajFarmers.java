package com.example.sdew021.friendsofframers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class PankajFarmers extends AppCompatActivity {

    private RecyclerView mNameView;
    public  static String farmerId;
    private Firebase mRef10;
    ArrayList<PankajFarmerDetails> farmerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pankaj_farmers);
        Firebase.setAndroidContext(this);
        farmerList =new ArrayList<>();
        final fname f =new fname(farmerList,this);
        mNameView=(RecyclerView) findViewById(R.id.nameView);

        mNameView.setAdapter(f);

        mRef10=new Firebase("https://friends-of-farmers.firebaseio.com/Users/Farmer/");


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mNameView.setLayoutManager(mLayoutManager);
        mNameView.setItemAnimator(new DefaultItemAnimator());

        mRef10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    PankajFarmerDetails pankajFarmerDetails=dataSnapshot1.getValue(PankajFarmerDetails.class);
                    pankajFarmerDetails.farmerId=dataSnapshot1.getKey().toString();
                    farmerList.add(pankajFarmerDetails);
                }
                f.notifyDataSetChanged();
                mRef10.removeEventListener(this);

                // String crop=dataSnapshot.getValue(String.class);
                //mNameView.setText(crop);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}