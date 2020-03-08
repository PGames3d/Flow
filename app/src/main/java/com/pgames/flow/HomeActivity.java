package com.pgames.flow;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.pgames.flow.firebaseHandler.FirebaseHandler;
import com.pgames.flow.ui.home.HomeFragment;
import com.pgames.flow.ui.home.MyListAdapter;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView mUserName;
    private TextView mUserId;
    private ImageView mProfilePic;
    String mPhone,
            mName;
    private int resid;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new MyListAdapter(getApplicationContext());
        if (findViewById(R.id.container_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }

            userType();
            HomeFragment homeFragment  = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,homeFragment).commit();
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navLister);
        getPermissions();
    }

    private void userType(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userType = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("User-Type").child("User");
        if (user!=null){
         userType.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 String selectUser = dataSnapshot.getValue().equals("Employer") ? "Employer" : "Job Seeker";
                 //switch the fragment by user type
                 switch (dataSnapshot.getValue().toString()){
                     case "Employer" :
//                         Log.e("Test","Employer");
                         
                         break;
                     case "Job Seeker" :
                         Log.e("Test","Job Seeker");
                         break;
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
        }
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navLister =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.bottom_nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.bottom_nav_dashboard:
                            selectedFragment = new Dashboard();

                            break;
                        case R.id.bottom_nav_profile:
                            selectedFragment = new Profile();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,selectedFragment).commit();
                    return true;
                }
            };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionPerformed(final DataTransportor transportor){

        FirebaseHandler.mValidateData.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
           FirebaseDatabase.getInstance().getReference().removeEventListener((ValueEventListener) FirebaseHandler.mValidateData);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionComplete(DataTransportor dataTransportor){

    }




    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        FirebaseDatabase.getInstance().goOffline();
        super.onStop();
    }
}