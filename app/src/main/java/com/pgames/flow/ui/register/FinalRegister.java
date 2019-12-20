package com.pgames.flow.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pgames.flow.CustomFragmentEvent;
import com.pgames.flow.DataTransportor;
import com.pgames.flow.MainActivity;
import com.pgames.flow.R;
import com.pgames.flow.firebaseHandler.*;
import com.pgames.flow.noInternet;
import com.pgames.flow.ui.register.finalMessage;
import com.pgames.flow.ui.splash.splash;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.pgames.flow.DataTransportor.mDataMap;
import static com.pgames.flow.ui.splash.splash.checkInternet;


public class FinalRegister extends Fragment implements FirebaseHandler {

    private Button mFinalRegisterProcess;
    private Spinner mUserType;
    private  Spinner mGetRequirement;
    private DataSnapshot innerSnapshot;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_final_register, container, false);
        mFinalRegisterProcess = (Button) view.findViewById(R.id.final_register_proceed);
        mUserType = (Spinner) view.findViewById(R.id.userTupe);
        mGetRequirement = (Spinner) view.findViewById(R.id.getRequirement);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    if (checkInternet.isOnline()) {
                        Toast.makeText(getContext(),"Online",Toast.LENGTH_LONG);
                        //nextAction();
                    }
                    else {
                        Thread.currentThread().interrupt();
                        if (splash.isInternet) {
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                            CustomFragmentEvent event = new CustomFragmentEvent(new noInternet(),R.id.main_host_fragment);
                            EventBus.getDefault().post(event);
                        }
                    }
                } catch (Exception e) {
                    // Thread.currentThread().interrupt();
                    // MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                    e.printStackTrace();
                }

            }
        }).start();




        if(mUser!=null) {
                        Toast.makeText(getContext(), "User Type", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("UserType").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final List<String> userType = new ArrayList<String>();
                                final List<String> getRequirement = new ArrayList<String>();

                                innerSnapshot = dataSnapshot;
                                userType.add("");
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    String userTypeName = userSnapshot.getKey();
                                    userType.add(userTypeName);
                                }

                                Collections.sort(userType);
                                userType.set(0, "Select User Type");

                                ArrayAdapter<String> userTypeAdapter = new ArrayAdapter<String>(getContext(), R.layout.custome_spinner, userType);
                                userTypeAdapter.setDropDownViewResource(R.layout.custome_spinner);
                                mUserType.setAdapter(userTypeAdapter);


                                mUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        getRequirement.clear();
                                        getRequirement.add("");

                                        if (!mUserType.getSelectedItem().toString().equals("Select District"))
                                            mGetRequirement.setVisibility(View.VISIBLE);
                                        else
                                            mGetRequirement.setVisibility(View.INVISIBLE);

                                        for (DataSnapshot getRequirementSnapshot : innerSnapshot.child(mUserType.getSelectedItem().toString()).getChildren()) {
                                            String getReqName = getRequirementSnapshot.getKey();
                                            getRequirement.add(getReqName);
                                        }


                                        Collections.sort(getRequirement);
                                        getRequirement.set(0, "Select Requirement");
                                        ArrayAdapter<String> getReqAdapter = new ArrayAdapter<String>(getContext(), R.layout.custome_spinner, getRequirement);
                                        getReqAdapter.setDropDownViewResource(R.layout.custome_spinner);
                                        mGetRequirement.setAdapter(getReqAdapter);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });

                                mGetRequirement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        if (!mGetRequirement.getSelectedItem().toString().equals("Select Requirement"))
                                            mFinalRegisterProcess.setVisibility(View.VISIBLE);
                                        else
                                            mFinalRegisterProcess.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
//
//                                mFinalRegisterProcess.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        mData.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//
//                                                try {
//
//                                                    //if starts here
//                                                    if (!dataSnapshot.child("User-Type").exists()){
//
//                                                        mUserMap.put("Personal/Profile-Status","complete");
//                                                        mUserMap.put("User-Type/User",mUserType.getSelectedItem().toString());
//                                                        mUserMap.put("User-Type/Requirement",mGetRequirement.getSelectedItem().toString());
//                                                        mData.updateChildren(mUserMap);
//                                                        nextAction();
//                                                    }else {
//
//                                                        nextAction();
//                                                    }
//                                                    //if ends here
//
//                                                }catch (Exception e){
//                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//
//
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//                                    }
//
//
//                                });

                            mFinalRegisterProcess.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDataMap.put("Personal/Profile-Status","complete");
                                    mDataMap.put("User-Type/User",mUserType.getSelectedItem().toString());
                                    mDataMap.put("User-Type/Requirement",mGetRequirement.getSelectedItem().toString());
                                    mData.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            try {
                                                DataTransportor transportor = new DataTransportor();
                                                mData.updateChildren(transportor.getmUserMap());
                                                nextAction();
                                            }catch (Exception e){
                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





        }
                return view;
            }




    @Override
    public void addData() {
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                try {

                    //if starts here
                    if (!dataSnapshot.child("User-Type").exists()){

                        mUserMap.put("Personal/Profile-Status","complete");
                        mUserMap.put("User-Type/User",mUserType.getSelectedItem().toString());
                        mUserMap.put("User-Type/Requirement",mGetRequirement.getSelectedItem().toString());
                        mData.updateChildren(mUserMap);
                        nextAction();
                    }else {

                        nextAction();
                    }
                    //if ends here

                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void nextAction() {
//        UserLogin login = new UserLogin();
//        login.userIsLoggedIn();

        CustomFragmentEvent event = new CustomFragmentEvent(new finalMessage(),R.id.main_host_fragment);
        EventBus.getDefault().post(event);
    }
}