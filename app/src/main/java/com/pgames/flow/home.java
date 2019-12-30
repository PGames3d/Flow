package com.pgames.flow;

import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pgames.flow.ui.home.MyListAdapter;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView mUserName;
    private TextView mUserId;
    private ImageView mProfilePic;
    String mPhone,
            mName;
   private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        new MyListAdapter(home.this);
        View headerView = navigationView.getHeaderView(0);
        final TextView userName = headerView.findViewById(R.id.userName);
        final TextView userId   = headerView.findViewById(R.id.userId);
        final ImageView profilePic = headerView.findViewById(R.id.imageView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       final FirebaseStorage storage = FirebaseStorage.getInstance();

        if (user != null) {
            FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid())
                    .child("Personal")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            userName.setText(dataSnapshot.child("Name").getValue().toString());
                            userId.setText(dataSnapshot.child("Phone").getValue().toString());

                           String s=dataSnapshot.child("Profile-Image").getValue().toString();
                            Toast.makeText(home.this, s, Toast.LENGTH_SHORT).show();
//                            Picasso.get().load(s).into(profilePic);
//                            profilePic.setImageURI();
                            Glide.with(home.this).load(s).apply(RequestOptions.circleCropTransform()).into(profilePic);
                        }catch (Exception e){
                            Toast.makeText(home.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }else
            Toast.makeText(home.this, "Connection Not Form "+user, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }



}