package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class farmerActivity extends AppCompatActivity {
    private FarmerAdapter farmerAdapter;
    private DatabaseReference mDatabaseRefrence;
    private RecyclerView CropList;
    private EditText searchBar;
    private ImageView profileImage;
    private List<Crop> filteredList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        CropList = (RecyclerView) findViewById(R.id.recyclerView);;
        mDatabaseRefrence=FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Prateek");
        FarmerAdapter.gCropList=new ArrayList<>();
        FarmerAdapter.gCropIds=new ArrayList<>();
        CropList.setLayoutManager(new LinearLayoutManager(this));
        farmerAdapter=new FarmerAdapter(this, mDatabaseRefrence);
        CropList.setAdapter(farmerAdapter);
        Onclick();


        searchBar=(EditText) findViewById(R.id.searchbar);
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

        profileImage=findViewById(R.id.profileImage);
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
        filteredList=new ArrayList<>();
        List<Integer> index = new ArrayList<Integer>();
        int var=0;
        for (Crop crop:FarmerAdapter.gCropList){
            if(crop.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(crop);
                index.add(var);
            }
            var++;
        }
        farmerAdapter.filterList(filteredList,index);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        super.onStop();
        farmerAdapter.cleanupListener();
    }




    public void Onclick() {
        farmerAdapter.setOnItemClickListener(new FarmerAdapter.OnItemCLickListener() {
            @Override
            public void onItemClick(Crop crop) {
                if (crop != null) {
                    Log.i("Item clicked", "");
                    if (crop.getName().toLowerCase().compareTo("wheat") == 0) {
                        Intent intent1 = new Intent(farmerActivity.this, WheatActivity.class);
                        startActivity(intent1);
                    } else if (crop.getName().toLowerCase().compareTo("corn") == 0) {
                        Intent intent2 = new Intent(farmerActivity.this, CornActivity.class);

                        startActivity(intent2);
                    } else if (crop.getName().toLowerCase().compareTo("sugarcane") == 0) {
                        Intent intent3 = new Intent(farmerActivity.this, SugarcaneActivity.class);
                        startActivity(intent3);
                    } else {
                        Intent intent4 = new Intent(farmerActivity.this, RiceActivity.class);
                        startActivity(intent4);
                    }
                }
                else
                    Log.i("NULL ITEM", "NULL ITEM");

            }


        });
    }
}
