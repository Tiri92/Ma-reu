package com.example.maru.controller;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;

import java.time.format.DateTimeFormatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private final List<Meeting> listOfMeeting;

    public MeetingAdapter(List<Meeting> listOfMeeting) {
        this.listOfMeeting = listOfMeeting;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = listOfMeeting.get(position);

        holder.mEmailList.setText(getEmailParticipantList(meeting));
        holder.mItemListName.setText(getTitleOfMeeting(meeting));
        holder.mSubjectOfMeeting.setText(meeting.getSubjectOfMeeting());
        holder.mItemOfListOfMeeting.setColorFilter(meeting.getColorOfMeeting());
        holder.mItemDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Réunion supprimé", Toast.LENGTH_SHORT).show();
                DI.getMeetingService().deleteMeeting(meeting);
                listOfMeeting.remove(meeting);
                notifyDataSetChanged();
            }
        });
    }

    private String getTitleOfMeeting(Meeting meeting) {
        String dateString = meeting.getDate().format(DateTimeFormatter.ofPattern("d LLL Y"));
        return meeting.getPlaceOfMeeting() + " - " + dateString + " - " + meeting.getTimeOfMeeting();
    }

    /**
     * "StringBuilder" is used to modify a "String", ".toString()" is used to cast a "StringBuilder" into
     * a "String" at the last to make a "return" for example, "append" is used to add something to the "String",
     * it's better than "add" and it optimize the code
     */
    private String getEmailParticipantList(Meeting meeting) {
        StringBuilder emailsOfParticipants = new StringBuilder();
        for (String mail : meeting.getListOfParticipants()) {
            emailsOfParticipants.append(mail).append("," + " ");
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
        public TextView mEmailList;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mItemDeleteButton;
        @BindView(R.id.subjectOfMeeting)
        public TextView mSubjectOfMeeting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
