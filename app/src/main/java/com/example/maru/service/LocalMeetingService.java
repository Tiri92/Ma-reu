package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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

    @Override
    public List<Meeting> getMeetingFilterByPlace(String selectedPlace) {
        List<Meeting> filteredMeetingListByPlace = new ArrayList<>(meetingList);

        for (Iterator<Meeting> it = filteredMeetingListByPlace.iterator(); it.hasNext(); ) {
            Meeting meeting = it.next();
            if (!meeting.getPlaceOfMeeting().equalsIgnoreCase(selectedPlace)) {
                it.remove();
            }
        }
        return filteredMeetingListByPlace;
    }

    @Override
    public List<Meeting> getMeetingFilterByDate(LocalDate filterDate) {
        List<Meeting> filteredMeetingList = new ArrayList<>(meetingList);

        for (Iterator<Meeting> it = filteredMeetingList.iterator(); it.hasNext(); ) {
            Meeting meeting = it.next();
            if (!meeting.getDate().isEqual(filterDate)) {
                it.remove();
            }
        }
        return filteredMeetingList;
    }

}
