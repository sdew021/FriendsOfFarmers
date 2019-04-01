/*
 *   Contributed by Prateek Sahu
 *   17CO130
 */

package com.example.sdew021.friendsofframers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FarmerRating extends AppCompatActivity {


    private DatabaseReference mDatabaseReference;
    private Button ratingBtn;
    private EditText ratingView;
    int userRating,rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_rating);
        ratingBtn=findViewById(R.id.rateBtn);
        ratingView=findViewById(R.id.ratingView);

        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Farmer").child(UserFarmers.farmerId);
        ratingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingView.getText().toString().compareTo("") != 0) {
                    userRating = Integer.parseInt(ratingView.getText().toString());
                    if (userRating <= 5 && userRating >= 1) {

                        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                rating = dataSnapshot.child("rating").getValue(Integer.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        rating=(userRating+rating)/2;
                        mDatabaseReference.child("rating").setValue(rating);
                        Toast.makeText(FarmerRating.this, "Your rating has been successfully submitted", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(FarmerRating.this, "Rating should be between 1 10 5", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(FarmerRating.this, "Rating cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
