package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.List;

public interface MeetingService {

    public List<Meeting> getMeetingList();

    public void addMeeting(Meeting meeting);

    public void deleteMeeting(Meeting meeting);

}
