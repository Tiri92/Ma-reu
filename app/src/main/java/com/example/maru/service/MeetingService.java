package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {

    List<Meeting> getMeetingList();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    List<Meeting> getMeetingFilterByPlace(String selectedPlace);

    List<Meeting> getMeetingFilterByDate(LocalDate mFilterDate);

}
