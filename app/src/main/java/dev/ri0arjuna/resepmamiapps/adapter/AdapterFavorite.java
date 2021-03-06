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
import dev.ri0arjuna.resepmamiapps.activity.detailfood.DetailFoodActivity;
import dev.ri0arjuna.resepmamiapps.db.DBase;
import dev.ri0arjuna.resepmamiapps.model.ModelFood;

import static dev.ri0arjuna.resepmamiapps.db.DBase.TABLE_NAME;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.HolderFavorite> {
    private List<ModelFood> makananList;
    private Context context;
    private RecyclerView mRecyclerV;

    public AdapterFavorite(List<ModelFood> makananList, Context context, RecyclerView mRecyclerView) {
        this.makananList = makananList;
        this.context = context;
        this.mRecyclerV = mRecyclerView;
    }

    @NonNull
    @Override
    public AdapterFavorite.HolderFavorite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new HolderFavorite(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterFavorite.HolderFavorite holder, @SuppressLint("RecyclerView") final int position) {
        final ModelFood modelMakanan = makananList.get(position);

        String str = modelMakanan.getNameFood();
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap).append(" ");
        }

        holder.textViewTitleFood.setText(builder.toString());
        Glide.with(context)
                .load(modelMakanan.getImageFood())
                .into(holder.imageViewPosterFood);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelFood data = makananList.get(position);
                Intent intent = new Intent(context, DetailFoodActivity.class);
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

        HolderFavorite(View itemView) {
            super(itemView);
            textViewTitleFood = itemView.findViewById(R.id.title_food);
            imageViewPosterFood = itemView.findViewById(R.id.image_poster);
        }
    }
}