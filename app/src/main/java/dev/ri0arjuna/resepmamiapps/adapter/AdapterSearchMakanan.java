package dev.ri0arjuna.resepmamiapps.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.model.ModelMakanan;
import dev.ri0arjuna.resepmamiapps.model.ModelSearchMakanan;

public class AdapterSearchMakanan extends RecyclerView.Adapter<AdapterSearchMakanan.HolderMakanan> {
    List<ModelSearchMakanan> makananList;
    Context context;

    public AdapterSearchMakanan(List<ModelSearchMakanan> makananList, Context context) {
        this.makananList = makananList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSearchMakanan.HolderMakanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);
        return new AdapterSearchMakanan.HolderMakanan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSearchMakanan.HolderMakanan holder, int position) {
        holder.textViewTitleFood.setText(makananList.get(position).getNama_makanan().toUpperCase());
        Glide.with(context)
                .load(makananList.get(position).getGambar_makanan())
                .into(holder.imageViewPosterFood);
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public class HolderMakanan extends RecyclerView.ViewHolder {
        TextView textViewTitleFood;
        ImageView imageViewPosterFood;

        public HolderMakanan(View itemView) {
            super(itemView);
            textViewTitleFood = itemView.findViewById(R.id.title_food);
            imageViewPosterFood = itemView.findViewById(R.id.image_poster);
        }
    }
}
