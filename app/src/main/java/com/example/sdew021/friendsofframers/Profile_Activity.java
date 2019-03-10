package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile_Activity extends AppCompatActivity {

    private TextView emailView,contactView,permanentAddView,currentAddView,nameView,editDetails;
    private DatabaseReference mDatabaseRefernce;
    private ImageView profileImage;
    private StorageReference mStorageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        emailView=findViewById(R.id.email);
        contactView=findViewById(R.id.contact);
        permanentAddView=findViewById(R.id.permanentAdd);
        currentAddView=findViewById(R.id.currentAdd);
        nameView=findViewById(R.id.name);
        editDetails=findViewById(R.id.editDetails);
        profileImage=findViewById(R.id.profileImage);
        mDatabaseRefernce= FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Prateek/farmer1/Details");
        final ValueEventListener dataListner =new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FarmerProfileDetails farmerProfileDetails = dataSnapshot.getValue(FarmerProfileDetails.class);
                nameView.setText(farmerProfileDetails.name);
                emailView.setText(farmerProfileDetails.email);
                contactView.setText(farmerProfileDetails.contact);
                currentAddView.setText((farmerProfileDetails.currentAdd));
                permanentAddView.setText(farmerProfileDetails.permanentAdd);
                mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://friends-of-farmers.appspot.com/Farmer_images/");
                mStorageReference.child("profilePic.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(profileImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Profile_Activity.this,"Cannot load image",Toast.LENGTH_SHORT);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDatabaseRefernce.addValueEventListener(dataListner);
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Profile_Activity.this, EditProfile.class);
                startActivity(intent1);
            }
        });

    }
}
