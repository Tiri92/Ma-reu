package com.example.maru;

import com.example.maru.di.DI;
import com.example.maru.model.Meeting;
import com.example.maru.service.FakeMeetingGenerator;
import com.example.maru.service.MeetingService;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeetingListUnitTest {

    private MeetingService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }


    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetingList();
        List<Meeting> expectedMeetings = FakeMeetingGenerator.generateMeetingList();
        MatcherAssert.assertThat(meetings, equalTo(expectedMeetings));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetingList().get(0);
        service.getMeetingList().remove(0);
        assertFalse(service.getMeetingList().contains(meetingToDelete));
    }

    @Test
    public void addMeetingWithSuccess() {
        int numberOfMeeting = FakeMeetingGenerator.generateMeetingList().size();
        Meeting aMeeting = new Meeting(LocalDate.of(2021, 8, 9), "13h00", "Salle G", "Test", Arrays.asList("tbmd@hotmail.com"), 0xFFaed581);
        service.addMeeting(aMeeting);
        int newNumberOfMeeting = service.getMeetingList().size();
        assertEquals(numberOfMeeting + 1, newNumberOfMeeting);
    }

    @Test
    public void getMeetingFilterByDateWithSuccess() {
        LocalDate filterDate = LocalDate.of(2021, 7, 25);
        List<Meeting> filterMeetings = service.getMeetingFilterByDate(filterDate);

        for (Meeting meeting : filterMeetings) {
            assertEquals(filterDate, meeting.getDate());
        }
    }

    @Test
    public void getMeetingFilterByPlaceWithSuccess() {
        String filterPlace = "Salle B";
        List<Meeting> filterMeetings = service.getMeetingFilterByPlace(filterPlace);

        for (Meeting meeting : filterMeetings) {
            assertEquals(filterPlace, meeting.getPlaceOfMeeting());
        }

    }

}


