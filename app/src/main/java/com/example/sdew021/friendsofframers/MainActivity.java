package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    int flag=0;
    EditText email , Password;
    String Email,password;
    Button login1,login2,login3,forgotpassword;
    String userrole;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout mylayout = (ConstraintLayout) findViewById(R.id.farmerT);
        AnimationDrawable animationDrawable=(AnimationDrawable) mylayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        email=(EditText)findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);
        login1=(Button) findViewById(R.id.login1);
        login2=(Button) findViewById(R.id.login1);
        login3=findViewById(R.id.login3);
        Email=email.getText().toString().trim();
        password = Password.getText().toString().trim();
        forgotpassword = (Button) findViewById(R.id.button2);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();


        if(user != null){
            FirebaseAuth.getInstance().signOut();
            /*firebaseAuth.signOut();*/
            /*startActivity(new Intent(MainActivity.this, farmerActivity.class));*/
        }
        login3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString().trim();
                password = Password.getText().toString().trim();

                firebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Farmer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role");
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String role = dataSnapshot.getValue(String.class);
                                    if(validatee(Email,password) && role!=null )
                                    {
                                        if(role.compareTo("farmer") ==0 )
                                        startActivity(new Intent(MainActivity.this, FarmerPage1.class));
                                    }
                                    if(validatee(Email,password) && role == null)
                                    {
                                        DatabaseReference mRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Consumer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role");
                                        mRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String role1 = dataSnapshot.getValue(String.class);
                                                if(role1.compareTo("consumer") !=0 )
                                                startActivity(new Intent(MainActivity.this, ConsumerActivity.class));
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        startActivity(new Intent(MainActivity.this, ConsumerActivity.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                        else if(Email.isEmpty()||password.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /*login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString().trim();
                password = Password.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Farmer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role");
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String role = dataSnapshot.getValue(String.class);
                                    if(validatee(Email,password) && role.compareTo("farmer")!=0)
                                    {
                                        startActivity(new Intent(MainActivity.this, ConsumerActivity.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }
                        else if(Email.isEmpty()||password.isEmpty())
                        {
                            Toast.makeText(MainActivity.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });*/
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Change Password",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PasswordActivity.class));
            }
        });
    }
    private Boolean validatee(String name,String password){
        Boolean result = false;
        if(name.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    public void setRole(String r){
        userrole = r;
    }
    public void gotoRegister(){
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        Intent intent1 = new Intent(this, registerActivity.class);
        startActivity(intent1);
    }
}