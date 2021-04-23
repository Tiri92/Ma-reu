package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {

    private static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting("16 juin 2021", "14h30" , "Salle A", "Marketing", Arrays.asList("john@gmail.com", "luca@gmail.com", "frank@gmail.com")),
            new Meeting("2 juin 2021", "15h45" , "Salle B", "Ressources Humaines", Arrays.asList("elise@gmail.com", "marie@gmail.com", "paul@gmail.com")),
            new Meeting("8 juin 2021", "17h15" , "Salle C", "Finance", Arrays.asList("alice@gmail.com", "laura@gmail.com", "alex@gmail.com"))
    );

    public static List<Meeting> generateMeetingList(){
        return new ArrayList<>(MEETING_LIST);
    }

}
