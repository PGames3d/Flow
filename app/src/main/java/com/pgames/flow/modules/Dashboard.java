package com.pgames.flow.modules;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pgames.flow.R;
import com.pgames.flow.ui.home.DataHomeFragment;

public class Dashboard extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_dashboard, container, false);
        getCount();

        return view;
    }

    private void getCount() {
        DatabaseReference countRef = FirebaseDatabase.getInstance().getReference().child("user-info");
    }


}
