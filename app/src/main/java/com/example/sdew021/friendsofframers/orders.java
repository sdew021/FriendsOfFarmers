/*
 *   Contributed by Rahul Kumar
 *   17CO133
 */


package com.example.sdew021.friendsofframers;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class orders extends AppCompatActivity {

    ListView lv;
    FirebaseListAdapter adapter;
    ArrayList<MyOrders> myOrdersArrayList;
    orders activity;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.myorders);

        String userId  = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myOrdersArrayList = new ArrayList<MyOrders>();
        activity = this;
        lv = (ListView) findViewById(R.id.listViewOrders);
        DatabaseReference myRef = FirebaseDatabase.getInstance()
                .getReference().child("Users").child("Consumer").child(userId).child("orders");
//
//        Cart c1 = new Cart("Sugar","100","50");
//        myRef.child("c1").setValue(c1);

        /*LinearLayout myLayout = (LinearLayout) findViewById(R.id.linLay);
        AnimationDrawable animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("TA" + dataSnapshot.toString());
                if (dataSnapshot.getChildrenCount() <= 0) {
                    // No items in cart
                } else {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        System.out.println(childDataSnapshot.getValue().toString());
                        MyOrders M = childDataSnapshot.getValue(MyOrders.class);
                        System.out.println(M);
                        myOrdersArrayList.add(M);
                    }
                    System.out.println("2 " + myOrdersArrayList.size());
                    lv.setAdapter(new OrdersLisAdapter(activity, myOrdersArrayList));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println(error.getMessage());
                // Failed to read value
            }
        });

        //lv.setAdapter(adapter);


    }


}
