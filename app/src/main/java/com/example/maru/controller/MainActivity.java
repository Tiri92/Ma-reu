package com.example.maru.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.util.Utils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_meeting_button)
    ImageButton mAddMeetingButton;

    private RecyclerView mRecyclerView;
    MeetingAdapter mAdapter;

    private MaterialDatePicker mDatePicker;

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

        mAdapter = new MeetingAdapter(DI.getMeetingService().getMeetingList());
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

    private AlertDialog createPlaceListPickerDialog() {
        String[] listOfPlace = {"Salle A", "Salle B", "Salle C", "Salle D", "Salle E", "Salle F", "Salle G", "Salle H", "Salle I", "Salle J"};
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichPlace) {
                String selectedPlace = listOfPlace[whichPlace];
                mRecyclerView.setAdapter(new MeetingAdapter(DI.getMeetingService().getMeetingFilterByPlace(selectedPlace)));
            }
        };
        AlertDialog alertDialog = new AlertDialog.Builder(mRecyclerView.getContext())
                .setTitle("Choose a room")
                .setItems(listOfPlace, clickListener)
                .create();
        return alertDialog;
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
                LocalDate filterDate = Utils.epochMilliToLocalDate((Long) selection);
                mRecyclerView.setAdapter(new MeetingAdapter(DI.getMeetingService().getMeetingFilterByDate(filterDate)));
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
                createPlaceListPickerDialog().show();
                return true;
            case R.id.no_filter:
                mRecyclerView.setAdapter(mAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
