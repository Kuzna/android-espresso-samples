package cz.kuzna.android.espresso.recyclerview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;

/**
 * @author Radek Kuznik
 */
public final class ImageViewDrawableMatcher {

    public static BoundedMatcher<View, ImageView> hasDrawableWithResId(final @DrawableRes int drawableResId) {
        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has drawable with resource ID");
            }

            @Override
            public boolean matchesSafely(ImageView imageView) {
                final Drawable actual = imageView.getDrawable();
                final Drawable expected = imageView.getResources().getDrawable(drawableResId);

                if (expected != null && actual != null && actual instanceof BitmapDrawable && expected instanceof BitmapDrawable) {
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        final Bitmap actualBitmap = ((BitmapDrawable) actual).getBitmap();
                        final Bitmap expectedBitmap = ((BitmapDrawable) expected).getBitmap();

                        return actualBitmap.sameAs(expectedBitmap);
                    } else {
                        return expected.getConstantState().equals(expected.getConstantState());
                    }
                }

                return false;
            }
        };
    }
}