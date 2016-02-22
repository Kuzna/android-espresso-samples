package cz.kuzna.android.espresso.recyclerview;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author Radek Kuznik
 */
public class RecyclerViewAssertions {

    public static ViewAssertion hasCount(final int count) {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }

                final RecyclerView recyclerView = (RecyclerView) view;
                assertThat(recyclerView.getAdapter().getItemCount(), equalTo(count));
            }
        };
    }

    public static ViewAssertion hasItemDrawableSameResId(final int position, final @IdRes int id, final @DrawableRes int drawableResId) {
        return new ViewAssertion() {
            @Override public void check(View view, NoMatchingViewException e) {
                if (!(view instanceof RecyclerView)) {
                    throw e;
                }

                final RecyclerView recyclerView = (RecyclerView) view;
                final ImageView favIcon = (ImageView)recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(id);
                assertThat(favIcon, ImageViewDrawableMatcher.hasDrawableWithResId(drawableResId));
            }
        };
    }
}