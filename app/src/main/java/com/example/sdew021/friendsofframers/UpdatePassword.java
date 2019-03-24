package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePassword extends AppCompatActivity {
    EditText newPassowordView,oldPasswordView,confirmPasswordView;
    Button updateBtn;
    FirebaseUser user;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        oldPasswordView=findViewById(R.id.oldPassword);
        confirmPasswordView=findViewById(R.id.confirmPassword);
        newPassowordView = findViewById(R.id.newPassword);
        updateBtn = findViewById(R.id.btnUpdatePassword);
        user= FirebaseAuth.getInstance().getCurrentUser();
        String userId=user.getUid();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Farmer").child(userId);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword=oldPasswordView.getText().toString();
                final String confirmPassword=confirmPasswordView.getText().toString();
                if(oldPassword.compareTo(confirmPassword)==0) {
                    AuthCredential credential = EmailAuthProvider
                            .getCredential(user.getEmail(), oldPassword);
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final String newPass=newPassowordView.getText().toString();
                                        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mDatabaseReference.child("password").setValue(newPass);
                                                    Toast.makeText(UpdatePassword.this, "Password Update Succesfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(UpdatePassword.this,Profile_Activity.class));
                                                } else {
                                                    Toast.makeText(UpdatePassword.this, "Error password not updated", Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(UpdatePassword.this, "Password Incorrect", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                else{
                    confirmPasswordView.setError("Password do not match");
                    Toast.makeText(UpdatePassword.this, "Please Enter Same Passwords", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayout linearLayout =  findViewById(R.id.linearLayout1);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }
}
