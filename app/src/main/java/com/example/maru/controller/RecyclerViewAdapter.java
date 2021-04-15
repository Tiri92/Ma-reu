package com.example.maru.controller;

import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;

import java.util.List;
import java.util.Random;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = listOfMeeting.get(position);

        holder.mEmailList.setText(getEmailParticipantList(meeting));
        holder.mItemListName.setText(getTitleOfMeeting(meeting));
        holder.mItemOfListOfMeeting.setColorFilter(getRandomColor(holder.itemView.getResources()));
        holder.mItemDeleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Réunion supprimé", Toast.LENGTH_SHORT).show();
                DI.getMeetingService().deleteMeeting(meeting);
                notifyDataSetChanged();
            }
        });

    }

    @ColorInt
    int getRandomColor(Resources resources) {
        @ColorRes int[] colorArray = {R.color.ROSE, R.color.MAUVE, R.color.BLUE, R.color.VIOLET, R.color.ROUGE, R.color.BLEUCIEL,
                                      R.color.VERTCLAIR, R.color.JAUNEBEIGE, R.color.BEIGE, R.color.JAUNEORANGE, R.color.SAUMON,
                                      R.color.ORANGE, R.color.CYAN  };

        int randomIndex = new Random().nextInt(colorArray.length);
        @ColorRes int randomColorResId = colorArray[randomIndex];
        @ColorInt int randomColorInt = ResourcesCompat.getColor(resources, randomColorResId, null);
        return randomColorInt;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
