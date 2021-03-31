package com.example.maru.controller;

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
import com.example.maru.di.DI;
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

        holder.mEmail_List.setText(getEmailParticipantList(meeting));
        holder.mItemListName.setText(getTitleOfMeeting(meeting));
        holder.mItemOfListOfMeeting.setColorFilter(R.color.purple_700);
        holder.mItemDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Réunion supprimé", Toast.LENGTH_SHORT).show();
                DI.getMeetingService().deleteMeeting(meeting);
                notifyItemRemoved(position);
            }
        });

    }

    private String getTitleOfMeeting(Meeting meeting) {
        return meeting.getPlaceOfMeeting() + " - " + meeting.getTimeOfMeeting() + " - " + meeting.getSubjectOfMeeting();
    }

    /**
     * "StringBuilder" is used to modify a "String", ".toString()" is used to cast a "StringBuilder" into
     * a "String" at the last to make a "return" for example, "append" is used to add something to the "String",
     * it's better than "add" and it optimize the code
     */
    private String getEmailParticipantList(Meeting meeting) {
        StringBuilder emailsOfParticipants = new StringBuilder();
        for(String mail:meeting.getListOfParticipants()){
            emailsOfParticipants.append(mail).append(" ");
        }
        return emailsOfParticipants.toString();
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
        @BindView(R.id.email_list)
        public TextView mEmail_List;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mItemDeleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
