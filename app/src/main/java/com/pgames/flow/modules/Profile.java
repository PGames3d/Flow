package com.pgames.flow.modules;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.MainActivity;
import com.pgames.flow.R;
import com.pgames.flow.ui.login.Login;

import java.util.HashMap;
import java.util.Map;


public class Profile extends Fragment {

    private ImageView mProfilePhoto;
    private TextView mProfileName;
    private TextView mProfilePhone;
    private TextView mProfileType;
    private TextView mProfileAddress;
    private String name;
    private Button mSignOut;
    private Button mUpdateProfile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

             View view = inflater.inflate(R.layout.fragment_profile, container, false);
                initialize(view);
                setProfile(view);

            mSignOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();

                }
            });
        return view;

    }
    private void initialize(View view){
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mProfileName = view.findViewById(R.id.profile_name);
        mProfilePhone = view.findViewById(R.id.profile_phone);
        mProfileType = view.findViewById(R.id.profile_type);
        mProfileAddress = view.findViewById(R.id.profile_address);
        mSignOut = view.findViewById(R.id.sign_out);
    }


    private void setProfile(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.child("Name").getValue().equals(""))
                        mProfileName.setText(dataSnapshot.child("Name").getValue().toString());
                    if(!dataSnapshot.child("Phone").getValue().equals(""))
                        mProfilePhone.setText(dataSnapshot.child("Phone").getValue().toString());
//                    if(!dataSnapshot.child("User-Type").child("User").getValue().equals(""))
//                        mProfileType.setText(dataSnapshot.child("User-Type").child("User").getValue().toString());
                    if(!dataSnapshot.child("User").getValue().equals(""))
                        if(dataSnapshot.child("User").getValue().equals("Normal"))
                            mProfileType.setText("I am "+dataSnapshot.child("Name").getValue().toString()+" user");
                        else
                            mProfileType.setText("I am "+dataSnapshot.child("Name").getValue().toString());
                    if(!dataSnapshot.child("Goan").getValue().equals("")&&
                            !dataSnapshot.child("Taluka").getValue().equals(""))
                        mProfileAddress.setText(dataSnapshot.child("Goan").getValue().toString()+","+
                        dataSnapshot.child("Taluka").getValue().toString());


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Profile",databaseError.getDetails());
                }
            });
        }
    }

}
