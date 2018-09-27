package dev.ri0arjuna.resepmamiapps.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterMakanan;
import dev.ri0arjuna.resepmamiapps.model.ModelMakanan;

public class SearchActivity extends AppCompatActivity {

    String nama_makanan, gambar_makanan, resep_makanan;
    int id;
    DatabaseReference mRef;
    List<ModelMakanan> list_search;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String search = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view_search);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        }

        String query = getIntent().getStringExtra("query");
        Toast.makeText(SearchActivity.this, "Dapet querynya : " + query, Toast.LENGTH_LONG).show();
        toolbar.setSubtitle("" + query);

        loadSearch(query);
    }

    private void loadSearch(final String query) {
        mRef = FirebaseDatabase.getInstance().getReference().child("Makanan");

//        final Query firebaseSearchQuery = mRef.orderByChild("nama_makanan")
//                .startAt(query.toLowerCase())
//                .endAt(query.toLowerCase() + "\uf8ff");
//        search = firebaseSearchQuery.toString();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_search = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.w("Firebase: ", "loadDataKonten()");

                    ModelMakanan value = dataSnapshot1.getValue(ModelMakanan.class);
                    ModelMakanan fire = new ModelMakanan();

                    nama_makanan = value != null ? value.getNama_makanan() : null;
                    gambar_makanan = value != null ? value.getGambar_makanan() : null;
                    resep_makanan = value != null ? value.getResep_makanan() : null;
                    id = value != null ? value.getId() : 0;

                    fire.setNama_makanan(nama_makanan);
                    fire.setGambar_makanan(gambar_makanan);
                    fire.setResep_makanan(resep_makanan);
                    fire.setId(id);

                    list_search.add(fire);

                    AdapterMakanan adapter = new AdapterMakanan(list_search, SearchActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Gagal setAdapter: " + search, Toast.LENGTH_SHORT).show();
            }
        });

//        firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                list_search = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                    Log.w("Firebase: ", "loadDataKonten()");
//
//                    ModelMakanan value = dataSnapshot1.getValue(ModelMakanan.class);
//                    ModelMakanan fire = new ModelMakanan();
//
//                    nama_makanan = value != null ? value.getNama_makanan() : null;
//                    gambar_makanan = value != null ? value.getGambar_makanan() : null;
//                    resep_makanan = value != null ? value.getResep_makanan() : null;
//                    id = value != null ? value.getId() : 0;
//
//                    fire.setNama_makanan(nama_makanan);
//                    fire.setGambar_makanan(gambar_makanan);
//                    fire.setResep_makanan(resep_makanan);
//                    fire.setId(id);
//
//                    list_search.add(fire);
//
//                    AdapterMakanan adapter = new AdapterMakanan(list_search, SearchActivity.this);
//                    recyclerView.setAdapter(adapter);
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(SearchActivity.this, "Gagal setAdapter: " + search, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
