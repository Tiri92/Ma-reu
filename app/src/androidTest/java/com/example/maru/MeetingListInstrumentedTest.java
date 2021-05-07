package com.example.maru;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maru.controller.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.maru.util.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListInstrumentedTest {
    private static int ITEMS_COUNT = 6;

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUp() {
    }

    @Test
    public void MyMeetingList_ShouldNotBeEmpty() {
        onView(withId(R.id.RecyclerView)).check(matches(hasMinimumChildCount(6)));
    }

    @Test
    public void MeetingList_DeleteAction_ShouldDeleteMeeting() {
        int numberOfDeletedItem = 3;
        onView(withId(R.id.RecyclerView)).check(withItemCount(ITEMS_COUNT));

        for (int i = 0; i < numberOfDeletedItem; i++) {
            onView(withId(R.id.RecyclerView))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i, new ViewAction() {
                        @Override
                        public Matcher<View> getConstraints() {
                            return null;
                        }

                        @Override
                        public String getDescription() {
                            return null;
                        }

                        @Override
                        public void perform(UiController uiController, View view) {
                            View button = view.findViewById(R.id.item_list_delete_button);
                            button.performClick();
                        }
                    }));
        }
        onView(withId(R.id.RecyclerView)).check(withItemCount(ITEMS_COUNT - numberOfDeletedItem));
    }

    @Test
    public void MeetingList_AddAction_ShouldAddMeeting() throws InterruptedException {
        onView(withId(R.id.RecyclerView)).check(withItemCount(ITEMS_COUNT));
        onView(withId(R.id.add_meeting_button)).perform(click());

        onView(withId(R.id.DateOfMeeting)).perform(click());
        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click());

        onView(withId(R.id.TimeOfMeeting)).perform(click());
        onView(withId(com.google.android.material.R.id.material_timepicker_ok_button)).perform(click());


        onView(withId(R.id.PlaceOfMeetingEditText)).perform(replaceText("Salle F"));

        onView(withId(R.id.SubjectOfMeetingEditText)).perform(replaceText("Gestion des Tests"));

        onView(withId(R.id.ListOfParticipantsEditText)).perform(replaceText("JohnBaby@gmail.com,"));


        onView(withId(R.id.SaveMeeting)).perform(click());

        onView(withId(R.id.RecyclerView)).check(withItemCount(ITEMS_COUNT + 1));
    }


    @Test
    public void MeetingFilter_FilterActionPerDate_ShouldFilterMeetingPerDate() throws InterruptedException {
        MeetingList_AddAction_ShouldAddMeeting();

        ViewInteraction overflowMenuButton = onView(withId(R.id.meeting_filter));
        overflowMenuButton.perform(click());

        ViewInteraction filterPerDate = onView(withText("Filter per date"));
        filterPerDate.perform(click());

        onView(withId(com.google.android.material.R.id.confirm_button)).perform(click());

        onView(withId(R.id.RecyclerView)).check(withItemCount(1));

    }

    @Test
    public void MeetingFilter_FilterActionPerPlace_ShouldFilterMeetingPerPlace() {
        ViewInteraction overflowMenuButton = onView(withId(R.id.meeting_filter));
        overflowMenuButton.perform(click());

        ViewInteraction filterPerPlace = onView(withText("Filter per place"));
        filterPerPlace.perform(click());

        onView(withId(com.google.android.material.R.id.select_dialog_listview)).perform(click());

        onView(withId(R.id.RecyclerView)).check(withItemCount(2));

    }

    @Test
    public void MeetingFilter_NoFilterAction_ShouldRemoveFilter() {
        MeetingFilter_FilterActionPerPlace_ShouldFilterMeetingPerPlace();
        ViewInteraction overflowMenuButton = onView(withId(R.id.meeting_filter));
        overflowMenuButton.perform(click());

        ViewInteraction filterPerPlace = onView(withText("No filter"));
        filterPerPlace.perform(click());
        onView(withId(R.id.RecyclerView)).check(withItemCount(ITEMS_COUNT));

    }

}