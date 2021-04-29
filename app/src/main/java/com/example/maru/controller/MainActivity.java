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
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.util.Utils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_meeting_button)
    ImageButton mAddMeetingButton;

    private RecyclerView mRecyclerView;
    MeetingAdapter mAdapter;

    private List<Meeting> mMeetingList;

    private MaterialDatePicker mDatePicker;

    private LocalDate mFilterDate;

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

        mMeetingList = DI.getMeetingService().getMeetingList();
        mAdapter = new MeetingAdapter(mMeetingList);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAddMeetingButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddMeetingActivity.class);
                ActivityCompat.startActivity(v.getContext(), intent, null);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private void createDatePicker() {
        mDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        mDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mFilterDate = Utils.epochMilliToLocalDate((Long) selection);
                mRecyclerView.setAdapter(new MeetingAdapter(getMeetingFilterByDate()));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_date_filter:
                createDatePicker();
                mDatePicker.show(getSupportFragmentManager().beginTransaction(), "DATE_PICKER");
                return true;
            case R.id.menu_place_filter:
                return true;
            case R.id.no_filter:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public List<Meeting> getMeetingFilterByDate() {
        List<Meeting> filteredMeetingList = new ArrayList<>(mMeetingList);

        for (Iterator<Meeting> it = filteredMeetingList.iterator(); it.hasNext(); ) {
            Meeting meeting = it.next();
            if (!meeting.getDate().isEqual(mFilterDate)) {
                it.remove();
            }
        }
        return filteredMeetingList;
    }


}
