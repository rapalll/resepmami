package dev.ri0arjuna.resepmamiapps.activity.favoritefood;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Objects;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterFavorite;
import dev.ri0arjuna.resepmamiapps.db.DBase;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        init();

        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        setupList();
        populateRecyclerView();
    }

    private void setupList() {
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recycler_view_fav);
        toolbar = findViewById(R.id.toolbar);
    }

    private void populateRecyclerView() {
        DBase dbHelper = new DBase(this);
        AdapterFavorite adapter = new AdapterFavorite(dbHelper.makananList(), this, mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateRecyclerView();
    }
}
