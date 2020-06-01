package com.pgames.flow.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.CustomFragmentEvent;
import com.pgames.flow.HomeActivity;
import com.pgames.flow.R;
import com.pgames.flow.ui.register.RegisterDetails;
import com.pgames.flow.network.CheckInternet;
import com.pgames.flow.noInternet;
import com.pgames.flow.ui.splash.splash;
import com.pgames.flow.utils.CountryToPhonePrefix;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class Login extends Fragment {
    public Button mSend;
    public EditText mPhoneNumber, mOTP;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String mVerificationId;
    private static String mProfileStatus;
    CheckInternet checkInternet;

//    public static FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_login, container, false);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (checkInternet.isOnline()) {
                        if (splash.mLoginFlag) {
                            splash.loginThread.interrupt();
                            userIsLoggedIn();
                        }
                    } else {
                        Thread.currentThread().interrupt();
                        if (splash.isInternet) {
                            CustomFragmentEvent event = new CustomFragmentEvent(new noInternet(), R.id.main_host_fragment);
                            EventBus.getDefault().post(event);
//                           MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                        }
                    }
                } catch (Exception e) {
                    // Thread.currentThread().interrupt();
                    // MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                    e.printStackTrace();
                }

            }
        }).start();


        mPhoneNumber = view.findViewById(R.id.mobile_input);
        mOTP = view.findViewById(R.id.otp_input);
        mSend = view.findViewById(R.id.start_otp_process);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                mOTP.setVisibility(View.VISIBLE);


                if (mVerificationId != null)
                    verifyPhoneNumberWithCode();
                else
                    startPhoneNumberVerification();

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
            }


            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                mVerificationId = verificationId;
                mOTP.setVisibility(View.VISIBLE);
                mSend.setText("Verify");
            }
        };


        return view;
    }

    private void verifyPhoneNumberWithCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, mOTP.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user != null) {

                        final DatabaseReference mUserDB = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                        mUserDB.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("Profile-Status", "init");
                                    userMap.put("Phone", user.getPhoneNumber());
                                    mUserDB.updateChildren(userMap);
                                    userIsLoggedIn();
                                } else {
                                    userIsLoggedIn();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                }

            }
        });
    }

    public void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
                            CustomFragmentEvent event = new CustomFragmentEvent(new RegisterDetails(), R.id.main_host_fragment);
                            EventBus.getDefault().post(event);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment,new RegisterDetails()).commit();
                            break;
                        case "complete":
                            startActivity(new Intent(getContext(), HomeActivity.class));
                            getActivity().finish();
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

    private void startPhoneNumberVerification() {
        String ISOPrefix = getCountryISO();
        String phone = mPhoneNumber.getText().toString();
        phone = phone.replace(" ", "");
        phone = phone.replace("-", "");
        phone = phone.replace("(", "");
        phone = phone.replace(")", "");

        if(!String.valueOf(phone.charAt(0)).equals("+"))
            phone = ISOPrefix + phone;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks);

    }


    private String getCountryISO() {
        String iso = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(getContext().TELEPHONY_SERVICE);
            if (telephonyManager.getNetworkCountryIso() != null)
                if (!telephonyManager.getNetworkCountryIso().toString().equals(""))
                    iso = telephonyManager.getNetworkCountryIso().toString();

        }catch (NullPointerException e){
            Log.e("Login","ISO Null"+iso);
        }

        return CountryToPhonePrefix.getPhone(iso);
    }
}