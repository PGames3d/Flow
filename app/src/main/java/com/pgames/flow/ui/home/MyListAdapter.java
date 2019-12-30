package com.pgames.flow.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.pgames.flow.R;
import com.pgames.flow.home;

import java.util.ArrayList;

import static android.widget.ListPopupWindow.MATCH_PARENT;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.startActivity;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {
    private ArrayList<MyListData> listdata;
    private static Context mContext;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<MyListData> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public MyListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyListData myListData = listdata.get(position);
        holder.name.setText(listdata.get(position).getName());
//        holder.phone.setText(listdata.get(position).getPhone());
        holder.address.setText(listdata.get(position).getAddress());
        //   holder.imageView.setImageResource(listdata.get(position).getImgId());
        holder.work.setText(listdata.get(position).getWork());

//        Glide.with(mContext).load(listdata.get(position).getProfilePic()).apply(RequestOptions.circleCropTransform()).into(holder.profileImg);
        Glide.with(mContext)
                .load(listdata.get(position).getProfilePic())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.profileImg);

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
                        callIntent.setData(Uri.parse("tel:"+listdata.get(position).getPhone()));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ) {
                            Toast.makeText(mContext, "First enable Calling permissions in settings.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        mContext.startActivity(callIntent);


                    }
                });

            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //   public ImageView imageView;
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
