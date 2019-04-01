/*
 * Contributed by Rishi Sharma
 *  17CO135
 *  */

package com.example.sdew021.friendsofframers;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SugarcaneActivity extends AppCompatActivity {

    private Button button1, button2;
    private DatabaseReference mDatabase;
    private DatabaseReference listdatabaseRefernce;
    private EditText mEnterQuantity;
    private ChildEventListener mChildEventListner;
    private FirebaseUser user;
    private int stock;
    private ImageView img;
    private String userId;
    EditText editText1;
    EditText editText2;
    int flag = 1;
    List<User> userList;
    UserAdapter userAdapter;
    private TextView priceView,quantatyView,pOrdersView,cropname;
    private DatabaseReference mDatabaseReference;

    TextView textView1,textView2,textView3,textView4,textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);

        priceView=findViewById(R.id.cp);
        quantatyView=findViewById(R.id.quant);
        pOrdersView=findViewById(R.id.porders);

        img = (ImageView) findViewById(R.id.riceimage);
        img.setImageResource(R.drawable.sugarcane);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        editText1 = (EditText) findViewById(R.id.enterquantity);
        editText2 = (EditText) findViewById(R.id.enterquantity2);//https://friends-of-farmers.firebaseio.com/Rishi/Farmer1/Crops/Crop2
        user= FirebaseAuth.getInstance().getCurrentUser();
        userId=user.getUid();


        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Users").
                child("Farmer").child(user.getUid()).child("crops").child("sugarcane");
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                priceView.setText(dataSnapshot.child("price").getValue(String.class));
                quantatyView.setText(dataSnapshot.child("stock").getValue(String.class));
                pOrdersView.setText(dataSnapshot.child("pendingOrders").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").
                child("Farmer").child(user.getUid()).child("crops").child("sugarcane");//        button1.setOnClickListener(new View.OnClickListener() {
//                                       @Override
//                                       public void onClick(View v) {
//                                           if (editText1.getText().toString().equals("")) {
//                                               Toast.makeText(RiceActivity.this, "Quantity Entered ", Toast.LENGTH_SHORT).show();
//                                               flag = 0;
//                                           }
//                                       }
//                                   });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText1.getText().toString().isEmpty()) {
                    Toast.makeText(SugarcaneActivity.this, "Enter Price:" + editText1.getText().toString(), Toast.LENGTH_SHORT).show();
                    flag = 0;
                } else {
//                    int price=Integer.parseInt(dataSnapshot.getValue().toString());
//                    price = Integer.parseInt(editText1.getText().toString())+price;
                    mDatabase.child("price").setValue(editText1.getText().toString());
                    Log.i("Price Updated Success","Price Updated Success");
                    Toast.makeText(SugarcaneActivity.this, "Price Updated Success", Toast.LENGTH_SHORT).show();
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText2.getText().toString().isEmpty()) {
                    Toast.makeText(SugarcaneActivity.this, "Enter Quantity:" + editText2.getText().toString(), Toast.LENGTH_SHORT).show();
                    flag = 0;
                } else {
                    mDatabase.child("stock").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            stock=Integer.parseInt(dataSnapshot.getValue().toString());
                            stock = Integer.parseInt(editText2.getText().toString())+stock;
                            mDatabase.child("stock").setValue(Integer.toString(stock));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(SugarcaneActivity.this,"Unable to Connect to database ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(" Quantity Success","Quantity Success");
                    Toast.makeText(SugarcaneActivity.this, "Quantity Updated Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RecyclerView recyclerView=findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter=new UserAdapter(this);
        recyclerView.setAdapter(userAdapter);

//        textView1 = (TextView)findViewById(R.id.textView6);
//        textView2 = (TextView)findViewById(R.id.textView7);
//        textView3 = (TextView)findViewById(R.id.textView8);
//        textView4 = (TextView)findViewById(R.id.textView10);
//        textView5 = (TextView)findViewById(R.id.textView11);



//        textView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RiceActivity.this, user1Activity.class);
//
//                startActivity(intent);
//
//            }
//        });
//
//
//        textView2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RiceActivity.this, user2Activity.class);
//
//                startActivity(intent);
//
//            }
//        });
//
//
//        textView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RiceActivity.this, user3Activity.class);
//
//                startActivity(intent);
//
//            }
//        });
//
//
//        textView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RiceActivity.this, user4Activity.class);
//
//                startActivity(intent);
//
//            }
//        });
//
//
//        textView5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(RiceActivity.this, user5Activity.class);
//
//                startActivity(intent);
//
//            }
//        });
        listdatabaseRefernce= FirebaseDatabase.getInstance().getReference().child("Users").child("Farmer")
                .child(userId).child("crops").child("sugarcane").child("user");


        ChildEventListener childEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey()+" HMap Size:"+UserAdapter.gCropIdValue.size());
                User user=dataSnapshot.getValue(User.class);
                UserAdapter.gUserIdValue.put(dataSnapshot.getKey(),user);
                userAdapter.filterList(UserAdapter.gUserIdValue);
//                filterList(UserAdapter.gUserIdValue);
                Log.w("onChildchanged", "onChildadded:");


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
                String userKey= dataSnapshot.getKey();
                User myUser =UserAdapter.gUserIdValue.get(userKey);
                if(myUser!=null){
                    Log.w("onChildchanged", "onChildChanged:" + userKey);
                    UserAdapter.gUserIdValue.put(userKey,myUser);
                    userAdapter.filterList(UserAdapter.gUserIdValue);
                }
                else{
                    Log.w("onChildchanged", "onChildChanged:unknown_child:" + userKey);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Log.d(TAG,"onChildRemoved:"+dataSnapshot.getKey());
                String userKey=dataSnapshot.getKey();
                User rUser=UserAdapter.gUserIdValue.get(userKey);
                if(rUser!=null){
                    UserAdapter.gUserIdValue.remove(userKey);
                    userAdapter.filterList(UserAdapter.gUserIdValue);
                }
                else{
                    Log.w("onChildremoved","onChildRemoved:unknown_child:"+userKey);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
                User movedUser=dataSnapshot.getValue(User.class);
                String userKey=dataSnapshot.getKey();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("oncancelled", "postComments:onCancelled", databaseError.toException());
                Toast.makeText(SugarcaneActivity.this, "Failed to load User data.",Toast.LENGTH_SHORT).show();

            }
        };
        listdatabaseRefernce.addChildEventListener(childEventListener);
        mChildEventListner=childEventListener;

        cropname=findViewById(R.id.textrice);
        cropname.setText("Sugarcane");
    }
}

