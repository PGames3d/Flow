package com.pgames.flow.modules;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pgames.flow.EmpPostJob;
import com.pgames.flow.HomeActivity;
import com.pgames.flow.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class dashboard_employee extends Fragment {
private CardView mPost;

    public dashboard_employee() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_employee, container, false);
        initialize(view);
        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final DialogFragment postDialogue = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar);
//                postDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
//                postDialogue.setContentView(R.layout.fragment_emp_post_job);
//                postDialogue.setCancelable(true);
//                postDialogue.show();
           EmpPostJob postJob  = new EmpPostJob();
          FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
          Fragment prev =getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
          if (prev!=null){
              ft.remove(prev);
          }
          ft.addToBackStack(null);
            postJob.show(ft,"");
                //postJob.setStyle(postJob.STYLE_NORMAL,
                //android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            }
        });
        return view;
    }

    private void initialize(View view) {
    mPost = view.findViewById(R.id.emp_post);
    }

}
