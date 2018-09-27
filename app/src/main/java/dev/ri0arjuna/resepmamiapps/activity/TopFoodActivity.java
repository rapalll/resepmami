package dev.ri0arjuna.resepmamiapps.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.ri0arjuna.resepmamiapps.ModelTopFood;
import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterTopView;

public class TopFoodActivity extends AppCompatActivity {

    String nama_makanan, gambar_makanan, resep_makanan;
    int id, favorited;
    RecyclerView recyclerView;
    AdapterTopView adapterMakanan;
    DatabaseReference mRef;
    List<ModelTopFood> makananList;
    ProgressDialog progressDialog;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_food);

        mRef = FirebaseDatabase.getInstance().getReference("Makanan");
        linearLayoutManager = new LinearLayoutManager(TopFoodActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_food);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);
        progressDialog.show();

        loadDataMakanan();
    }

    private void loadDataMakanan() {
        mRef = FirebaseDatabase.getInstance().getReference().child("Makanan");
        final Query asc = mRef.orderByChild("favorited");

        asc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                makananList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.w("Firebase: ", "loadDataMakanan()");

                    ModelTopFood value = dataSnapshot1.getValue(ModelTopFood.class);
                    ModelTopFood fire = new ModelTopFood();

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

                    adapterMakanan = new AdapterTopView(makananList, TopFoodActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(adapterMakanan);
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
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
}
