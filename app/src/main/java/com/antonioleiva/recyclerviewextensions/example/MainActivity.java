package com.antonioleiva.recyclerviewextensions.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(false);
        final MyRecyclerAdapter adapter;

        recyclerView.setAdapter(adapter = new MyRecyclerAdapter(createMockList(), R.layout.item));

        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<ViewModel>() {
            @Override
            public void onItemClick(View view, ViewModel viewModel) {
                adapter.remove(viewModel);
            }
        });
    }

    private List<ViewModel> createMockList() {
        List<ViewModel> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add(new ViewModel(i, "Item " + (i + 1), MOCK_URL + (i % 10 + 1)));
        }
        return items;
    }


    public int offsetForVerticalScrolling(RecyclerView recyclerView) {
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

        int position = llm.findFirstVisibleItemPosition();
        MyRecyclerAdapter.ViewHolder mViewHolder = (MyRecyclerAdapter.ViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        View item = mViewHolder.itemView;

        int y = (int) item.getY();
        if (y < 0) y *= -1;

        if (position == 0) return y;
        else {
            int offset = y;
            for (int i = 0; i < position; i++) {
                //Add your previous item heights to offset
            }
            return offset;
        }
    }

}
