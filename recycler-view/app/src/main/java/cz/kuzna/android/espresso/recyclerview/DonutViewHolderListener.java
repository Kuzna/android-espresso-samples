package cz.kuzna.android.espresso.recyclerview;

import android.view.View;

/**
 * @author Radek Kuznik
 */
public interface DonutViewHolderListener {
    void onItemClick(int position, View view);
    void onItemLongClick(int position, View view);
    void onFavoriteClick(int position, View view);
}