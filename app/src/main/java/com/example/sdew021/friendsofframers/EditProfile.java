package com.example.sdew021.friendsofframers;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.DefaultDatabaseErrorHandler;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    private TextView emailView,contactView,permanentAddView,currentAddView,nameView;
    private DatabaseReference mDatabaseRefernce;
    private StorageReference mStorageReference;
    private Button saveButton,selectImage;
    private static final int GALLERY_INTENT=2;
    private ImageView pImage;
    private ProgressBar progressBar;
    private String name;
    private Uri uri;
    private int checkImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        checkImage=0;
        emailView=findViewById(R.id.email);
        contactView=findViewById(R.id.contact);
        permanentAddView=findViewById(R.id.permanentAdd);
        currentAddView=findViewById(R.id.currentAdd);
        saveButton=findViewById(R.id.saveButton);
        selectImage=findViewById(R.id.selectImage);
        pImage=findViewById(R.id.pimage);
        progressBar=findViewById(R.id.progressBar);
//        nameView=findViewById(R.id.name);
        mDatabaseRefernce= FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Prateek/farmer1/Details");
        mDatabaseRefernce.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name=dataSnapshot.child("name").getValue(String.class);
                Upload upload= dataSnapshot.child("image").getValue(Upload.class);
                Picasso.get().load(upload.getmImageurl()).into(pImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,contact,currentAdd,permanentAdd;
                email=emailView.getText().toString();
                contact=contactView.getText().toString();
                currentAdd=currentAddView.getText().toString();
                permanentAdd=permanentAddView.getText().toString();
                int var=0;
                if(!email.isEmpty()){
                    if(isValidEmail(email)) {
                        mDatabaseRefernce.child("email").setValue(email);
                        var++;
                    }
                    else
                        Toast.makeText(EditProfile.this," Email Address invalid,Cannot be updated",Toast.LENGTH_SHORT).show();

                }
                if(!contact.isEmpty()){
                    if(contact.length()==10) {
                        mDatabaseRefernce.child("contact").setValue(contact);
                        var++;
                    }
                    else
                        Toast.makeText(EditProfile.this," Contact No. invalid,Cannot be updated",Toast.LENGTH_SHORT).show();

                }
                if(!currentAdd.isEmpty()){
                    mDatabaseRefernce.child("currentAdd").setValue(currentAdd);
                    var++;
                }
                if(!permanentAdd.isEmpty()){
                    mDatabaseRefernce.child("permanentAdd").setValue(permanentAdd);
                    var++;
                }
                if(checkImage==1){
                    uploadFile(uri);
                }
                if(var>0) {
                    Toast.makeText(EditProfile.this, "Valid Details Updated Succesfully", Toast.LENGTH_SHORT).show();
                }
                if(var==0&&checkImage!=1)
                    Toast.makeText(EditProfile.this, "Nothing Updated", Toast.LENGTH_SHORT).show();
                checkImage=0;

            }
        });
        mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://friends-of-farmers.appspot.com/Farmer_images");
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1,GALLERY_INTENT);
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT&&resultCode==RESULT_OK&&data!=null &&data.getData()!=null){
            uri=data.getData();
//            StorageReference filepath=mStorageReference.child("Farmer_images").child(uri.getLastPathSegment());
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(EditProfile.this,"Images uploaded sucessfully",Toast.LENGTH_SHORT).show();
//
//                }
//            });
            Picasso.get().load(uri).into(pImage);
            checkImage=1;
        }
    }

    String getFileExtension(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    void uploadFile(Uri uri){
        if(uri!=null){
            StorageReference filepath=mStorageReference.child(name+"."+getFileExtension(uri));
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfile.this,"Images uploaded sucessfully",Toast.LENGTH_SHORT).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },5000);

                    Upload upload=new Upload(name,taskSnapshot.getUploadSessionUri().toString());
                    mDatabaseRefernce.child("image").setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditProfile.this,"Error in Image Uploading"+e.getMessage(),Toast.LENGTH_SHORT).show();


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                }
            });

        }
        else{
            Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show();
        }
    }
}
