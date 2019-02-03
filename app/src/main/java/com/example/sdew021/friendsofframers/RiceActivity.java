package com.example.sdew021.friendsofframers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RiceActivity extends AppCompatActivity {
    EditText editText1;
    int flag=1;
    List<User> userList;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);

        editText1 = findViewById(R.id.enterquantity);
        userList=new ArrayList<>();
        userList.add(new User("Rishi","3rd Block NITK","9765634770","50","5","2"));
        userList.add(new User("Prateek","3rd Block NITK","9765634770","50","5","2"));
        userList.add(new User("Rahul","3rd Block NITK","9765634770","50","5","2"));
        userList.add(new User("Ranjan","3rd Block NITK","9765634770","50","5","2"));
        RecyclerView recyclerView=findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter=new UserAdapter(this, userList);
        recyclerView.setAdapter(userAdapter);

    }

    public void button4(View v){

        if(editText1.getText().toString().equalsIgnoreCase("")){
            editText1.setError("Please enter something");
            flag=0;
        }

        if(flag==1){
            Toast.makeText(RiceActivity.this,"abc",Toast.LENGTH_LONG).show();
        }

    }
}
