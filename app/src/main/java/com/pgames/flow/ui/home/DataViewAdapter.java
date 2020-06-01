package com.pgames.flow.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pgames.flow.R;


public class DataViewAdapter extends FirebaseRecyclerAdapter<DataView, DataViewAdapter.ViewHolder> {

    public DataViewAdapter(@NonNull FirebaseRecyclerOptions<DataView> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final DataView model) {
    try {
        Log.e("position", Integer.toString(position));
        Log.e("Name",model.getName());
        Log.e("Phone",model.getPhone());
        Log.e("Requirement",model.getRequirement());
        Log.e("Goan",model.getGoan());
        Log.e("Taluka",model.getTaluka());
        Log.e("CardName",DataHomeFragment.cardNameValue);
            holder.name.setText(model.getName());
//        holder.phone.setText(listdata.get(position).getPhone());
            holder.address.setText(model.getGoan() + "," + model.getTaluka());
            //   holder.imageView.setImageResource(listdata.get(position).getImgId());
            holder.work.setText(model.getRequirement());

//        Glide.with(mContext).load(listdata.get(position).getProfilePic()).apply(RequestOptions.circleCropTransform()).into(holder.profileImg);
//        Glide.with(mContext)
//                .load(listdata.get(position).getProfilePic())
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(holder.profileImg);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   Toast.makeText(view.getContext(), "click on item: " + myListData.getName(), Toast.LENGTH_SHORT).show();
                    holder.linearLayout.setBackground(holder.cardView.getBackground());
                    holder.call.setVisibility(View.VISIBLE);
                    holder.call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //             Toast.makeText(mContext, listdata.get(position).getPhone() + "Calling", Toast.LENGTH_SHORT).show();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            callIntent.setData(Uri.parse("tel:" + model.getPhone()));
                        if (ActivityCompat.checkSelfPermission(DataHomeFragment.DataFragmentContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                            Toast.makeText(DataHomeFragment.DataFragmentContext, "First enable Calling permissions in settings.", Toast.LENGTH_LONG).show();
                            return;
                        }
                                      DataHomeFragment.DataFragmentContext.startActivity(callIntent);


                        }
                    });

                }
            });

    }catch (Exception e){
        Log.e("Holder",e.getMessage());
    }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;
        public TextView address;
        public TextView work;
        public ImageView profileImg;
        public LinearLayout linearLayout;
        public CardView cardView;
        public Button call;
        public ViewHolder(View itemView) {
            super(itemView);
            //         this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.phone = (TextView) itemView.findViewById(R.id.phone);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.work = (TextView) itemView.findViewById(R.id.work);
            this.profileImg = (ImageView) itemView.findViewById(R.id.profile_icon);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.row_user);
            this.cardView = (CardView) itemView.findViewById(R.id.card_user);
            this.call = (Button) itemView.findViewById(R.id.call);
        }
    }
}


