package com.pgames.flow.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.DataTransportor;
import com.pgames.flow.R;
import com.pgames.flow.firebaseHandler.*;
import com.pgames.flow.HomeActivity;

import java.util.HashMap;
import java.util.Map;

public class finalMessage extends Fragment{
    private Button button;
    public FinalRegister finalRegister;
    private userFlag userFlag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_message, container, false);

        button = view.findViewById(R.id.sucess_done);

//        userFlag.autoProcess();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNewData();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                    getActivity().finish();
               }
            });

    return view;
    }


    private void addNewData(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.i("user",user.getDisplayName());
        if (user!=null){



            //Access all user data saved in UserMap
            final DataTransportor transportor = new DataTransportor();
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user-info")
                    .child(transportor.getmUserMap().get("District").toString())
                    .child(transportor.getmUserMap().get("User").toString());
//            Log.i("test",databaseReference.toString());

            //push the current data into new key
            final DatabaseReference newPushedRef = databaseReference.push();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("User",user.getUid());
                    Map<String, Object>  userInfo = new HashMap<>();
                    userInfo.put("Name",transportor.getmUserMap().get("Name"));
                    userInfo.put("Phone",user.getPhoneNumber());
                    userInfo.put("Taluka",transportor.getmUserMap().get("Taluka"));
                    userInfo.put("Goan",transportor.getmUserMap().get("Goan"));
                    userInfo.put("Requirement",transportor.getmUserMap().get("Requirement"));
                    newPushedRef.updateChildren(userInfo);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i("test",databaseError.getMessage());
                }
            });

        }


    }

}
