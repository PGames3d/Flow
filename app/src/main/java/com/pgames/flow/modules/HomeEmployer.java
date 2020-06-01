package com.pgames.flow.modules;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pgames.flow.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeEmployer extends Fragment {

    private FirebaseUser user;
    private RecyclerView recyclerView;
    private PostViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public HomeEmployer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home_employer, container, false);

        recyclerView = view.findViewById(R.id.emp_post_added);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference query = FirebaseDatabase.getInstance().getReference()
                .child("user").child(user.getUid()).child("posts");

        Log.e("Key",query.getKey());
        FirebaseRecyclerOptions<PostView> options =
                new FirebaseRecyclerOptions.Builder<PostView>()
                        .setQuery(query, PostView.class)
                        .build();

        Log.e("Test","Running");
        adapter = new PostViewAdapter(options);
        recyclerView.setAdapter(adapter);


        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
