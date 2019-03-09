package com.example.sdew021.friendsofframers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private Firebase mRef;
    private DatabaseReference mDatabase;
    private ProgressDialog mDiag;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

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
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://friendsofframers1-master.firebaseio.com/Users");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.mainContainer);
        AnimationDrawable animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                checkDataEntered();
                if(flag==0)
                    startRegister();
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

    public void startRegister(){
        mDiag = new ProgressDialog(this);
        mDiag.setMessage("Registering..");
        mDiag.show();
        String name = editText2.getText().toString();
        String email = editText3.getText().toString().trim();
        String phone = editText4.getText().toString().trim();
        String permanentadd = editText5.getText().toString().trim();
        String currentadd = editText6.getText().toString().trim();
        String password = editText8.getText().toString().trim();

        DatabaseReference current_user_db = mDatabase.child(name);
        current_user_db.child("email").setValue(email);
        current_user_db.child("phone").setValue(phone);
        current_user_db.child("permanent address").setValue(permanentadd);
        current_user_db.child("current address").setValue(currentadd);
        current_user_db.child("password").setValue(password);
        mDiag.dismiss();
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
