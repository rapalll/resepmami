package dev.ri0arjuna.resepmamiapps.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.activity.DetailMakananActivity;
import dev.ri0arjuna.resepmamiapps.db.DBase;
import dev.ri0arjuna.resepmamiapps.model.ModelMakanan;

import static dev.ri0arjuna.resepmamiapps.db.DBase.TABLE_NAME;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.HolderFavorite> {
    private List<ModelMakanan> makananList;
    private Context context;
    private DBase dBase;
    private RecyclerView mRecyclerV;

    public AdapterFavorite(List<ModelMakanan> makananList, Context context, RecyclerView mRecyclerView) {
        this.makananList = makananList;
        this.context = context;
        this.mRecyclerV = mRecyclerView;
    }

    @NonNull
    @Override
    public AdapterFavorite.HolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dBase = new DBase(context.getApplicationContext());

        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new HolderFavorite(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavorite.HolderFavorite holder, @SuppressLint("RecyclerView") final int position) {
        final ModelMakanan modelMakanan = makananList.get(position);

        holder.favoriteButton.setChecked(false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();

        try {
            if (preferences.contains(String.valueOf(modelMakanan.getId())) &&
                    preferences.getBoolean(String.valueOf(modelMakanan.getId()), false)) {
                holder.favoriteButton.setChecked(true);
            } else {
                holder.favoriteButton.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.favoriteButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    editor.putBoolean(String.valueOf(modelMakanan.getId()), true);
                    editor.apply();
                } else {
                    SQLiteDatabase db = dBase.getWritableDatabase();

                    db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + modelMakanan.getId() + "'");

                    makananList.remove(position);
                    notifyItemRangeChanged(holder.getAdapterPosition(), makananList.size());
                    notifyDataSetChanged();

                    editor.putBoolean(String.valueOf(modelMakanan.getId()), false);
                    editor.apply();
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {

            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {

            }
        });

        String str = modelMakanan.getNama_makanan();
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap + " ");
        }

        holder.textViewTitleFood.setText(builder.toString());
        Glide.with(context)
                .load(modelMakanan.getGambar_makanan())
                .into(holder.imageViewPosterFood);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelMakanan data = makananList.get(position);
                Intent intent = new Intent(context, DetailMakananActivity.class);
                intent.putExtra("detail_makanan", new GsonBuilder().create().toJson(data));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try {
            if (makananList.size() == 0) {
                Snackbar snackbar = Snackbar.make(mRecyclerV.getRootView(), "Empty foodvorite", Snackbar.LENGTH_INDEFINITE)
                        .setAction("HOME", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((Activity) context).finish();
                            }
                        });
                snackbar.show();
                arr = 0;
            } else {
                arr = makananList.size();
            }
        } catch (Exception ignored) {

        }
        return arr;
    }

    class HolderFavorite extends RecyclerView.ViewHolder {
        TextView textViewTitleFood;
        ImageView imageViewPosterFood;
        SparkButton favoriteButton;

        HolderFavorite(View itemView) {
            super(itemView);
            textViewTitleFood = itemView.findViewById(R.id.title_food);
            imageViewPosterFood = itemView.findViewById(R.id.image_poster);
            favoriteButton = itemView.findViewById(R.id.spark_button);
        }
    }
}