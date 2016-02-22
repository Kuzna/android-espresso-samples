package cz.kuzna.android.espresso.recyclerview;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

/**
 * @author Radek Kuznik
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    private static final int ITEMS_COUNT = 12;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRemoveOnSwipeLeft() throws Exception {
        /** Init verification */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT));

        /** Execute */
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, swipeLeft()));

        /** Verify */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT - 1));
    }

    @Test
    public void testRemoveOnSwipeRight() throws Exception {
        /** Init verification */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT));

        /** Execute */
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, swipeRight()));

        /** Verify */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT - 1));
    }

    @Test
    public void testOnItemClick() throws Exception {
        final int position = 10;

        /** Execute */
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position, click()));

        /** Verify */
        onView(withText("Clicked on " + position))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testRemoveOnItemLongClick() throws Exception {
        /** Init verification */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT));

        /** Execute */
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(10, longClick()));

        /** Verify */
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT - 1));
    }

    @Test
    public void testOnFavoriteClick() throws Exception {
        final int position = 2;

        /** Init verification - not favorite */
        onView(withId(R.id.recycler_view)).check(RecyclerViewAssertions.hasItemDrawableSameResId(position, R.id.imageViewFavorite, R.drawable.ic_star_border_grey_500_24dp));

        /** Execute */
        onView(withId(R.id.recycler_view))
                .perform(ExtendedRecyclerViewActions.actionOnItemAtPosition(position, R.id.imageViewFavorite, click()));

        /** Verify */
        onView(withId(R.id.recycler_view)).check(RecyclerViewAssertions.hasItemDrawableSameResId(position, R.id.imageViewFavorite, R.drawable.ic_star_yellow_500_24dp));
    }

    @Test
    public void testItemCount() throws Exception {
        onView(withId(R.id.recycler_view))
                .check(RecyclerViewAssertions.hasCount(ITEMS_COUNT));
    }

    @Test
    public void testScrollToBottom() throws Exception {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(ITEMS_COUNT - 1, scrollTo()));
    }

    @Test
    public void testScrollToTop() throws Exception {
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
    }
}