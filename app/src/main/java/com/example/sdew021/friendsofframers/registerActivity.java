/*
 *   Contributed by Saurabh Dewangan
 *   17CO140
 */

package com.example.sdew021.friendsofframers;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.Random;

import static android.os.Build.VERSION_CODES.M;

public class registerActivity  extends AppCompatActivity {
    int flag = 0;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText8;
    EditText editText9;
    String email1,password1;
    Button button;
    Button button2;
    Button button3;
    EditText otp;
    Button otpbutton;
    RadioGroup rg;
    RadioButton rb;
    String userID;
    private static final int GALLERY_INTENT=2;
    private Uri uri;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference current_user_db;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    Random r = new Random();
    int low = 1000;
    int high = 9999;
    int result = r.nextInt(high-low)+low;
    String msg = Integer.toString(result);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);



        if(ContextCompat.checkSelfPermission(registerActivity.this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(registerActivity.this,
                    new String[]{Manifest.permission.SEND_SMS},1);
        }

        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText6 = findViewById(R.id.editText6);
        editText8 = findViewById(R.id.editText8);
        editText9 = findViewById(R.id.editText9);
        otp = findViewById(R.id.otp);
        otpbutton = findViewById(R.id.otpbutton);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        rg = findViewById(R.id.rbgroup);
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user =mAuth.getCurrentUser();
        if(user != null){
            userID = user.getUid();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");


        LinearLayout myLayout = (LinearLayout) findViewById(R.id.mainContainer);
        AnimationDrawable animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        otpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editText4.getText().toString();



                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phone,null,msg,null,null);
                Toast.makeText(getApplicationContext(),"Message Sent Successfully!",Toast.LENGTH_LONG).show();
                if(ContextCompat.checkSelfPermission(registerActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(registerActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkDataEntered();
                int selectedId = rg.getCheckedRadioButtonId();
                rb = findViewById(selectedId);
                final String check = rb.getText().toString();
                Toast.makeText(registerActivity.this,check,Toast.LENGTH_SHORT).show();
                email1 = editText3.getText().toString();
                password1 = editText8.getText().toString();
                if(flag==0)
                    mAuth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(registerActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            userID = user.getUid();
                            sendVerificationEmail();
                            //
                            // Toast.makeText(registerActivity.this, "Login Successful  "+user.getUid(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(registerActivity.this,"REGISTERED!!!",Toast.LENGTH_LONG).show();
                            if(flag==0 && check.equals("FARMER")){
                                current_user_db = mDatabase.child("Farmer").child(user.getUid());
                                StorageReference uploadpp = storageRef.child(user.getUid());
                                uri = Uri.parse("android.resource://com.example.sdew021.friendsofframers/drawable/farmer_icon_app");
                                try {
                                    InputStream stream = getContentResolver().openInputStream(uri);
                                    uploadpp.putStream(stream);
                                    Toast.makeText(registerActivity.this, "PP UPLOADED!!!", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Log.d("Error","Profile pic error",null);
                                }
                                startRegisterFarmer();

                            }

                            else if(flag==0 && check.equals("CONSUMER")){
                                current_user_db = mDatabase.child("Consumer").child(user.getUid());
                                StorageReference uploadpp = storageRef.child(user.getUid());
                                uri = Uri.parse("android.resource://com.example.sdew021.friendsofframers/drawable/farmer_icon_app");
                                try {
                                    InputStream stream = getContentResolver().openInputStream(uri);
                                    uploadpp.putStream(stream);
                                    Toast.makeText(registerActivity.this, "PP UPLOADED!!!", Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Log.d("Error","Profile pic error",null);
                                }
                                startRegisterConsumer();
                            }

                            if(flag==0)
                            {
                                //startActivity(new Intent(registerActivity.this, farmerActivity.class));
                                /*user = mAuth.getCurrentUser();
                                userID = user.getUid();
                                Toast.makeText(registerActivity.this,user.getUid(),Toast.LENGTH_LONG);*/
                            }

                        }
                        else if(email1.isEmpty()||password1.isEmpty())
                        {
                            Toast.makeText(registerActivity.this,"Please Enter all the details",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(registerActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                /*if(flag==0 && check.equals("FARMER"))
                    startRegisterFarmer();
                else if(flag==0 && check.equals("CONSUMER"))
                    startRegisterConsumer();*/


            }
        });



        /*button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                *//*int selectedId = rg.getCheckedRadioButtonId();
                rb = findViewById(selectedId);
                String check = rb.getText().toString();
                checkDataEntered();
                if(flag==0 && check.equals("FARMER"))
                    startRegisterFarmer();
                else
                    startRegisterConsumer();
                if(flag==0)
                    gotoLogin();*//*
                authUser();
                if(flag==0)
                    gotoLogin();

            }
        });
*/
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm((ViewGroup) findViewById(R.id.scrollView2));
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLogin();
            }
        });
    }



    /*public void authUser(){
        String email = editText3.getText().toString();
        String password = editText8.getText().toString();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful() ) {
                    //task.getResult().getUser();
                    int selectedId = rg.getCheckedRadioButtonId();
                    rb = findViewById(selectedId);
                    String check = rb.getText().toString();
                    checkDataEntered();
                    if(flag==0 && check.equals("FARMER"))
                        startRegisterFarmer();
                    else
                        startRegisterConsumer();

                    Toast.makeText(getApplicationContext(), "no error occurred", Toast.LENGTH_SHORT).show();
                } else {
                   // updateUI(task.getResult().getUser());
                    Toast.makeText(getApplicationContext(),"Registration unsuccessfull",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/



    /*private void updateUI(FirebaseUser getUserFromReg){

            userID = getUserFromReg.getUid();
    }*/

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registerActivity.this,"Email Sent",Toast.LENGTH_LONG);
                }
                else{
                    Toast.makeText(registerActivity.this,"Email Not Sent",Toast.LENGTH_LONG);
                }
            }
        });
    }

    public void startRegisterFarmer(){
        flag = 1;

        String name = editText2.getText().toString();
        String email = editText3.getText().toString().trim();
        String phone = editText4.getText().toString().trim();
        String permanentadd = editText5.getText().toString().trim();
        String currentadd = editText6.getText().toString().trim();
        String password = editText8.getText().toString().trim();

        current_user_db.child("email").setValue(email);
        current_user_db.child("contact").setValue(phone);
        current_user_db.child("name").setValue(name);
        current_user_db.child("balance").setValue("1000");
        current_user_db.child("role").setValue("farmer");
        current_user_db.child("image").setValue("gs://friends-of-farmers.appspot.com/Farmer_images/appicon.jpg");
        current_user_db.child("permanentAdd").setValue(permanentadd);
        current_user_db.child("currentAdd").setValue(currentadd);
        current_user_db.child("password").setValue(password);
        current_user_db.child("rating").setValue(0);

        DatabaseReference rice= current_user_db.child("crops").child("rice");
        rice.child("price").setValue("0");
        rice.child("name").setValue("rice");
        rice.child("pendingOrders").setValue("0");
        rice.child("stock").setValue("0");
        rice.child("user").setValue("0");

        DatabaseReference wheat= current_user_db.child("crops").child("wheat");
        wheat.child("price").setValue("0");
        wheat.child("name").setValue("wheat");
        wheat.child("pendingOrders").setValue("0");
        wheat.child("stock").setValue("0");
        wheat.child("user").setValue("0");

        DatabaseReference sugarcane= current_user_db.child("crops").child("sugarcane");
        sugarcane.child("price").setValue("0");
        sugarcane.child("name").setValue("sugarcane");
        sugarcane.child("pendingOrders").setValue("0");
        sugarcane.child("stock").setValue("0");
        sugarcane.child("user").setValue("0");

        DatabaseReference dal = current_user_db.child("crops").child("dal");
        dal.child("price").setValue("0");
        dal.child("name").setValue("dal");
        dal.child("pendingOrders").setValue("0");
        dal.child("stock").setValue("0");
        dal.child("user").setValue("0");

        DatabaseReference corn = current_user_db.child("crops").child("corn");
        corn.child("price").setValue("0");
        corn.child("name").setValue("corn");
        corn.child("pendingOrders").setValue("0");
        corn.child("stock").setValue("0");
        corn.child("user").setValue("0");

        flag=0;

        gotoLogin();

    }

    public void startRegisterConsumer(){
        flag=1;

        String name = editText2.getText().toString();
        String email = editText3.getText().toString().trim();
        String phone = editText4.getText().toString().trim();
        String permanentadd = editText5.getText().toString().trim();
        String currentadd = editText6.getText().toString().trim();
        String password = editText8.getText().toString().trim();

        current_user_db.child("email").setValue(email);
        current_user_db.child("contact").setValue(phone);
        current_user_db.child("balance").setValue("1000");
        current_user_db.child("name").setValue(name);
        current_user_db.child("role").setValue("farmer");
        current_user_db.child("image").setValue("gs://friends-of-farmers.appspot.com/Farmer_images/appicon.jpg");
        current_user_db.child("permanentAdd").setValue(permanentadd);
        current_user_db.child("currentAdd").setValue(currentadd);
        current_user_db.child("password").setValue(password);

        DatabaseReference myCart = current_user_db.child("myCart").child("item");
        myCart.child("name").setValue("0");
        myCart.child("price").setValue("0");
        myCart.child("quantity").setValue("0");

        DatabaseReference orders = current_user_db.child("orders").child("item");
        orders.child("cropPrice").setValue("0");
        orders.child("cropname").setValue("0");
        orders.child("quantity").setValue("0");
        orders.child("shippingAddress").setValue("0");
        flag=0;

        gotoLogin();
    }

    public void gotoLogin(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }



    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    boolean isEmail(EditText text){
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    boolean isShort(EditText text){
        int length = text.length();
        if(length<8)
            return true;
        else
            return false;
    }

    boolean isMobile(EditText text){
        int length = text.length();
        if(length==10)
            return true;
        else
            return false;
    }

    boolean isOtp(EditText text){
        CharSequence str = text.getText().toString();
        return str.equals(msg);
    }


    boolean isSame(EditText text1,EditText text2){
        CharSequence str1 = text1.getText().toString();
        CharSequence str2 = text2.getText().toString();
        return str1.equals(str2);
    }
    void checkDataEntered(){
        flag=0;
        if(isEmpty(editText2)){
            flag=1;
            //Toast t = Toast.makeText(this, "Enter Your Name To Register",Toast.LENGTH_SHORT);
            //t.show();
            editText2.setError("This field cannot be empty");
        }
        if(!isEmail(editText3)){
            flag=1;
            editText3.setError("Enter Valid Email ID");
        }
        if(!isMobile(editText4)){
            flag=1;
            editText4.setError("Enter A Valid Phone Number");
        }
        if(isEmpty(editText5)){
            flag = 1;
            editText5.setError("Enter Your Permanent Address To Register");
        }
        if(isEmpty(editText6)){
            flag=1;
            editText6.setError("Enter Your Current Address To Register");
        }
        if(isEmpty(editText8)){
            flag=1;
            editText8.setError("Enter Your Password To Register");
        }
        if(isShort(editText8)){
            flag=1;
            editText8.setError("Minimum 8 Character Password");
        }
        if(!isOtp(otp)) {
            flag=1;
            otp.setError("Wrong Otp");
        }
        if(!isSame(editText8,editText9)){
            flag=1;
            editText9.setError("Enter same Password as above");
        }

        if(isEmpty(editText9)){
            flag=1;
            editText9.setError("Confirm Your Password To Register");
        }

    }
}
