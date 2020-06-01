package com.pgames.flow.modules;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pgames.flow.R;

public class PostViewAdapter extends FirebaseRecyclerAdapter<PostView, PostViewAdapter.ViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PostViewAdapter(@NonNull FirebaseRecyclerOptions<PostView> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull PostView model) {
        holder.Title.setText(model.getTitle());
        holder.Desc.setText(model.getDesc());
        holder.EmpName.setText(model.getName());
        holder.Phone.setText(model.getPhone());
        holder.Skill.setText(model.getSkill());
        holder.IsEducation.setText(model.getIsEducation());
        holder.Education.setText(model.getEducation());
        holder.Company.setText(model.getCompany());
        holder.Location.setText(model.getLocation());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emp_post, parent, false);

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView Company;
        public TextView Desc;
        public TextView Education;
        public TextView Location;
        public TextView EmpName;
        public TextView Phone;
        public TextView Skill;
        public TextView Title;
        public TextView IsEducation;

        public ViewHolder(@NonNull View v) {
            super(v);
            this.Company = (TextView) v.findViewById(R.id.e_company);
            this.Desc = v.findViewById(R.id.e_desc);
            this.Education = v.findViewById(R.id.e_education);
            this.Location = v.findViewById(R.id.e_location);
            this.EmpName = v.findViewById(R.id.e_emp_name_post);
            this.Phone = v.findViewById(R.id.e_emp_phone_post);
            this.Skill = v.findViewById(R.id.e_skill);
            this.Title = v.findViewById(R.id.e_title);
            this.IsEducation = v.findViewById(R.id.e_isEdu);
        }
    }
}
