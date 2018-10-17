package dev.ri0arjuna.resepmamiapps.activity.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.activity.FavoriteActivity;
import dev.ri0arjuna.resepmamiapps.activity.InfoActivity;
import dev.ri0arjuna.resepmamiapps.activity.TopFoodActivity;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterMakanan;
import dev.ri0arjuna.resepmamiapps.model.ModelFood;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {

    private RecyclerView recyclerView;
    private AdapterMakanan adapterMakanan;
    private List<ModelFood> modelFoodList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private MaterialSearchView searchView;
    private NavigationView navigationView;
    private MainPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_food);
        searchView = findViewById(R.id.search_material);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenterImp(this);
        progressDialog = new ProgressDialog(this);

        presenter.loadRecipe();
        searchMakanan();

        setupData();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);
    }

    private void setupData() {
        adapterMakanan = new AdapterMakanan(modelFoodList, MainActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(adapterMakanan);
    }

    private void searchMakanan() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchRecipe(newText);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_search || super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_foodvorite) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
        } else if (id == R.id.nav_top_food) {
            startActivity(new Intent(MainActivity.this, TopFoodActivity.class));
        } else if (id == R.id.nav_info) {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_menu);
    }

    @Override
    public void showLoading() {
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showFood(List<ModelFood> modelFoods) {
        modelFoodList.clear();
        modelFoodList.addAll(modelFoods);
        adapterMakanan.notifyDataSetChanged();
    }

    @Override
    public void showSearchFood(List<ModelFood> modelFoods) {
        modelFoodList.clear();
        modelFoodList.addAll(modelFoods);
        adapterMakanan.notifyDataSetChanged();
    }
}