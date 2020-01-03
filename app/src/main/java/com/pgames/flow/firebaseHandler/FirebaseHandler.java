package com.pgames.flow.firebaseHandler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public interface FirebaseHandler {
    //Required variables
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("user").child(mUser.getUid());
    DatabaseReference mValidateData = FirebaseDatabase.getInstance().getReference();
    Map<String,Object> mUserMap = new HashMap<>();


    //Required methods
    void addData();
    void nextAction();



}
