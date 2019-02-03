package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class farmerActivity extends AppCompatActivity {
    List<Crop> cropList;
    FarmerAdapter farmerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        RecyclerView CropList = (RecyclerView) findViewById(R.id.recyclerView);;
        cropList=new ArrayList<>();

        cropList.add(new Crop(R.drawable.wheat,"Wheat","1","2"));
        cropList.add(new Crop(R.drawable.corn,"Corn","1","2"));
        cropList.add(new Crop(R.drawable.sugarcane,"Sugarcane","1","2"));
        cropList.add(new Crop(R.drawable.rice,"Rice","1","2"));

        CropList.setLayoutManager(new LinearLayoutManager(this));
        farmerAdapter=new FarmerAdapter(this,cropList);
        CropList.setAdapter(farmerAdapter);
        EditText searchBar=(EditText) findViewById(R.id.searchbar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        Onclick();

        ImageView profileImage=findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                openActivity2();
            }
        });
    }
    public void openActivity2(){
        Intent intent=new Intent(this,Activity2.class);
        startActivity(intent);
    }


    private void filter(String text){
        List<Crop> filteredList=new ArrayList<>();
        for (Crop crop:cropList){
            if(crop.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(crop);
            }
        }
        farmerAdapter.filterList(filteredList);
    }

    public void Onclick(){
        farmerAdapter.setOnItemClickListener(new FarmerAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(int position) {
                int pos=position;
                Log.i("Item clicked",String.valueOf(pos));
                if(pos==0){
                    Intent intent1=new Intent(farmerActivity.this,WheatActivity.class);
                    startActivity(intent1);
                }
                else if(pos==1){
                    Intent intent2=new Intent(farmerActivity.this,CornActivity.class);
                    startActivity(intent2);
                }
                else if(pos==2){
                    Intent intent3=new Intent(farmerActivity.this,SugarcaneActivity.class);
                    startActivity(intent3);
                }
                else{
                    Intent intent4=new Intent(farmerActivity.this,RiceActivity.class);
                    startActivity(intent4);
                }
            }
        });

    }
}
