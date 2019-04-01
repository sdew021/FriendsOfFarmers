/*
 *   Contributed by Saurabh Dewangan
 *   17CO140
 */

package com.example.sdew021.friendsofframers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class consumerWallet extends AppCompatActivity {

    TextView balance;
    EditText amount;
    EditText checkOtp;
    Button button;
    Button otp;
    String contact;
    Random r = new Random();
    int low = 1000;
    int high = 9999;
    int result = r.nextInt(high-low)+low;
    String msg = Integer.toString(result);
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_wallet);

        amount = (EditText)findViewById(R.id.amount);
        checkOtp = (EditText)findViewById(R.id.otpCheck);
        balance = (TextView)findViewById(R.id.balance);
        otp = findViewById(R.id.otpBut);
        button = findViewById(R.id.button);
        Firebase.setAndroidContext(this);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Consumer").child(user.getUid());

        mDatabase.child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String bal = dataSnapshot.getValue().toString();
                balance.setText(bal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase.child("contact").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contact = dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(contact,null,msg,null,null);
                Toast.makeText(getApplicationContext(),"Message Sent Successfully!",Toast.LENGTH_LONG).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.isEmailVerified() && check(amount) == 0 && checkMsg(checkOtp)){
                    withdrawMoney();
                }
                else{
                    Toast.makeText(consumerWallet.this,"Verfiy Email",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public int check(EditText text){
        String str = text.getText().toString().trim();
        if(str.isEmpty()){
            text.setError("Invalid amount");
            return 1;
        }else{
            return 0;
        }
    }

    boolean checkMsg(EditText text){
        String str = text.getText().toString().trim();
        if(!str.equals(msg))
            text.setError("Invalid OTP");
        return str.equals(msg);
    }

    public void withdrawMoney(){
        final String amt = amount.getText().toString();
        mDatabase.child("balance").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String amount = dataSnapshot.getValue().toString();
                if((Integer.parseInt(amount) + Integer.parseInt(amt)) >= 50000 ){
                    Toast.makeText(consumerWallet.this,"Wallet limit : 5000",Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.child("balance").setValue(Integer.toString(Integer.parseInt(amount)+Integer.parseInt(amt)));
                    Toast.makeText(consumerWallet.this,"Transaction Complete!!!",Toast.LENGTH_SHORT).show();
                    String finBal = Integer.toString(Integer.parseInt(amount)+Integer.parseInt(amt));
                    String remBal = Integer.toString(Integer.parseInt(amt));
                    sendTransactionMessage(remBal);
                    balance.setText(finBal);
                    clearForm((ViewGroup) findViewById(R.id.constLay) );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clearForm(ViewGroup group){
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    public void sendTransactionMessage(String bal){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(contact,null,"Rupees "+bal+" has been debited from your account",null,null);
        Toast.makeText(getApplicationContext(),"Transaction Successfull",Toast.LENGTH_LONG).show();
    }
}
