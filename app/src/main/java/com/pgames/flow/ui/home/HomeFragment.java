package com.pgames.flow.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.R;

public class HomeFragment extends Fragment {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static String dist = "district";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //initialization of all the elements or views in home fragment
        initialize(view);
        getDist();


        return view;
    }

    private void initialize(View view) {
        CardView mBarber = view.findViewById(R.id.card_barber);
        CardView mCook = view.findViewById(R.id.card_cook);
        CardView mCleaner = view.findViewById(R.id.card_cleaner);
        CardView mDriver = view.findViewById(R.id.card_driver);
        CardView mOffBoy = view.findViewById(R.id.card_office_boy);
        CardView mSalePerson = view.findViewById(R.id.card_sales_person);
        CardView mPlumber = view.findViewById(R.id.card_plumber);
        mBarber.setOnClickListener(mCardListener);
        mCook.setOnClickListener(mCardListener);
        mCleaner.setOnClickListener(mCardListener);
        mDriver.setOnClickListener(mCardListener);
        mOffBoy.setOnClickListener(mCardListener);
        mSalePerson.setOnClickListener(mCardListener);
        mPlumber.setOnClickListener(mCardListener);
    }


    ////Click listener
    private View.OnClickListener mCardListener = new View.OnClickListener() {
        String selectedCard = null;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.card_barber:
                    selectedCard = "Barber";
                    break;
                case R.id.card_cook:
                    selectedCard = "Cook";
                    break;
                case R.id.card_cleaner:
                    selectedCard = "Cleaner";
                    break;
                case R.id.card_driver:
                    selectedCard = "Driver";
                    break;
                case R.id.card_office_boy:
                    selectedCard = "OfficeBoy";
                    break;
                case R.id.card_sales_person:
                    selectedCard = "SalePerson";
                    break;
                case R.id.card_plumber:
                    selectedCard = "Plumber";
                    break;
            }

            DataHomeFragment dataHomeFragment = new DataHomeFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            Bundle args = new Bundle();
            args.putString("CardName",selectedCard);
            args.putString("district",dist);
            dataHomeFragment.setArguments(args);
            dataHomeFragment.setTargetFragment(HomeFragment.this,1);
            dataHomeFragment.show(ft, "dialog");
//            dataHomeFragment.setStyle(DialogFragment.STYLE_NORMAL,
//                    android.R.style.Theme_DeviceDefault_Light_NoActionBar);
        }
    };


    private void getDist(){
        if (user!=null){
            try {
                DatabaseReference distRef = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
                distRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.child("District").getValue().equals(""))
                            dist = dataSnapshot.child("District").getValue().toString();
                        Log.e("District",dist);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.e("District Reference",databaseError.getDetails());
                    }
                });
            }catch (Exception e){
                Log.e("Exception",e.getMessage());
            }

        }
    }

}

