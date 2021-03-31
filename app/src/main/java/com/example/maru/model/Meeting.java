package com.example.maru.model;

import java.util.List;

public class Meeting {


    private String timeOfMeeting;

    private String placeOfMeeting;

    private String subjectOfMeeting;

    private List<String> listOfParticipants;

    /**
     * Constructor
     *
     * @param timeOfMeeting
     * @param placeOfMeeting
     * @param subjectOfMeeting
     * @param listOfParticipants
     */

    public Meeting(String timeOfMeeting, String placeOfMeeting, String subjectOfMeeting, List<String> listOfParticipants) {

        this.timeOfMeeting = timeOfMeeting;
        this.placeOfMeeting = placeOfMeeting;
        this.subjectOfMeeting = subjectOfMeeting;
        this.listOfParticipants = listOfParticipants;
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

}
