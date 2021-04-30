package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {

    private static final List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(LocalDate.of(2021, 6, 16), "14h30" , "Salle A", "Marketing", Arrays.asList("john@gmail.com", "luca@gmail.com", "frank@gmail.com"), 0xffaed581),
            new Meeting(LocalDate.of(2021, 5, 17), "15h45" , "Salle B", "Ressources Humaines", Arrays.asList("elise@gmail.com", "marie@gmail.com", "paul@gmail.com"), 0xDFFA440A),
            new Meeting(LocalDate.of(2021, 7, 25), "17h15" , "Salle B", "Finance", Arrays.asList("alice@gmail.com", "laura@gmail.com", "alex@gmail.com"), 0xffffd740),
            new Meeting(LocalDate.of(2021, 5, 17 ), "9h45", "Salle A", "Application", Arrays.asList("alex@gmail.com", "laurent@gmail.com", "alex@gmail.com"), 0xD0EA13DF),
            new Meeting(LocalDate.of(2021, 7, 25 ), "16h20", "Salle D", "Objectif", Arrays.asList("may@gmail.com", "ethan@gmail.com", "alex@gmail.com"), 0xff64b5f6),
            new Meeting(LocalDate.of(2021, 6, 16 ), "11h50", "Salle D", "Recrutement", Arrays.asList("paul@gmail.com", "abou@gmail.com", "alex@gmail.com"), 0xE6AE6B07)
    );

    public static List<Meeting> generateMeetingList(){
        return new ArrayList<>(MEETING_LIST);
    }

}

