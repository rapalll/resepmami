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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.activity.FavoriteActivity;
import dev.ri0arjuna.resepmamiapps.activity.InfoActivity;
import dev.ri0arjuna.resepmamiapps.activity.TopFoodActivity;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterMakanan;
import dev.ri0arjuna.resepmamiapps.model.ModelMakanan;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String nama_makanan, gambar_makanan, resep_makanan;
    private int id, favorited;
    private RecyclerView recyclerView;
    private AdapterMakanan adapterMakanan;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().getRoot();
    private List<ModelMakanan> makananList;
    private ProgressDialog progressDialog;
    private MaterialSearchView searchView;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = FirebaseDatabase.getInstance().getReference("Makanan");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_food);
        searchView = findViewById(R.id.search_material);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        loadDataMakanan();
        searchMakanan();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_menu);
    }

    private void searchMakanan() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference("Makanan")
                        .orderByChild("nama_makanan")
                        .startAt(newText)
                        .endAt(newText + "\uf8ff");

                firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        makananList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Log.w("Firebase: ", "loadDataKonten()");

                            ModelMakanan value = dataSnapshot1.getValue(ModelMakanan.class);
                            ModelMakanan fire = new ModelMakanan();

                            nama_makanan = value != null ? value.getNama_makanan() : null;
                            gambar_makanan = value != null ? value.getGambar_makanan() : null;
                            resep_makanan = value != null ? value.getResep_makanan() : null;
                            id = value != null ? value.getId() : 0;
                            favorited = value != null ? value.getId() : 0;

                            fire.setNama_makanan(nama_makanan);
                            fire.setGambar_makanan(gambar_makanan);
                            fire.setResep_makanan(resep_makanan);
                            fire.setId(id);
                            fire.setFavorited(favorited);

                            makananList.add(fire);

                            AdapterMakanan adapter = new AdapterMakanan(makananList, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Gagal setAdapter: ", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
        });
    }

    private void loadDataMakanan() {
        mRef = FirebaseDatabase.getInstance().getReference().child("Makanan");
        final Query asc = mRef.orderByChild("nama_makanan");

        asc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.w("Firebase: ", "loadDataMakanan()");

                    ModelMakanan value = dataSnapshot1.getValue(ModelMakanan.class);
                    ModelMakanan fire = new ModelMakanan();

                    nama_makanan = value != null ? value.getNama_makanan() : null;
                    gambar_makanan = value != null ? value.getGambar_makanan() : null;
                    resep_makanan = value != null ? value.getResep_makanan() : null;
                    id = value != null ? value.getId() : 0;
                    favorited = value != null ? value.getFavorited() : 0;

                    fire.setNama_makanan(nama_makanan);
                    fire.setGambar_makanan(gambar_makanan);
                    fire.setResep_makanan(resep_makanan);
                    fire.setId(id);
                    fire.setFavorited(favorited);

                    makananList.add(fire);

                    adapterMakanan = new AdapterMakanan(makananList, MainActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerView.setAdapter(adapterMakanan);
                    adapterMakanan.notifyDataSetChanged();
                }

                progressDialog.dismiss();
                mRef.keepSynced(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase: ", "failed to read value.", databaseError.toException());
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
        loadDataMakanan();
    }
}