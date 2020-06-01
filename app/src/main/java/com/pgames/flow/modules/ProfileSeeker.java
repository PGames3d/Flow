package com.pgames.flow.modules;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.pgames.flow.MainActivity;
import com.pgames.flow.R;

public class ProfileSeeker extends Fragment {
    private Button mSignOut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_seeker, container, false);
        initialization(view);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

            }
        });
        return view;
    }

    private void initialization(View view){
        mSignOut = view.findViewById(R.id.seek_sign_out);
    }
}
