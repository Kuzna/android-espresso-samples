package cz.kuzna.android.espresso.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Radek Kuznik
 */
public class MainActivity extends AppCompatActivity implements DonutViewHolderListener {

    private DonutsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new DonutsAdapter();
        mAdapter.setListener(this);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int swipeDir) {
                mAdapter.remove(viewHolder.getAdapterPosition());
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(getApplicationContext(), "Clicked on " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int position, View view) {
        mAdapter.remove(position);
    }

    @Override
    public void onFavoriteClick(int position, View view) {
        final Donut donut = mAdapter.getItem(position);
        donut.favorite = !donut.favorite;

        ((ImageView)view).setImageResource(donut.favorite ? R.drawable.ic_star_yellow_500_24dp : R.drawable.ic_star_border_grey_500_24dp);
    }
}