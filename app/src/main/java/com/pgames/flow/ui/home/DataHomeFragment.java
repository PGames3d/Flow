package com.pgames.flow.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.R;
import com.pgames.flow.modules.Profile;
import com.pgames.flow.ui.home.MyListAdapter;
import com.pgames.flow.ui.home.MyListData;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataHomeFragment extends DialogFragment {
    private static String dist = "district";
    private DataViewAdapter adapter;
    private RecyclerView recyclerView;
    public static String cardNameValue;
    public static Context DataFragmentContext;
    private FirebaseRecyclerOptions<DataView> options;
    private Boolean isQueryEmpty = false;
    private AlertDialog.Builder mNotFound;
    private Query query;
    private View view;
    private TextView err;
    private RelativeLayout errLayout;
    private Button err_close;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_data_home, container, false);
        initialize(view);
        cardNameValue = getArguments().getString("CardName");
        dist = getArguments().getString("district");
        DataFragmentContext = getContext();
        Log.e("District DataHomeFrag", dist);
        Log.e("cardNameValue", cardNameValue);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        query = FirebaseDatabase.getInstance().getReference()
                .child("user-info")
                .child(dist)
                .child("Job Seeker")
                .orderByChild("Requirement")
                .equalTo(cardNameValue);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    errLayout.setVisibility(View.VISIBLE);
                    err.setText("Currently non of the " + cardNameValue + "'s registered on flow please do one share to find someone that you want... ");
                   // err.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Query error", databaseError.getMessage());
            }
        });

        options = new FirebaseRecyclerOptions.Builder<DataView>()
                .setQuery(query, DataView.class)
                .build();
        adapter = new DataViewAdapter(options);
        recyclerView.setAdapter(adapter);

        err_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().onBackPressed();
            }
        });
        return view;
    }

    private void initialize(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.home_recycler);
        err = view.findViewById(R.id.err_msg);
        errLayout = view.findViewById(R.id.err_layout);
        err_close = view.findViewById(R.id.err_close);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
