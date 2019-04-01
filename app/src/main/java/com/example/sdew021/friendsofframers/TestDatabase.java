/*
 *   Contributed by Prateek Sahu
 *   17CO130
 */
package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class TestDatabase extends AppCompatActivity {

    private ListView mlistView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(TestDatabase.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_database);
        if(!FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mlistView=(ListView) findViewById(R.id.listview);
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Users1");

        FirebaseListAdapter<String> firebaseListAdapter=new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_expandable_list_item_1,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {
                TextView textView=(TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        mlistView.setAdapter(firebaseListAdapter);

    }


}
