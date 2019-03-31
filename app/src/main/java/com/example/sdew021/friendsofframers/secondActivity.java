package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
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

import org.w3c.dom.Text;

public class secondActivity extends AppCompatActivity{
    MenuItem Profile;
    DatabaseReference databaseReference ;
    String user_id = "Saurabh Dewangan";
    EditText name,address,contact,permanentaddress,email;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageReference;
    private ImageView profImg;
    private String userId;
    private FirebaseUser consumer_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_userside);
        profImg = findViewById(R.id.profImg);
        name =findViewById(R.id.userName);
        address = findViewById(R.id.userAddress);
        permanentaddress =  findViewById(R.id.userPermanent);
        email = findViewById(R.id.userEmail);
        contact = findViewById(R.id.userContact);
        email.setEnabled(false);
        address.setEnabled(false);
        permanentaddress.setEnabled(false);
        contact.setEnabled(false);
        name.setEnabled(false);
        profImg.setEnabled(false);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Consumer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Toast.makeText(secondActivity.this, "1",Toast.LENGTH_SHORT).show();
                UserConsumer user = (UserConsumer) dataSnapshot.getValue(UserConsumer.class);
//                name.setEnabled(true);
//                email.setEnabled(true);
//                address.setEnabled(true);
//                permanentaddress.setEnabled(true);
//                contact.setEnabled(true);

                //Log.d("Second", user.toString());
                name.setText(user.getName());
                address.setText(user.getCurrentAdd());
                permanentaddress.setText(user.getPermanentAdd());
                contact.setText(user.getPhone());
                email.setText(user.getEmail());
                consumer_user=FirebaseAuth.getInstance().getCurrentUser();
                userId=consumer_user.getUid();

                mStorageReference= FirebaseStorage.getInstance().getReference();
                mStorageReference.child(userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(profImg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(secondActivity.this,"Cannot load image",Toast.LENGTH_SHORT);
                    }
                });
//                email.setEnabled(false);
//                address.setEnabled(false);
//                permanentaddress.setEnabled(false);
//                contact.setEnabled(false);
//                name.setEnabled(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(secondActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });






    }

    public void save_details(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child("Consumer").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef.child("currentAdd").setValue(address.getText().toString());
        myRef.child("name").setValue(name.getText().toString());
        myRef.child("permanentAddress").setValue(permanentaddress.getText().toString());
        myRef.child("phone").setValue(contact.getText().toString());
//        myRef.child("profImg").setValue(profImg.get)
        myRef.child("permanentAdd").setValue(permanentaddress.getText().toString());
        myRef.child("contact").setValue(contact.getText().toString());
        name.setEnabled(false);
        address.setEnabled(false);
        permanentaddress.setEnabled(false);
        contact.setEnabled(false);
        profImg.setEnabled(false);
    }

    public void edit(View view) {
        name.setEnabled(true);
        address.setEnabled(true);
        permanentaddress.setEnabled(true);
        contact.setEnabled(true);
        profImg.setEnabled(true);
    }
}

