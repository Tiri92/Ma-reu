package com.example.maru.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.model.Meeting;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.maru.di.DI.meetingService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_meeting_button)
    ImageButton mAddMeetingButton;

    private RecyclerView recyclerView;
    RecyclerViewAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView = findViewById(R.id.RecyclerView);
        List<Meeting> listOfMeeting = meetingService.getMeetingList();
        mAdapter = new RecyclerViewAdapter(listOfMeeting);
        recyclerView.setAdapter(mAdapter);


        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), AddMeetingActivity.class);
            ActivityCompat.startActivity(v.getContext(), intent, null);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date_filter:
                return true;
            case R.id.menu_place_filter:
                return true;
            case R.id.no_filter:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }
}