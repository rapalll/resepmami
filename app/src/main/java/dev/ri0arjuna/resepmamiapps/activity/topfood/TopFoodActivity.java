package dev.ri0arjuna.resepmamiapps.activity.topfood;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.adapter.AdapterTopView;
import dev.ri0arjuna.resepmamiapps.model.ModelTopFood;

public class TopFoodActivity extends AppCompatActivity implements TopFoodView {

    private RecyclerView recyclerView;
    private AdapterTopView adapterMakanan;
    private List<ModelTopFood> modelTopFoods = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LinearLayoutManager linearLayoutManager;
    private TopFoodPresenterImp presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_food);

        presenter = new TopFoodPresenterImp(this);
//        mRef = FirebaseDatabase.getInstance().getReference("Makanan");
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

        setupList();
        presenter.loadTopView();
    }

    private void setupList() {
        adapterMakanan = new AdapterTopView(modelTopFoods, TopFoodActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterMakanan);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        adapterMakanan.notifyDataSetChanged();
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
    public void showTopFood(List<ModelTopFood> modelTopFoodList) {
        modelTopFoods.clear();
        modelTopFoods.addAll(modelTopFoodList);
        adapterMakanan.notifyDataSetChanged();
    }

//    private void loadDataMakanan() {
//        mRef = FirebaseDatabase.getInstance().getReference().child("Makanan");
//        final Query asc = mRef.orderByChild("favorited");
//
//        asc.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                makananList = new ArrayList<>();
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                    Log.w("Firebase: ", "loadDataMakanan()");
//
//                    ModelTopFood value = dataSnapshot1.getValue(ModelTopFood.class);
//                    ModelTopFood fire = new ModelTopFood();
//
//                    nama_makanan = value != null ? value.getNameFood() : null;
//                    gambar_makanan = value != null ? value.getImageFood() : null;
//                    resep_makanan = value != null ? value.getRecipeFood() : null;
//                    id = value != null ? value.getIdFood() : 0;
//                    favorited = value != null ? value.getFavoritedFood() : 0;
//
//                    fire.setNameFood(nama_makanan);
//                    fire.setImageFood(gambar_makanan);
//                    fire.setRecipeFood(resep_makanan);
//                    fire.setIdFood(id);
//                    fire.setFavoritedFood(favorited);
//
//                    makananList.add(fire);
//
//                    adapterMakanan = new AdapterTopView(makananList, TopFoodActivity.this);
//                    recyclerView.setHasFixedSize(true);
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setAdapter(adapterMakanan);
//                    linearLayoutManager.setReverseLayout(true);
//                    linearLayoutManager.setStackFromEnd(true);
//                    adapterMakanan.notifyDataSetChanged();
//                }
//
//                progressDialog.dismiss();
//                mRef.keepSynced(true);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("Firebase: ", "failed to read value.", databaseError.toException());
//            }
//        });
//    }
}
