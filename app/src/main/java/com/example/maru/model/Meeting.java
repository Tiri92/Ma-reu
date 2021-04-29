package com.example.maru.model;

import java.time.LocalDate;
import java.util.List;

public class Meeting {

    private LocalDate date;
    private String timeOfMeeting;
    private String placeOfMeeting;
    private String subjectOfMeeting;
    private List<String> listOfParticipants;
    private int colorOfMeeting;

    public Meeting(LocalDate date, String timeOfMeeting, String placeOfMeeting, String subjectOfMeeting, List<String> listOfParticipants, int colorOfMeeting) {
        this.date = date;
        this.timeOfMeeting = timeOfMeeting;
        this.placeOfMeeting = placeOfMeeting;
        this.subjectOfMeeting = subjectOfMeeting;
        this.listOfParticipants = listOfParticipants;
        this.colorOfMeeting = colorOfMeeting;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTimeOfMeeting() {
        return timeOfMeeting;
    }

    public void setTimeOfMeeting(String timeOfMeeting) {
        this.timeOfMeeting = timeOfMeeting;
    }

    public String getPlaceOfMeeting() {
        return placeOfMeeting;
    }

    public void setPlaceOfMeeting(String placeOfMeeting) {
        this.placeOfMeeting = placeOfMeeting;
    }

    public String getSubjectOfMeeting() {
        return subjectOfMeeting;
    }

    public void setSubjectOfMeeting(String subjectOfMeeting) {
        this.subjectOfMeeting = subjectOfMeeting;
    }

    public List<String> getListOfParticipants() {
        return listOfParticipants;
    }

    public void setListOfParticipants(List<String> listOfParticipants) {
        this.listOfParticipants = listOfParticipants;
    }

    public int getColorOfMeeting() {
        return colorOfMeeting;
    }

    public void setColorOfMeeting(int colorOfMeeting) {
        this.colorOfMeeting = colorOfMeeting;
    }
}
