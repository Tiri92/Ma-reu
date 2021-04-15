package com.example.maru.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.maru.R;
import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.SaveMeeting)
    public Button mSaveMeetingButton;
    @BindView(R.id.DateOfMeeting2)
    public TextInputEditText mDateOfMeeting;
    @BindView(R.id.TimeOfMeeting2)
    public TextInputEditText mTimeOfMeeting;
    @BindView(R.id.PlaceOfMeeting2)
    public TextInputEditText mPlaceOfMeeting;
    @BindView(R.id.SubjectOfMeeting2)
    public TextInputEditText mSubjectOfMeeting;
    @BindView(R.id.ListOfParticipants2)
    public TextInputEditText mListOfParticipants;
    private MaterialDatePicker mDatePicker;
    private MaterialTimePicker mTimePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mListOfParticipants.setError("there must be comma");

        mDateOfMeeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mDatePicker==null || !mDatePicker.isAdded()){
                    createDatePicker();
                    mDatePicker.show(getSupportFragmentManager().beginTransaction(), "DATE_PICKER");
                }
            }
        });
        mTimeOfMeeting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mTimePicker==null || !mTimePicker.isAdded()){
                    createTimePicker();
                    mTimePicker.show(getSupportFragmentManager().beginTransaction(), "TIME_PICKER");
                }
            }
        });
        mSaveMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // we used a regex to separate all email by a ","
                String line = mListOfParticipants.getText().toString();
                String[] separated = line.split(",");
                List<String> participantList = Arrays.asList(separated);

                Toast.makeText(v.getContext(), "Réunion ajouté", Toast.LENGTH_SHORT).show();

                Meeting meeting = new Meeting(
                        mDateOfMeeting.getText().toString(),
                        mPlaceOfMeeting.getText().toString(),
                        mSubjectOfMeeting.getText().toString(),
                        participantList);

                DI.meetingService.addMeeting(meeting);
                finish();
            }
        });
    }

    private void createTimePicker() {
        mTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build();
        mTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View selection) {
                String timeString = String.format("%dh%d", mTimePicker.getHour(), mTimePicker.getMinute());
                mTimeOfMeeting.setText(timeString);
            }
        });
    }

    private void createDatePicker() {
        mDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        mDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                mDateOfMeeting.setText(mDatePicker.getHeaderText());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mDatePicker!=null && mDatePicker.isAdded()) {
            mDatePicker.dismiss();
        }
        if(mTimePicker!=null && mTimePicker.isAdded()) {
            mTimePicker.dismiss();
        }
    }
}
