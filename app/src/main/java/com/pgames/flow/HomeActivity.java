package com.pgames.flow;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.pgames.flow.modules.Dashboard;
import com.pgames.flow.modules.HomeEmployer;
import com.pgames.flow.modules.HomeSeeker;
import com.pgames.flow.modules.Profile;
import com.pgames.flow.modules.ProfileEmployer;
import com.pgames.flow.modules.ProfileSeeker;
import com.pgames.flow.modules.dashboard_employee;
import com.pgames.flow.modules.dashboard_seeker;
import com.pgames.flow.ui.home.DataView;
import com.pgames.flow.ui.home.DataViewAdapter;
import com.pgames.flow.ui.home.HomeFragment;
import com.pgames.flow.ui.home.MyListAdapter;
import com.pgames.flow.utils.BottomNavigationBehavior;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView mUserName;
    private TextView mUserId;
    private ImageView mProfilePic;
    String mPhone,
            mName;
    private int mDashId = R.layout.fragment_dashboard;
    private int mHomeId = R.layout.fragment_home;
    private int mProfileId = R.layout.fragment_profile;
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

            //For Offline mode

            userType();
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.setOnNavigationItemSelectedListener(navLister);

            // attaching bottom sheet behaviour - hide / show on scroll
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
            layoutParams.setBehavior(new BottomNavigationBehavior());



            //Welcome Fragmemt for each time start the app
            WelcomeFragment welcomeFragment = new WelcomeFragment();
////            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.container_fragment,welcomeFragment).commit();
        }

        getPermissions();
    }

    private void userType(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference userType = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("User");
        if (user!=null){
         userType.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                 String selectUser = dataSnapshot.getValue().equals("Employer") ? "Employer" : "Job Seeker";
                 //switch the fragment by user type
                 switch (dataSnapshot.getValue().toString()){
                     case "Employer" :
//                         Log.e("Test","Employer");
                            mDashId = R.layout.fragment_dashboard_employee;
                            mHomeId = R.layout.fragment_home_employer;
                            mProfileId = R.layout.fragment_profile_employer;
                         break;
                     case "Job Seeker" :
//                         Log.e("Test","Job Seeker");
                         mDashId = R.layout.fragment_dashboard_seeker;
                         mHomeId = R.layout.fragment_home_seeker;
                         mProfileId = R.layout.fragment_profile_seeker;
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
                            selectedFragment = mHomeId == R.layout.fragment_home_employer ? new HomeEmployer() :
                            mHomeId == R.layout.fragment_home_seeker ? new HomeSeeker(): new HomeFragment();
                            break;
                        case R.id.bottom_nav_dashboard:

                            selectedFragment = mDashId==R.layout.fragment_dashboard_employee ? new dashboard_employee() :
                                    mDashId == R.layout.fragment_dashboard_seeker ? new dashboard_seeker(): new Dashboard();
                            break;
                        case R.id.bottom_nav_profile:
                            selectedFragment = mProfileId == R.layout.fragment_profile_employer ? new ProfileEmployer() :
                                  mProfileId == R.layout.fragment_profile_seeker ? new ProfileSeeker() : new Profile();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,selectedFragment).commit();
                    return true;
                }
            };



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().goOnline();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}