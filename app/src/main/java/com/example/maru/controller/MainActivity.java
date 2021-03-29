package com.example.maru.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.model.Meeting;

import java.util.List;

import static com.example.maru.di.DI.meetingService;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.RecyclerView);

        List<Meeting> listOfMeeting = meetingService.getMeetingList();
        recyclerView.setAdapter(new RecyclerViewAdapter(listOfMeeting));
    }
}