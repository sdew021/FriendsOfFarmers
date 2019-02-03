package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    int flag=0;
    EditText email , password;
    String Email;
    Button login1,login2,login3;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout mylayout = (ConstraintLayout) findViewById(R.id.farmerT);
        AnimationDrawable animationDrawable=(AnimationDrawable) mylayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login1=findViewById(R.id.login1);
        login2=findViewById(R.id.login2);
        login3=findViewById(R.id.login3);
        Email=email.getText().toString().trim();

        login3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegister();
            }
        });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                if(flag==0){
                    gotoLogin1();
                }
            }
        });

        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();

            }
        });

    }

    public void gotoLogin1(){
         Intent intent1 = new Intent(this,farmerActivity.class);
         startActivity(intent1);
    }

    public void gotoRegister(){
        Intent intent1 = new Intent(this, registerActivity.class);
        startActivity(intent1);
    }

    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isShort(EditText text){
        int length = text.length();
        if(length<8)
            return true;
        else
            return false;
    }
    void checkDataEntered(){
        flag=0;
        if(!isEmail(email)) {
            flag = 1;
            email.setError("Enter Valid Email");
        }
        if(isShort(password)){
            flag=1;
            password.setError("Enter Correct Password");
        }
    }
}