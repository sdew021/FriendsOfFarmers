/*
 *   Contributed by Saurabh Dewangan
 *   17CO140
 */

package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class feedback extends AppCompatActivity {

    EditText message;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        ConstraintLayout myLayout = (ConstraintLayout) findViewById(R.id.containerfeed);
        AnimationDrawable animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().trim().compareTo("")!=0)
                sendEmail();
                else
                    Toast.makeText(feedback.this,"Enter Some feedback",Toast.LENGTH_LONG).show();
            }
        });


    }

    protected void sendEmail() {

        String msg = message.getText().toString();
        Log.i("Send email", "");

        String[] TO = {"sdewangan021@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK");
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
            Toast.makeText(feedback.this,"FEEDBACK SENT",Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(feedback.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}