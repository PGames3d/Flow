package com.pgames.flow.ui.home;

import android.app.Person;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        myListData = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        final MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (mUser!=null){
            try {
                String s = mData.child("Address").child("District").push().getKey();
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
//                DatabaseReference reference =
                Log.i(TAG,s);

            }catch (Exception e){
                Log.e(TAG,"Exception",e);
            }

        }

          return root;
    }

    private void getPersonalData(final MyListAdapter adapter, DataSnapshot childSnap){
        FirebaseDatabase.getInstance().getReference().child("user").child(childSnap.getKey()).child("Personal")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            String name="",
                                    phone="",
                                    status="";
                            if (dataSnapshot.exists()){
                                if (dataSnapshot.child("Name").getValue()!=null)
                                    name = dataSnapshot.child("Name").getValue().toString();
                                if (dataSnapshot.child("Phone").getValue()!=null)
                                    phone = dataSnapshot.child("Phone").getValue().toString();
                                if (dataSnapshot.child("Profile-Status").getValue()!=null)
                                    status = dataSnapshot.child("Profile-Status").getValue().toString();

                                MyListData listData = new MyListData(name,phone,status);

                                myListData.add(listData);
                                adapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void getuserList(String district, final String work){

    }



}