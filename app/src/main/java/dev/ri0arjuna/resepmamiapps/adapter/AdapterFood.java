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
import com.google.gson.GsonBuilder;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.activity.detailfood.DetailFoodActivity;
import dev.ri0arjuna.resepmamiapps.db.DBase;
import dev.ri0arjuna.resepmamiapps.model.ModelFood;

import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_GAMBAR_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_ID;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_NAMA_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_RESEP_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.TABLE_NAME;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.HolderMakanan> {
    private List<ModelFood> makananList;
    private Context context;

    public AdapterFood(List<ModelFood> makananList, Context context) {
        this.makananList = makananList;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderMakanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new HolderMakanan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HolderMakanan holder, @SuppressLint("RecyclerView") final int position) {
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

    public int getItemCount() {
        int arr = 0;

        try {
            if (makananList.size() == 0) {
                return arr;
            } else {
                arr = makananList.size();
            }
        } catch (Exception ignored) {
        }
        return arr;
    }

    class HolderMakanan extends RecyclerView.ViewHolder {
        private TextView textViewTitleFood;
        private ImageView imageViewPosterFood;

        HolderMakanan(View itemView) {
            super(itemView);
            textViewTitleFood = itemView.findViewById(R.id.title_food);
            imageViewPosterFood = itemView.findViewById(R.id.image_poster);
        }
    }
}
