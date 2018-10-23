package dev.ri0arjuna.resepmamiapps.adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.GsonBuilder;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.model.ModelTopFood;
import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.activity.detailfood.DetailFoodActivity;
import dev.ri0arjuna.resepmamiapps.db.DBase;
import dev.ri0arjuna.resepmamiapps.model.ModelFood;

import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_GAMBAR_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_ID;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_NAMA_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_RESEP_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.TABLE_NAME;

public class AdapterTopView extends RecyclerView.Adapter<AdapterTopView.ViewHolder> {
    private List<ModelTopFood> makananList;
    private Context context;
//    private DBase dBase;

    public AdapterTopView(List<ModelTopFood> makananList, Context context) {
        this.makananList = makananList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterTopView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_view, parent, false);
        return new AdapterTopView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterTopView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final ModelTopFood modelMakanan = makananList.get(position);

//        dBase = new DBase(context.getApplicationContext());

        final int fav = modelMakanan.getFavoritedFood();
        final int id = modelMakanan.getIdFood();
        final String nama_makanan = modelMakanan.getNameFood();
        final String gambar_makanan = modelMakanan.getImageFood();
        final String resep_makanan = modelMakanan.getRecipeFood();

//        holder.favoriteButton.setChecked(false);
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        final SharedPreferences.Editor editor = preferences.edit();
//
//        try {
//            if (preferences.contains(String.valueOf(modelMakanan.getIdFood())) &&
//                    preferences.getBoolean(String.valueOf(modelMakanan.getIdFood()), false)) {
//                holder.favoriteButton.setChecked(true);
//            } else {
//                holder.favoriteButton.setChecked(false);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        holder.favoriteButton.setEventListener(new SparkEventListener() {
//            @Override
//            public void onEvent(ImageView button, boolean buttonState) {
//                if (buttonState) {
//                    final int count = fav + 1;
//                    updateFav(nama_makanan, resep_makanan, gambar_makanan, id, count);
//
//                    SQLiteDatabase db = dBase.getWritableDatabase();
//                    ContentValues values = new ContentValues();
//                    values.put(COLUMN_ID, modelMakanan.getIdFood());
//                    values.put(COLUMN_NAMA_MAKANAN, modelMakanan.getNameFood());
//                    values.put(COLUMN_GAMBAR_MAKANAN, modelMakanan.getImageFood());
//                    values.put(COLUMN_RESEP_MAKANAN, modelMakanan.getRecipeFood());
//
//                    // insert
//                    db.insert(TABLE_NAME, null, values);
//                    db.close();
//
//                    editor.putBoolean(String.valueOf(modelMakanan.getIdFood()), true);
//                    editor.apply();
//                } else{
//                    final int count = fav - 1;
//                    updateFav(nama_makanan, resep_makanan, gambar_makanan, id, count);
//
//                    SQLiteDatabase db = dBase.getWritableDatabase();
//
//                    db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + modelMakanan.getIdFood() + "'");
//
//                    editor.putBoolean(String.valueOf(modelMakanan.getIdFood()), false);
//                    editor.apply();
//                }
//            }
//
//            @Override
//            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
//            }
//
//            @Override
//            public void onEventAnimationStart(ImageView button, boolean buttonState) {
//
//            }
//        });

        String str = modelMakanan.getNameFood();
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }

        holder.textViewFav.setText("Ranked: " + String.valueOf(modelMakanan.getFavoritedFood()));
        holder.textViewTitleFood.setText(builder.toString());
        Glide.with(context)
                .load(modelMakanan.getImageFood())
                .into(holder.imageViewPosterFood);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelTopFood data = makananList.get(position);
                Intent intent = new Intent(context, DetailFoodActivity.class);
                intent.putExtra("detail_makanan", new GsonBuilder().create().toJson(data));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

//    private void updateFav(String nama_makanan, String resep_makanan, String gambar_makanan, int id, int count) {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Makanan");
//        ModelFood modelMakanan = new ModelFood(nama_makanan, resep_makanan, gambar_makanan, id, count);
//
//        databaseReference.child(String.valueOf(id)).setValue(modelMakanan);
//    }

    public int getItemCount() {
        int arr = 0;

        try {
            if (makananList.size() == 0) {
                arr = 0;
            } else {
                arr = makananList.size();
            }
        } catch (Exception ignored) {
        }
        return arr;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitleFood, textViewFav;
        ImageView imageViewPosterFood;
//        SparkButton favoriteButton;

        ViewHolder(View itemView) {
            super(itemView);
            textViewFav = itemView.findViewById(R.id.favorited);
            textViewTitleFood = itemView.findViewById(R.id.title_food);
            imageViewPosterFood = itemView.findViewById(R.id.image_poster);
//            favoriteButton = itemView.findViewById(R.id.spark_button);
        }
    }
}
