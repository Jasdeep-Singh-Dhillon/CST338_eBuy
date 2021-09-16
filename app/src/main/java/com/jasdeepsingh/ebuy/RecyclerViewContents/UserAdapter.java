package com.jasdeepsingh.ebuy.RecyclerViewContents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.entities.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private List<User> mUsers;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onStarClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mUsername;
        public TextView mFirstName;
        public TextView mLastName;
        public ImageView mDeleteImage;
        public ImageView mAdminImage;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mUsername = itemView.findViewById(R.id.user_username);
            mFirstName = itemView.findViewById(R.id.user_first_name);
            mLastName = itemView.findViewById(R.id.user_last_name);
            mDeleteImage = itemView.findViewById(R.id.user_delete);
            mAdminImage = itemView.findViewById(R.id.user_admin);

            mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            mAdminImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onStarClick(position);
                        }
                    }
                }
            });
        }
    }

    public UserAdapter(List<User> users) {
        mUsers = users;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.ViewHolder holder, int position) {
        User currentItem = mUsers.get(position);
        holder.mUsername.setText(currentItem.getUsername());
        holder.mFirstName.setText(currentItem.getFirstName());
        holder.mLastName.setText(currentItem.getLastName());
        if(currentItem.getIsAdmin()) {
            holder.mAdminImage.setImageResource(R.drawable.star_filled);
        } else {
            holder.mAdminImage.setImageResource(R.drawable.star_outline);
        }
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
