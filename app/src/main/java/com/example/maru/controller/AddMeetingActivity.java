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
import com.example.maru.util.Utils;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.SaveMeeting)
    public Button mSaveMeetingButton;
    @BindView(R.id.DateOfMeeting)
    public TextView mDateTextView;
    @BindView(R.id.TimeOfMeeting)
    public TextView mTimeTextView;
    @BindView(R.id.PlaceOfMeetingEditText)
    public TextInputEditText mPlaceEditText;
    @BindView(R.id.SubjectOfMeetingEditText)
    public TextInputEditText mSubjectEditText;
    @BindView(R.id.ListOfParticipantsEditText)
    public TextInputEditText mParticipantListEditText;

    private MaterialDatePicker mDatePicker;
    private MaterialTimePicker mTimePicker;

    private LocalDate mDate;

    boolean isPlaceFieldValid = false;
    boolean isSubjectFieldValid = false;
    boolean isParticipantsFieldValid = false;

    private static final Pattern upperCaseLetter =
            Pattern.compile("^Salle [A-J]{1}$");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meeting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        mDateTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mDatePicker==null || !mDatePicker.isAdded()){
                    createDatePicker();
                    mDatePicker.show(getSupportFragmentManager().beginTransaction(), "DATE_PICKER");
                }
            }
        });

        mTimeTextView.setOnClickListener(new View.OnClickListener(){
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
                String line = mParticipantListEditText.getText().toString();
                String[] separated = line.split(",");
                List<String> participantList = Arrays.asList(separated);

                Toast.makeText(v.getContext(), "Réunion ajouté", Toast.LENGTH_SHORT).show();

                Meeting meeting = new Meeting(
                        mDate,
                        mTimeTextView.getText().toString(),
                        mPlaceEditText.getText().toString(),
                        mSubjectEditText.getText().toString(),
                        participantList,
                        Utils.getRandomColor(v.getResources()));

                DI.getMeetingService().addMeeting(meeting);
                finish();
            }
        });

        mPlaceEditText.addTextChangedListener(placeTextWatcher);
        mSubjectEditText.addTextChangedListener(subjectTextWatcher);
        mParticipantListEditText.addTextChangedListener(participantsTextWatcher);

    }  //onCreate finish here

    TextWatcher subjectTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String subject = mSubjectEditText.getText().toString().trim();
            if(subject.isEmpty()) {
                mSubjectEditText.setError("Field can't be empty");
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
            String place = mPlaceEditText.getText().toString().trim();
            if(place.isEmpty()) {
                mPlaceEditText.setError("Field can't be empty");
                isPlaceFieldValid = false;
            }
            else if(!place.contains("Salle")) {
                mPlaceEditText.setError("Must start with 'Salle'");
                isPlaceFieldValid = false;
            }
            else if(!upperCaseLetter.matcher(place).matches()){
                mPlaceEditText.setError("Use one upper case letter between A & J");
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
            String listOfParticipants = mParticipantListEditText.getText().toString().trim();

            if (listOfParticipants.isEmpty()) {
                mParticipantListEditText.setError("Field can't be empty");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains("@")) {
                mParticipantListEditText.setError("there must be @");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains(".")) {
                mParticipantListEditText.setError("there must be point");
                isParticipantsFieldValid = false;
            }
            else if (!listOfParticipants.contains(",")) {
                mParticipantListEditText.setError("there must be comma");
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
        if(isParticipantsFieldValid && isPlaceFieldValid && isSubjectFieldValid && !mDateTextView.getText().toString().isEmpty()
                && !mTimeTextView.getText().toString().isEmpty() ){
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
                mTimeTextView.setText(timeString);
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
                mDate = Utils.epochMilliToLocalDate((Long) selection);
                String calendarString = mDate.format(DateTimeFormatter.ofPattern("d LLL Y"));
                mDateTextView.setText(calendarString);
                updateSaveMeetingButton();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mDatePicker!=null && mDatePicker.isAdded()) {
            mDatePicker.dismiss();
        }
        if(mTimePicker!=null && mTimePicker.isAdded()) {
            mTimePicker.dismiss();
        }
    }
}
