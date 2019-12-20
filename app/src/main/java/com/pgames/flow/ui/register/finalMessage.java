package com.pgames.flow.ui.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pgames.flow.R;
import com.pgames.flow.firebaseHandler.*;
import com.pgames.flow.home;

public class finalMessage extends Fragment{
    private Button button;
    public FinalRegister finalRegister;
    private userFlag userFlag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final_message, container, false);

        button = view.findViewById(R.id.sucess_done);

//        userFlag.autoProcess();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), home.class));
                    getActivity().finish();
               }
            });

    return view;
    }

}
