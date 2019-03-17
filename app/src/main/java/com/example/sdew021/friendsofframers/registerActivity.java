package com.example.sdew021.friendsofframers;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class registerActivity  extends AppCompatActivity {
    int flag = 0;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText8;
    EditText editText9;
    Button button;
    Button button2;
    Button button3;
    EditText otp;
    Button otpbutton;
    RadioGroup rg;
    RadioButton rb;
    private Firebase mRef;
    private DatabaseReference mDatabase;
    private ProgressDialog mDiag;

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
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://friends-of-farmers.firebaseio.com/Users");
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

            }
        });




        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                int selectedId = rg.getCheckedRadioButtonId();
                rb = findViewById(selectedId);
                String check = rb.getText().toString();
                checkDataEntered();
                if(flag==0 && check.equals("FARMER"))
                    startRegisterFarmer();
                else
                    startRegisterConsumer();
                if(flag==0)
                    gotoLogin();

            }
        });

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

    public void startRegisterFarmer(){
        mDiag = new ProgressDialog(this);
        mDiag.setMessage("Registering..");
        mDiag.show();
        String name = editText2.getText().toString();
        String email = editText3.getText().toString().trim();
        String phone = editText4.getText().toString().trim();
        String permanentadd = editText5.getText().toString().trim();
        String currentadd = editText6.getText().toString().trim();
        String password = editText8.getText().toString().trim();

        DatabaseReference current_user_db = mDatabase.child("Farmer").child(name);
        current_user_db.child("email").setValue(email);
        current_user_db.child("phone").setValue(phone);
        current_user_db.child("permanentAddress").setValue(permanentadd);
        current_user_db.child("currentAddress").setValue(currentadd);
        current_user_db.child("password").setValue(password);
        current_user_db.child("rating").setValue("0");

        DatabaseReference rice= current_user_db.child("crops").child("rice");
        rice.child("price").setValue("0");
        rice.child("pendingOrders").setValue("0");
        rice.child("stock").setValue("0");
        rice.child("user").setValue("0");

        DatabaseReference wheat= current_user_db.child("crops").child("wheat");
        wheat.child("price").setValue("0");
        wheat.child("pendingOrders").setValue("0");
        wheat.child("stock").setValue("0");
        wheat.child("user").setValue("0");

        DatabaseReference sugarcane= current_user_db.child("crops").child("sugarcane");
        sugarcane.child("price").setValue("0");
        sugarcane.child("pendingOrders").setValue("0");
        sugarcane.child("stock").setValue("0");
        sugarcane.child("user").setValue("0");

        DatabaseReference dal = current_user_db.child("crops").child("dal");
        dal.child("price").setValue("0");
        dal.child("pendingOrders").setValue("0");
        dal.child("stock").setValue("0");
        dal.child("user").setValue("0");

        DatabaseReference corn = current_user_db.child("crops").child("corn");
        corn.child("price").setValue("0");
        corn.child("pendingOrders").setValue("0");
        corn.child("stock").setValue("0");
        corn.child("user").setValue("0");

        mDiag.dismiss();
    }

    public void startRegisterConsumer(){
        String name = editText2.getText().toString();
        String email = editText3.getText().toString().trim();
        String phone = editText4.getText().toString().trim();
        String permanentadd = editText5.getText().toString().trim();
        String currentadd = editText6.getText().toString().trim();
        String password = editText8.getText().toString().trim();

        DatabaseReference current_user_db = mDatabase.child("Consumer").child(name);
        current_user_db.child("email").setValue(email);
        current_user_db.child("phone").setValue(phone);
        current_user_db.child("permanentAddress").setValue(permanentadd);
        current_user_db.child("currentAddress").setValue(currentadd);
        current_user_db.child("password").setValue(password);

        DatabaseReference myCart = current_user_db.child("myCart");
        myCart.child("image").setValue("0");
        myCart.child("name").setValue("0");
        myCart.child("price").setValue("0");
        myCart.child("quantity").setValue("0");

        DatabaseReference orders = current_user_db.child("orders");
        orders.child("cropPrice").setValue("0");
        orders.child("cropname").setValue("0");
        orders.child("quantity").setValue("0");
        orders.child("shippingAddress").setValue("0");
    }

    public void gotoLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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
