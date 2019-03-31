package com.example.sdew021.friendsofframers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static android.support.constraint.Constraints.TAG;

public class farmerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FarmerAdapter farmerAdapter;
    private DatabaseReference mDatabaseRefrence;
    private DatabaseReference mDatabaseRefrence2;
    private StorageReference mStorageReference;
    private RecyclerView CropList;
    private EditText searchBar;
    private ImageView profileImage;
    public static Map<String,Crop> filteredMap;
    private List<Crop> cropList=new ArrayList<>();
    private Spinner spinner;
    private ChildEventListener mChildEventListner;
    private FirebaseUser currentFirebaseUser;
    String searchText;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        searchText="";
        CropList = (RecyclerView) findViewById(R.id.recyclerView);
        spinner=(Spinner) findViewById(R.id.spinner);
        currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userId=currentFirebaseUser.getUid();
        Log.w(TAG, userId);
        Log.w(TAG, "after spinnner");
//        mDatabaseRefrence=FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Users/Farmer/pm7FdiYxQ9Xt3lnGnmAo3rnzhcP2/crops");
        mDatabaseRefrence=FirebaseDatabase.getInstance().getReference().child("Users")
                .child("Farmer").child(userId).child("crops");
        listenData(this,mDatabaseRefrence);
//        filterList(FarmerAdapter.gCropIdValue);
        CropList.setLayoutManager(new LinearLayoutManager(this));
        farmerAdapter=new FarmerAdapter(this, cropList);
        CropList.setAdapter(farmerAdapter);
        farmerAdapter.filterList(FarmerAdapter.gCropIdValue);
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

                searchText=s.toString();
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

        ArrayAdapter<CharSequence> spinnerAdapter=ArrayAdapter.createFromResource(this, R.array.sortArray,R.layout.spinner_item);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);



        mDatabaseRefrence2= FirebaseDatabase.getInstance().getReferenceFromUrl("https://friends-of-farmers.firebaseio.com/Prateek/farmer1/Details");
        final ValueEventListener dataListner =new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FarmerProfileDetails farmerProfileDetails = dataSnapshot.getValue(FarmerProfileDetails.class);
                mStorageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://friends-of-farmers.appspot.com/");
                mStorageReference.child(userId).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerCrop().into(profileImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(farmerActivity.this,"Cannot load image",Toast.LENGTH_SHORT);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mDatabaseRefrence2.addValueEventListener(dataListner);

    }
    public void openActivity2(){
        Intent intent=new Intent(this,Profile_Activity.class);
        startActivity(intent);
    }


    private void filter(String text){
        filteredMap=new LinkedHashMap<>();
//        Log.w(TAG,"filter called with text:"+searchText);
        for(Entry mapElement:FarmerAdapter.gCropIdValue.entrySet()){
            String key=(String) mapElement.getKey();
            Crop crop=(Crop)mapElement.getValue();
            if(crop.getName()!=null&&crop.getName().toLowerCase().contains(text.toLowerCase())){
                filteredMap.put(key,crop);
            }
//            Log.w(TAG,"filter Crop:"+crop.getName());
        }
        farmerAdapter.filterList(filteredMap);
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
                    } else if(crop.getName().toLowerCase().compareTo("rice") == 0){
                        Intent intent4 = new Intent(farmerActivity.this, RiceActivity.class);
                        startActivity(intent4);
                    }
                    else{
                        Intent intent4 = new Intent(farmerActivity.this, DalActivity.class);
                        startActivity(intent4);
                    }

                }
                else
                    Log.i("NULL ITEM", "NULL ITEM");

            }


        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
        if(position==0){
//            Log.w(TAG,"position:"+position);
            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            FarmerAdapter.gCropIdValue=sortByStock(FarmerAdapter.gCropIdValue,true);
//            printMap(FarmerAdapter.gCropIdValue);
            filter(searchText);
        }
        else if(position==1){
//            Log.w(TAG,"position:"+position);
            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            FarmerAdapter.gCropIdValue=sortByStock(FarmerAdapter.gCropIdValue,false);
//            printMap(FarmerAdapter.gCropIdValue);
            filter(searchText);
        }
        else if(position==2){
//            Log.w(TAG,"position:"+position);
            Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
            FarmerAdapter.gCropIdValue=sortBypendingOrders(FarmerAdapter.gCropIdValue,true);
//            printMap(FarmerAdapter.gCropIdValue);
            filter(searchText);

        }
        else {
//            Log.w(TAG,"position:"+position);
            Toast.makeText(parent.getContext(),text, Toast.LENGTH_SHORT).show();
            FarmerAdapter.gCropIdValue = sortBypendingOrders(FarmerAdapter.gCropIdValue, false);
//            printMap(FarmerAdapter.gCropIdValue);
            filter(searchText);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private static Map<String, Crop> sortByStock(Map<String, Crop> unsortMap, final boolean order)
    {

        List<Entry<String, Crop>> list = new LinkedList<Entry<String, Crop>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Crop>>()
        {
            public int compare(Entry<String, Crop> o1, Entry<String, Crop> o2)
            {
                int stock1=Integer.parseInt(o1.getValue().getStock());
                int stock2=Integer.parseInt(o2.getValue().getStock());
                if (order)
                {
                    return Integer.compare(stock1,stock2);
                }
                else
                {
                    return Integer.compare(stock2,stock1);

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Crop> sortedMap = new LinkedHashMap<>();
        printList(list);
//        Log.d(TAG,"Break");
        for (int i=0;i<list.size();++i)
        {
//            Log.d(TAG,"i="+i+" Key :" + list.get(i).getKey() + " Value :"+ list.get(i).getValue().getStock());
            sortedMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
//        printMap(sortedMap);
        return sortedMap;
    }


    private static Map<String, Crop> sortBypendingOrders(Map<String, Crop> unsortMap, final boolean order)
    {

        List<Entry<String, Crop>> list = new LinkedList<Entry<String, Crop>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Crop>>()
        {
            public int compare(Entry<String, Crop> o1, Entry<String, Crop> o2)
            {
                int stock1=Integer.parseInt(o1.getValue().getPendingOrders());
                int stock2=Integer.parseInt(o2.getValue().getPendingOrders());
                if (order)
                {
                    return Integer.compare(stock1,stock2);
                }
                else
                {
                    return Integer.compare(stock2,stock1);

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Crop> sortedMap = new LinkedHashMap<>();
        printList(list);
//        Log.d(TAG,"Break");
        for (int i=0;i<list.size();++i)
        {
//            Log.d(TAG,"i="+i+" Key :" + list.get(i).getKey() + " Value :"+ list.get(i).getValue().getStock());
            sortedMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
//        printMap(sortedMap);
        return sortedMap;
    }

    public static void printMap(Map<String, Crop> map)
    {
        for (Entry<String, Crop> entry : map.entrySet())
        {
            Log.d(TAG,"Key :" + entry.getKey() + " Value :"+ entry.getValue().getName());
        }
    }
    public static void printList(List<Entry<String, Crop>> list)
    {
        for (int i=0;i<list.size();++i)
        {
            Entry<String,Crop> entry=list.get(i);
            Log.d(TAG,"i="+i+" Key :" + entry.getKey() + " Value :"+ entry.getValue().getStock());
        }
    }
    void listenData(final Context mCtx, DatabaseReference databaseRefernce){
        Log.w(TAG, "listen data called");
        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey()+" HMap Size:"+FarmerAdapter.gCropIdValue.size());
                Crop crop=dataSnapshot.getValue(Crop.class);
                if (crop.getName()!=null&&crop.getName().toLowerCase().compareTo("wheat") == 0) {
                    crop.Crop_image=R.drawable.wheat;

                } else if (crop.getName()!=null&&crop.getName().toLowerCase().compareTo("corn") == 0) {
                    crop.Crop_image=R.drawable.corn;

                } else if (crop.getName()!=null&&crop.getName().toLowerCase().compareTo("sugarcane") == 0) {
                    crop.Crop_image=R.drawable.sugarcane;

                } else if(crop.getName()!=null&&crop.getName().toLowerCase().compareTo("rice") == 0) {
                    crop.Crop_image=R.drawable.rice;
                } else if(crop.getName()!=null&&crop.getName().toLowerCase().compareTo("dal") == 0) {
                    crop.Crop_image = R.drawable.dal;
                }
                FarmerAdapter.gCropIdValue.put(dataSnapshot.getKey(),crop);
                FarmerAdapter.gCropIdValue=sortByStock(FarmerAdapter.gCropIdValue,true);
                farmerAdapter.filterList(FarmerAdapter.gCropIdValue);
//                filterList(FarmerAdapter.gCropIdValue);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                String cropKey= dataSnapshot.getKey();
                Crop myCrop =FarmerAdapter.gCropIdValue.get(cropKey);
                if(myCrop!=null){
                    FarmerAdapter.gCropIdValue.put(cropKey,myCrop);
                }
                else{
                    Log.w(TAG, "onChildChanged:unknown_child:" + cropKey);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG,"onChildRemoved:"+dataSnapshot.getKey());
                String cropKey=dataSnapshot.getKey();
                Crop rCrop=FarmerAdapter.gCropIdValue.get(cropKey);
                if(rCrop!=null){
                    FarmerAdapter.gCropIdValue.remove(cropKey);
                }
                else{
                    Log.w(TAG,"onChildRemoved:unknown_child:"+cropKey);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                Crop movedCrop=dataSnapshot.getValue(Crop.class);
                String cropKey=dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(mCtx, "Failed to load comments.",Toast.LENGTH_SHORT).show();

            }
        };

//        Log.w(TAG, "After firebase listner");
        databaseRefernce.addChildEventListener(childEventListener);
        mChildEventListner=childEventListener;
    }
}
