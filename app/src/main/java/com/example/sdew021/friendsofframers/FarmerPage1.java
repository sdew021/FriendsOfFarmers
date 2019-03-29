package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class FarmerPage1 extends AppCompatActivity {
    private Button btnCrop;
    private StorageReference mStorageReference;
    private ImageView profileImage;
    private TextView header;
    private DatabaseReference mDatabaseRefernce;
    private String userId;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_page1);
        btnCrop=findViewById(R.id.btncrop);
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerPage1.this,farmerActivity.class));
            }
        });
        mStorageReference= FirebaseStorage.getInstance().
                getReferenceFromUrl("gs://friends-of-farmers.appspot.com/Farmer_images/");
        mStorageReference.child("profilePic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                profileImage=findViewById(R.id.navProfileImage);
                Picasso.get().load(uri).fit().centerCrop().into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FarmerPage1.this,"Cannot load image",Toast.LENGTH_SHORT);
            }
        });
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();
        mDatabaseRefernce= FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Farmer").child(userId).child("name");
        final ValueEventListener dataListner =new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                header=findViewById(R.id.header);
                header.setText(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseRefernce.addValueEventListener(dataListner);
    }
}
