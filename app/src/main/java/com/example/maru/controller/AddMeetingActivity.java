package com.example.maru.controller;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.SaveMeeting)
    public Button mSaveMeetingButton;
    @BindView(R.id.DateOfMeeting)
    public TextView mDateOfMeeting;
    @BindView(R.id.TimeOfMeeting)
    public TextView mTimeOfMeeting;
    @BindView(R.id.PlaceOfMeeting2)
    public TextInputEditText mPlaceOfMeeting;
    @BindView(R.id.SubjectOfMeeting2)
    public TextInputEditText mSubjectOfMeeting;
    @BindView(R.id.ListOfParticipants2)
    public TextInputEditText mListOfParticipants;

    private MaterialDatePicker mDatePicker;
    private MaterialTimePicker mTimePicker;

    boolean isPlaceFieldValid = false;
    boolean isSubjectFieldValid = false;
    boolean isParticipantsFieldValid = false;

    private static final Pattern upperCaseLetter =
            Pattern.compile("(?=.*[A-Z])");
    Matcher matcher = upperCaseLetter.matcher("A-Z");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

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
                        mTimeOfMeeting.getText().toString(),
                        mPlaceOfMeeting.getText().toString(),
                        mSubjectOfMeeting.getText().toString(),
                        participantList);

                DI.meetingService.addMeeting(meeting);
                finish();
            }
        });

        mPlaceOfMeeting.addTextChangedListener(placeTextWatcher);
        mSubjectOfMeeting.addTextChangedListener(subjectTextWatcher);
        mListOfParticipants.addTextChangedListener(participantsTextWatcher);

    }  //onCreate finish here

    TextWatcher subjectTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String subject = mSubjectOfMeeting.getText().toString().trim();
            if(subject.isEmpty()) {
                mSubjectOfMeeting.setError("Field can't be empty");
                isSubjectFieldValid = false;
            }
            else{
                isSubjectFieldValid = true;
            }
            updateSaveMeetingButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher placeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String place = mPlaceOfMeeting.getText().toString().trim();
            if(place.isEmpty()) {
                mPlaceOfMeeting.setError("Field can't be empty");
                isPlaceFieldValid = false;
            }
            else if(!place.contains("Salle")) {
                mPlaceOfMeeting.setError("Must start with 'Salle'");
                isPlaceFieldValid = false;
            }
            else if(!upperCaseLetter.matcher(place).matches()){
                mPlaceOfMeeting.setError("Use one upper case letter");
                isPlaceFieldValid = false;
            }
            else{
                isPlaceFieldValid = true;
            }
            updateSaveMeetingButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher participantsTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String listOfParticipants = mListOfParticipants.getText().toString().trim();

            if (listOfParticipants.isEmpty()) {
                mListOfParticipants.setError("Field can't be empty");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains("@")) {
                mListOfParticipants.setError("there must be @");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains(".")) {
                mListOfParticipants.setError("there must be point");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains(",")) {
                mListOfParticipants.setError("there must be comma");
                isParticipantsFieldValid = false;
            }
            else{
                isParticipantsFieldValid = true;
            }
            updateSaveMeetingButton();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void updateSaveMeetingButton() {
        if(isParticipantsFieldValid && isPlaceFieldValid && isSubjectFieldValid && !mDateOfMeeting.getText().toString().isEmpty()
                && !mTimeOfMeeting.getText().toString().isEmpty() ){
            mSaveMeetingButton.setEnabled(true);
        }
        else{
            mSaveMeetingButton.setEnabled(false);
        }
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
                updateSaveMeetingButton();
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
                updateSaveMeetingButton();
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
