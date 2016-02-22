package cz.kuzna.android.espresso.recyclerview;

import android.support.annotation.IdRes;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.allOf;

/**
 * @author Radek Kuznik
 */
public class ExtendedRecyclerViewActions {

    public static ViewAction actionOnItemAtPosition(final int position, final @IdRes int id, final ViewAction viewAction) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "actionOnItemAtPosition performing ViewAction: " + viewAction.getDescription() + " on item view with id " + id + " at position: " + position;
            }

            @Override
            public void perform(UiController uiController, View view) {
                final RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.scrollToPosition(position);
                uiController.loopMainThreadUntilIdle();

                final RecyclerView.ViewHolder viewHolderForPosition = recyclerView.findViewHolderForPosition(position);

                if (null == viewHolderForPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(new IllegalStateException("No view holder at position: " + position))
                            .build();
                }

                final View viewAtPosition = viewHolderForPosition.itemView;
                if (null == viewAtPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(viewAtPosition))
                            .withCause(new IllegalStateException("No view at position: " + position)).build();
                }

                final View viewItem =viewAtPosition.findViewById(id);

                if (null == viewItem) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(viewAtPosition))
                            .withCause(new IllegalStateException("No item view " + id + " at position: " + position)).build();
                }

                viewAction.perform(uiController, viewItem);
            }
        };
    }
}