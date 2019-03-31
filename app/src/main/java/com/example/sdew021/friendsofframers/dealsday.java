package com.example.sdew021.friendsofframers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class dealsday extends AppCompatActivity {

    private ImageView sugarcane,Dal,Wheat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todaydeals);
        sugarcane=findViewById(R.id.sugarcaneimage);
        sugarcane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dealsday.this,Activity2.class));
            }
        });
//
//        Wheat=findViewById(R.id.wheatimage);
//        Wheat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(dealsday.this,Activity2.class));
//            }
//        });
//
//        Dal=findViewById(R.id.);
//        Dal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(dealsday.this,Activity2.class));
//            }
//        });
    }

}
