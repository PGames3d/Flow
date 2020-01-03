package com.pgames.flow.firebaseHandler;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.MainActivity;
import com.pgames.flow.R;
import com.pgames.flow.ui.register.FinalRegister;
import com.pgames.flow.ui.register.RegisterDetails;
import com.pgames.flow.ui.register.finalMessage;

public class UserLogin {
    private String mProfileStatus;

    public void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("Personal");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        //String str = dataSnapshot1.getValue().toString();
                        if (dataSnapshot1.getKey().equals("Profile-Status")) {
                            mProfileStatus = dataSnapshot1.getValue().toString();
                        }
                    }

                    switch (mProfileStatus) {
                        case "init":
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment,new RegisterDetails()).commit();
                            break;
                        case "half":
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment,new FinalRegister()).commit();

                            //Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            break;
                        case "complete":
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment,new finalMessage()).commit();
                            break;
                        default:
                            //Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

}
