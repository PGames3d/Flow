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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.CustomFragmentEvent;
import com.pgames.flow.MainActivity;
import com.pgames.flow.R;
import com.pgames.flow.firebaseHandler.FirebaseHandler;
import com.pgames.flow.firebaseHandler.UserLogin;
import com.pgames.flow.ui.login.Login;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.pgames.flow.DataTransportor.mDataMap;


public class RegisterDetails extends Fragment implements FirebaseHandler {
    private Button mRegiterProceed;
    private FirebaseDatabase firebaseDatabase;
    private Spinner mDistrict, mTaluka, mGoan;
    private DataSnapshot innerSnapshot;
    private EditText mYourName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_register_details, container, false);

        //Spiner initialization   ie.Select List
        mDistrict = (Spinner) view.findViewById(R.id.districtList);
        mTaluka = (Spinner) view.findViewById(R.id.talukaList);
        mGoan = (Spinner) view.findViewById(R.id.goanList);
        mRegiterProceed = view.findViewById(R.id.register_proceed);
        mYourName = view.findViewById(R.id.yourName);


//        mTaluka.setVisibility(View.INVISIBLE);
//        mGoan.setVisibility(View.INVISIBLE);
//        mRegiterProceed.setVisibility(View.INVISIBLE);
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (mUser != null) {


            firebaseDatabase.getReference().child("AddressDetails").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> district = new ArrayList<String>();
                    final List<String> taluka = new ArrayList<String>();
                    final List<String> goan = new ArrayList<String>();
                    innerSnapshot = dataSnapshot;
                    district.add("");
                    for (DataSnapshot districtSnapshot : dataSnapshot.child("District").getChildren()) {
                        String districtName = districtSnapshot.getKey();
                        district.add(districtName);
                    }

                    Collections.sort(district);
                    district.set(0, "Select District");

                    ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(getContext(), R.layout.custome_spinner, district);
                    districtAdapter.setDropDownViewResource(R.layout.custome_spinner);
                    mDistrict.setAdapter(districtAdapter);


                    mDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            taluka.clear();
                            taluka.add("");

                            if (!mDistrict.getSelectedItem().toString().equals("Select District"))
                                mTaluka.setVisibility(View.VISIBLE);
                            else
                                mTaluka.setVisibility(View.INVISIBLE);

                            for (DataSnapshot talukaSnapshot : innerSnapshot.child("District").child(mDistrict.getSelectedItem().toString()).getChildren()) {
                                String talukaName = talukaSnapshot.getKey();
                                taluka.add(talukaName);
                            }


                            Collections.sort(taluka);
                            taluka.set(0, "Select Taluka");
                            ArrayAdapter<String> talukaAdapter = new ArrayAdapter<String>(getContext(), R.layout.custome_spinner, taluka);
                            talukaAdapter.setDropDownViewResource(R.layout.custome_spinner);
                            mTaluka.setAdapter(talukaAdapter);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            mTaluka.setVisibility(View.INVISIBLE);
                        }
                    });

                    mTaluka.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            goan.clear();
                            goan.add("");

                            if (!mTaluka.getSelectedItem().toString().equals("Select Taluka"))
                                mGoan.setVisibility(View.VISIBLE);
                            else
                                mGoan.setVisibility(View.INVISIBLE);

                            for (DataSnapshot goanSnapshot : innerSnapshot.child("District").child(mDistrict.getSelectedItem().toString()).child(mTaluka.getSelectedItem().toString()).getChildren()) {
                                String goanName = goanSnapshot.getKey();
                                goan.add(goanName);
                            }
                            Collections.sort(goan);
                            goan.set(0, "Select Goan");
                            ArrayAdapter<String> goanAdapter = new ArrayAdapter<String>(getContext(), R.layout.custome_spinner, goan);
                            goanAdapter.setDropDownViewResource(R.layout.custome_spinner);
                            mGoan.setAdapter(goanAdapter);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            mGoan.setVisibility(View.INVISIBLE);
                        }
                    });

                    mGoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (!mGoan.getSelectedItem().equals("Select Goan"))
                                mRegiterProceed.setVisibility(View.VISIBLE);
                            else
                                mRegiterProceed.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//
//                            mRegiterProceed.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (mUser!=null){
//                                        //Toast.makeText(getContext(), mUser.getUid().toString(), Toast.LENGTH_LONG).show();
//
//                                        mData.addValueEventListener(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
//
//                                                try {
//
//                                                    //if starts here
//                                                    if (!dataSnapshot.child("Address").exists()){
////                        Map<String,Object> mUserMap = new HashMap<>();
//                                                        mUserMap.put("Personal/Name",mYourName.getText().toString());
//                                                        mUserMap.put("Personal/Profile-Status","half");
//                                                        mUserMap.put("Address/District",mDistrict.getSelectedItem().toString());
//                                                        mUserMap.put("Address/Taluka",mTaluka.getSelectedItem().toString());
//                                                        mUserMap.put("Address/Goan",mGoan.getSelectedItem().toString());
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
//                                    }
//                                }
//                            });

                    mRegiterProceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDataMap.put("Personal/Name", mYourName.getText().toString());
                            mDataMap.put("Personal/Profile-Status", "half");
                            mDataMap.put("Address/District", mDistrict.getSelectedItem().toString());
                            mDataMap.put("Address/Taluka", mTaluka.getSelectedItem().toString());
                            mDataMap.put("Address/Goan", mGoan.getSelectedItem().toString());
                            //mData.updateChildren(mUserMap);
                            nextAction();
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
        if (mUser != null) {
            //Toast.makeText(getContext(), mUser.getUid().toString(), Toast.LENGTH_LONG).show();

            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                    try {

                        //if starts here
                        if (!dataSnapshot.child("Address").exists()) {
//                        Map<String,Object> mUserMap = new HashMap<>();
                            mUserMap.put("Personal/Name", mYourName.getText().toString());
                            mUserMap.put("Personal/Profile-Status", "half");
                            mUserMap.put("Address/District", mDistrict.getSelectedItem().toString());
                            mUserMap.put("Address/Taluka", mTaluka.getSelectedItem().toString());
                            mUserMap.put("Address/Goan", mGoan.getSelectedItem().toString());
                            mData.updateChildren(mUserMap);
                            nextAction();
                        } else {

                            nextAction();
                        }
                        //if ends here

                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void nextAction() {
//
//        UserLogin login = new UserLogin();
//        login.userIsLoggedIn();
        CustomFragmentEvent event = new CustomFragmentEvent(new FinalRegister(), R.id.main_host_fragment);
        EventBus.getDefault().post(event);
    }


}