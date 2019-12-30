package com.pgames.flow.ui.home;

import android.app.Person;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.R;

import java.util.ArrayList;

import static com.pgames.flow.firebaseHandler.FirebaseHandler.mData;
import static com.pgames.flow.firebaseHandler.FirebaseHandler.mUser;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DataSnapshot snapshot;
    private String[] allUsers;
    private ArrayList<MyListData> myListData;
    private static final String TAG = "HomeFragment";
    private Button setlocation,getlocation;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Typeface roboto = Typeface.createFromAsset(getContext().getAssets(),
                "font/Roboto-Bold.ttf"); //use this.getAssets if you are calling from an Activity
        //txtView.setTypeface(roboto);
        myListData = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        final MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
//        setlocation = (Button) root.findViewById(R.id.setlocation);
//        getlocation = (Button) root.findViewById(R.id.getlocation);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/geofire");
//       final GeoFire geoFire = new GeoFire(ref);
//        setlocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973));
//                geoFire.setLocation("firebase-hq", new GeoLocation(37.7853889, -122.4056973), new GeoFire.CompletionListener() {
//
//                    @Override
//                    public void onComplete(String key, DatabaseError error) {
//                        if (error != null) {
//                            System.err.println("There was an error saving the location to GeoFire: " + error);
//                            Log.e(TAG,"There was an error saving the location to GeoFire: " + error);
//                        } else {
//                            System.out.println("Location saved on server successfully!");
//                            Log.i(TAG,"Location saved on server successfully!");
//                        }
//                    }
//                });
//
//            }
//        });
//
//        getlocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                geoFire.getLocation("firebase-hq", new LocationCallback() {
//                    @Override
//                    public void onLocationResult(String key, GeoLocation location) {
//                        if (location != null) {
//                            System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));
//                        Log.i(TAG,String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));
//                            Toast.makeText(getContext(), String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude), Toast.LENGTH_SHORT).show();
//                        } else {
//                            System.out.println(String.format("There is no location for key %s in GeoFire", key));
//                            Log.i(TAG,String.format("There is no location for key %s in GeoFire", key));
//                            Toast.makeText(getContext(), String.format("There is no location for key %s in GeoFire", key), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        System.err.println("There was an error getting the GeoFire location: " + databaseError);
//                        Log.e(TAG,"There was an error getting the GeoFire location: " + databaseError);
//                        Toast.makeText(getContext(), "There was an error getting the GeoFire location: " + databaseError, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        if (mUser!=null){
            try {
//                String s = mData.child("Address").child("District").push().getKey();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user-availability-area").child("Ahmednagar");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast.makeText(getContext(), dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            FirebaseDatabase.getInstance().getReference().child("user-availability-area").child("Ahmednagar").child(dataSnapshot1.getKey())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            try {
                                                String name="",
                                                        phone="",
                                                        address="",
                                                        profilePic = "",
                                                        work="";

                                                if (dataSnapshot.exists()){
                                                    if (dataSnapshot.child("Name").getValue()!=null)
                                                        name = dataSnapshot.child("Name").getValue().toString();
                                                    if (dataSnapshot.child("Phone").getValue()!=null)
                                                        phone = dataSnapshot.child("Phone").getValue().toString();
                                                    if (dataSnapshot.child("Address").getValue()!=null)
                                                        address = dataSnapshot.child("Address").getValue().toString();
                                                    if (dataSnapshot.child("Profile-Pic").getValue()!=null)
                                                        profilePic = dataSnapshot.child("Profile-Pic").getValue().toString();
                                                    if (dataSnapshot.child("work").getValue()!=null)
                                                        work = dataSnapshot.child("work").getValue().toString();

//                                                    Toast.makeText(getContext(), name+phone+address+profilePic+work, Toast.LENGTH_SHORT).show();
                                                    MyListData listData = new MyListData(name,phone,address,profilePic,work);

                                                    myListData.add(listData);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }catch (Exception e){
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
//                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
               //                DatabaseReference reference =
//                Log.i(TAG,s);

            }catch (Exception e){
                Log.e(TAG,"Exception",e);
            }

        }

          return root;
    }

    private void getPersonalData(final MyListAdapter adapter, DataSnapshot childSnap){
        FirebaseDatabase.getInstance().getReference().child("user-availability-area").child("Ahmednagar").child(childSnap.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            String name="",
                                    phone="",
                                    address="",
                                    profilePic = "",
                                    work="";

                            if (dataSnapshot.exists()){
                                if (dataSnapshot.child("Name").getValue()!=null)
                                    name = dataSnapshot.child("Name").getValue().toString();
                                if (dataSnapshot.child("Phone").getValue()!=null)
                                    phone = dataSnapshot.child("Phone").getValue().toString();
                                if (dataSnapshot.child("Address").getValue()!=null)
                                    address = dataSnapshot.child("Address").getValue().toString();
                                if (dataSnapshot.child("Profile-Pic").getValue()!=null)
                                    profilePic = dataSnapshot.child("Profile-Pic").getValue().toString();
                                if (dataSnapshot.child("work").getValue()!=null)
                                    work = dataSnapshot.child("work").getValue().toString();
                                MyListData listData = new MyListData(name,phone,address,profilePic,work);

                                myListData.add(listData);
                                adapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getuserList(String district, final String work){

    }



}