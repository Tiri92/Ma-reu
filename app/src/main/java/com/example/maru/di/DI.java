package com.example.maru.di;

import com.example.maru.service.LocalMeetingService;
import com.example.maru.service.MeetingService;

/**
 * Dependency injector to get instance of services
 */

public class DI {

    public static MeetingService meetingService = new LocalMeetingService();

    /**
     * Get an instance on @{@link MeetingService}
     * @return
     */
    public static MeetingService getMeetingService() { return meetingService;
    }

    /**
     * Get always a new instance on @{@link MeetingService}. Useful for tests, so we ensure the context is clean.
     * @return
     */
    public static MeetingService getNewInstanceApiService() {
        return new LocalMeetingService();
    }

}
