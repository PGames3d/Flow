package com.pgames.flow.ui.splash;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pgames.flow.CustomFragmentEvent;
import com.pgames.flow.HomeActivity;
import com.pgames.flow.ui.login.Login;
import com.pgames.flow.R;
import com.pgames.flow.ui.register.RegisterDetails;
import com.pgames.flow.network.CheckInternet;
import com.pgames.flow.noInternet;

import org.greenrobot.eventbus.EventBus;


public class splash extends Fragment {
Handler handler;

    private String mProfileStatus;
   public static CheckInternet checkInternet;
   public static boolean mLoginFlag = false;
   public static Thread loginThread;
   public static boolean isInternet = false;
   ImageView imageView;
    Animation anim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        imageView=(ImageView) view.findViewById(R.id.imageView2); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //startActivity(new Intent(this,HomeActivity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);

         loginThread = new Thread(new Runnable() {


            public void run() {
                try {


                    if (checkInternet.isOnline()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            mLoginFlag = true;
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(user.getUid()).child("Personal");
                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            //String str = dataSnapshot1.getValue().toString();
                                            if (dataSnapshot1.getKey().equals("Profile-Status")) {
                                                mProfileStatus = dataSnapshot1.getValue().toString();

                                            }

                                        }
                                        try {


                                            switch (mProfileStatus) {
                                                case "init":
                                                    CustomFragmentEvent event = new CustomFragmentEvent(new RegisterDetails(), R.id.main_host_fragment);
                                                    //event.setFragment(new RegisterDetails());
                                                    //event.setId(R.id.main_host_fragment);
                                                    EventBus.getDefault().post(event);
//                                        MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment,new RegisterDetails()).commit();

                                                    break;
                                                case "complete":
                                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                                    getActivity().finish();
                                                    break;
                                                default:
                                                    //Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();

                                            }
                                        }catch (NullPointerException e){
//                                            Log.e("mProfile",e.getMessage());

                                            FirebaseAuth.getInstance().signOut();
                                            CustomFragmentEvent event = new CustomFragmentEvent(new Login(), R.id.main_host_fragment);
                                            EventBus.getDefault().post(event);

                                        }

                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    FirebaseAuth.getInstance().signOut();
                                }
                            });


                        }
                    } else {
                        Thread.currentThread().interrupt();
                        isInternet = true;
                        mLoginFlag = true;
//                        MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                       CustomFragmentEvent event = new CustomFragmentEvent(new noInternet(),R.id.main_host_fragment);
                        EventBus.getDefault().post(event);
                    }
                }catch (Exception e){
//                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                    //MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new noInternet()).commit();
                }
            }
        });

        loginThread.start();
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //MainActivity.fragmentManager.beginTransaction().remove(new splash()).commit();
//                getActivity().getFragmentManager().popBackStack();
                if (!mLoginFlag) {
                    loginThread.interrupt();
                   CustomFragmentEvent event = new CustomFragmentEvent(new Login(),R.id.main_host_fragment);
                    EventBus.getDefault().post(event);
//                    MainActivity.fragmentManager.beginTransaction().replace(R.id.main_host_fragment, new Login()).commit();

                }
          }
        },3000);

        return view;
    }

}
