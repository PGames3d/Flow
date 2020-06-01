package com.pgames.flow;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmpPostJob extends DialogFragment {
    private Button mClose;
    private Button mPost;
    private EditText title;
    private EditText desc;
    private EditText company;
    private EditText location;
    private EditText skill;
    private CheckBox isEducation;
    private EditText education;
    private DatabaseReference databaseRef ;
    private FirebaseUser user ;
    private DatabaseReference mCurrentUser ;
    private static Context context;
    public EmpPostJob() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emp_post_job, container, false);
        initialize(view);

        context = getContext();
        databaseRef =  FirebaseDatabase.getInstance().getReference().child("posts");
        user= FirebaseAuth.getInstance().getCurrentUser();
        mCurrentUser = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid());
//        setStyle(DialogFragment.STYLE_NORMAL,
//                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Work Fine", Toast.LENGTH_SHORT).show();
                Log.e("Post", "Work Fine");
                getActivity().onBackPressed();
            }
        });

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPost.setVisibility(View.GONE);
                if (user != null) {
                    mCurrentUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.e("Title",title.getText().toString());
                            Log.e("Desc",desc.getText().toString());
                            Log.e("Company",company.getText().toString());
                            Log.e("Locationn",location.getText().toString());
                            Log.e("Skill",skill.getText().toString());
                            Log.e("IsEducation",Boolean.toString(isEducation.isChecked()));
                            Log .e("Eduaction",education.getText().toString());
                            Log.e("Phone",dataSnapshot.child("Phone").getValue().toString());
                            Log.e("Name",dataSnapshot.child("Name").getValue().toString());
                            Log.e("Posted",ServerValue.TIMESTAMP.toString());

                            final DatabaseReference newPost = databaseRef.child(dataSnapshot.child("District").getValue().toString()).push();
                            final DatabaseReference currentPost = mCurrentUser.child("posts").child(newPost.getKey());

                            String isEdu = isEducation.isChecked() ? "Required" : "Non";

                            Map<String,Object> postMap = new HashMap<>();
                            postMap.put("Title",title.getText().toString());
                            postMap.put("Desc",desc.getText().toString());
                            postMap.put("Company",company.getText().toString());
                            postMap.put("Location",location.getText().toString());
                            postMap.put("Skill",skill.getText().toString());
                            postMap.put("IsEducation",isEdu);
                            postMap.put("Education",education.getText().toString());
                            postMap.put("Phone",dataSnapshot.child("Phone").getValue().toString());
                            postMap.put("Name",dataSnapshot.child("Name").getValue().toString());
                            postMap.put("Posted", ServerValue.TIMESTAMP);
                            postMap.put("area",dataSnapshot.child("Taluka").getValue().toString()+"_"+dataSnapshot.child("Goan").getValue().toString());
                            currentPost.updateChildren(postMap);
                            newPost.updateChildren(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context, "Posted", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }
                            });

//                            newPost.child("Title").setValue(title.getText().toString());
//                            newPost.child("Desc").setValue(desc.getText().toString());
//                            newPost.child("Company").setValue(company.getText().toString());
//                            newPost.child("Location").setValue(location.getText().toString());
//                            newPost.child("Skill").setValue(skill.getText().toString());
//                            newPost.child("isEducation").setValue(isEducation.isChecked());
//                            newPost.child("Education").setValue(education.getText().toString());
//                            newPost.child("Phone").setValue(dataSnapshot.child("Phone").getValue().toString());
//                            newPost.child("Name").setValue(dataSnapshot.child("Name").getValue().toString())
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }
        });
        return view;
    }

    private void initialize(View view) {
        mClose = view.findViewById(R.id.emp_close_post);
        mPost = view.findViewById(R.id.emp_post_btn);
        title = view.findViewById(R.id.work_title);
        desc = view.findViewById(R.id.work_desc);
        company = view.findViewById(R.id.work_company);
        location = view.findViewById(R.id.work_location);
        skill = view.findViewById(R.id.work_skill);
        isEducation = view.findViewById(R.id.is_education);
        education = view.findViewById(R.id.education);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}
