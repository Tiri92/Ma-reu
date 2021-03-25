package com.example.maru.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.maru.R;
import com.example.maru.model.Meeting;
import com.example.maru.service.MeetingService;
import com.example.maru.service.LocalMeetingService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.RecyclerView);

        MeetingService meetingService = new LocalMeetingService();

        List<Meeting> listOfMeeting = meetingService.getMeetingList();
    }
}