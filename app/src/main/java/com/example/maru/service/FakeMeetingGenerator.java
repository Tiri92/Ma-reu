package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {

    private static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting("14h15", "Réunion A", "Marketing", Arrays.asList("john@gmail.com", "luca@gmail.com", "frank@gmail.com")),
            new Meeting("17h30", "Réunion B", "Ressources Humaines", Arrays.asList("elise@gmail.com", "marie@gmail.com", "paul@gmail.com")),
            new Meeting("16h20", "Réunion C", "Finance", Arrays.asList("alice@gmail.com", "laura@gmail.com", "alex@gmail.com"))
    );

    public static List<Meeting> generateMeetingList(){
        return new ArrayList<>(MEETING_LIST);
    }

}
