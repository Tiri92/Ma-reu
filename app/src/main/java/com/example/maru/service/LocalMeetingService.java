package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.List;

public class LocalMeetingService implements MeetingService {

    private final List<Meeting> meetingList = FakeMeetingGenerator.generateMeetingList();

    @Override
    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    @Override
    public void addMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }
}
