package com.example.maru.service;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeMeetingGenerator {

    private static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting(40, "Salle A", "Marketing", Arrays.asList("toto@tutu.com", "titi@tutu.com", "tata@tutu.com")),
            new Meeting(30, "Salle B", "Ressource Humaine", Arrays.asList("toto@tutu.com", "titi@tutu.com", "tata@tutu.com")),
            new Meeting(20, "Salle C", "Finance", Arrays.asList("toto@tutu.com", "titi@tutu.com", "tata@tutu.com"))
    );

    public static List<Meeting> generateMeetingList(){
        return new ArrayList<>(MEETING_LIST);
    }

}
