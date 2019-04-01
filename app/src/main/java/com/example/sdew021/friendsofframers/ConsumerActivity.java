package com.example.sdew021.friendsofframers;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ConsumerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button btnbuy;
    String user_id = "Saurabh Dewangan";
    //FirebaseAuth firebaseAuth;
    // DatabaseReference databaseReference ;
    FirebaseAuth mAuth;

    TextView name_profile;
    private FirebaseUser user;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser currentUser;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer);
        //profile = new Users();
        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        userId=currentUser.getUid();
        mDatabaseReference=FirebaseDatabase.getInstance().getReference().child("Users").child("Consumer").child(userId);

        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.openDrawer(Gravity.START);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        btnbuy = findViewById(R.id.btnbuy);

        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ConsumerActivity.this, UserFarmers.class);

                startActivity(i);

            }
        });



        // startActivity(new Intent(ConsumerActivity.this,dealsday.class));




        // for users name
        //irebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users").child("Consumer").child(userId);
        //FirebaseDatabase firebaseDatabase;
//          DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
        //databaseReference = FirebaseDatabase.getInstance().getReference(user_id);



        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name_profile = findViewById(R.id.name_profile);
                name_profile.setText(dataSnapshot.child("name").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
                // Log.d("Second", user);
                //  name_profile.setText(user.getName());

//        final TextView t_name = findViewById(R.id.name_profile);






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            Dialog dialog = new Dialog(this);
//            dialog.addContentView();
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // Toast.makeText(ConsumerActivity.this,Integer.toString(id),Toast.LENGTH_LONG).show();
        //Log.d("DBUG",Integer.toString(id) + "   --------------------");

        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, secondActivity.class));
        } else if (id == R.id.nav_deals) {
            startActivity(new Intent(this, dealsday.class));

        } else if (id == R.id.nav_orders) {
            startActivity(new Intent(this, orders.class));

        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(this, start.class));

        } else if (id == R.id.nav_wallet) {
            startActivity(new Intent(this, consumerWallet.class));

        } else if (id == R.id.nav_help) {
            startActivity(new Intent(this, help.class));

        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(this, feedback.class));


        } else if(id == R.id.nav_exit){
            /*Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*/
            mAuth.signOut();
            startActivity(new Intent(ConsumerActivity.this,MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}



