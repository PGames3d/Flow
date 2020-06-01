package com.pgames.flow.modules;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgames.flow.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashRecentCall extends Fragment {

    public DashRecentCall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_recent_call, container, false);
    }
}
