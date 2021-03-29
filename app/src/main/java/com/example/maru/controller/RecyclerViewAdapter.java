package com.example.maru.controller;

import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final List<Meeting> listOfMeeting;

    public RecyclerViewAdapter(List<Meeting> listOfMeeting) {
        this.listOfMeeting = listOfMeeting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meetinglistitem, parent, false);
            return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = listOfMeeting.get(position);
        holder.mItemListName.setText(meeting.getSubjectOfMeeting());
        holder.mItemOfListOfMeeting.setColorFilter( /*getRandomColorFilter()*/ R.color.teal_200);
        holder.mItemDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Ça marche!", Toast.LENGTH_SHORT).show(); }
        });
    }

    private ColorFilter getRandomColorFilter() {
        ColorFilter colorFilter = new ColorFilter();

        return colorFilter;
    }

    @Override
    public int getItemCount() {
        return listOfMeeting.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_meeting)
        public ImageView mItemOfListOfMeeting;
        @BindView(R.id.item_list_name)
        public TextView mItemListName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mItemDeleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
