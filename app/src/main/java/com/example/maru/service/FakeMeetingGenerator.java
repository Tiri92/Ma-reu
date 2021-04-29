package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {

    private static final List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(LocalDate.of(2021, 6, 16), "14h30" , "Salle A", "Marketing", Arrays.asList("john@gmail.com", "luca@gmail.com", "frank@gmail.com"), 0xffaed581),
            new Meeting(LocalDate.of(2021, 5, 9), "15h45" , "Salle B", "Ressources Humaines", Arrays.asList("elise@gmail.com", "marie@gmail.com", "paul@gmail.com"), 0xff64b5f6),
            new Meeting(LocalDate.of(2021, 7, 25), "17h15" , "Salle C", "Finance", Arrays.asList("alice@gmail.com", "laura@gmail.com", "alex@gmail.com"), 0xffffd740)
    );

    public static List<Meeting> generateMeetingList(){
        return new ArrayList<>(MEETING_LIST);
    }

}

