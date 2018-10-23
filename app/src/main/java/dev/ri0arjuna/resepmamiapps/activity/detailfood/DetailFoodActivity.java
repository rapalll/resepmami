package dev.ri0arjuna.resepmamiapps.activity.detailfood;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.GsonBuilder;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;
import com.victor.loading.rotate.RotateLoading;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import dev.ri0arjuna.resepmamiapps.R;
import dev.ri0arjuna.resepmamiapps.db.DBase;
import dev.ri0arjuna.resepmamiapps.model.ModelFood;

import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_FAVORITE_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_GAMBAR_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_ID;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_NAMA_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.COLUMN_RESEP_MAKANAN;
import static dev.ri0arjuna.resepmamiapps.db.DBase.TABLE_NAME;

public class DetailFoodActivity extends AppCompatActivity
        implements View.OnClickListener, SparkEventListener {
    private Toolbar toolbar;
    private ModelFood modelFood;
    private Uri uri;
    private PDFView pdfView;
    private RotateLoading rotateLoading;
    private ImageButton buttonShare;
    private StringBuilder builder;
    private SparkButton favoriteButton;
    private DBase dBase;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private int id, fav;
    private String nama_makanan, gambar_makanan, resep_makanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);

        instantiate();
        init();
        setupItem();
        checkedFavorite();

        new RetreivePDFStream().execute(uri.toString());

        buttonShare.setOnClickListener(this);
        favoriteButton.setEventListener(this);
    }

    @SuppressLint("StaticFieldLeak")
    private class RetreivePDFStream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected void onPreExecute() {
            rotateLoading.start();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            pdfView.fromStream(inputStream)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .defaultPage(0)
                    .load();

            rotateLoading.stop();
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void checkedFavorite() {
        favoriteButton.setChecked(false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        try {
            if (preferences.contains(String.valueOf(modelFood.getIdFood())) &&
                    preferences.getBoolean(String.valueOf(modelFood.getIdFood()), false)) {
                favoriteButton.setChecked(true);
            } else {
                favoriteButton.setChecked(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupItem() {
        String str = modelFood.getNameFood();
        String[] strArray = str.split(" ");
        builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
            builder.append(cap).append(" ");
        }

        toolbar.setTitle(builder.toString());
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        uri = Uri.parse(modelFood.getRecipeFood());
    }

    private void init() {
        favoriteButton = findViewById(R.id.btn_favorite);
        toolbar = findViewById(R.id.toolbar);
        pdfView = findViewById(R.id.pdfview);
        buttonShare = findViewById(R.id.btn_share);
        rotateLoading = findViewById(R.id.rotateloading);
    }

    private void instantiate() {
        rotateLoading = new RotateLoading(this);
        modelFood = new GsonBuilder().create().fromJson(getIntent().getStringExtra("detail_makanan"), ModelFood.class);

        Toast.makeText(this, ""+modelFood.getNameFood() + modelFood.getFavoritedFood(), Toast.LENGTH_SHORT).show();

        uri = Uri.parse(modelFood.getRecipeFood());
        dBase = new DBase(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    getResources().getString(R.string.nama_makanan) + builder.toString() + "\n" +
                            getResources().getString(R.string.download_resep_makanan) + uri + "\n\n" +
                            getResources().getString(R.string.selamat_menikmati));
            startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
        }
    }

    @Override
    public void onEvent(ImageView button, boolean buttonState) {
        fav = modelFood.getFavoritedFood();
        id = modelFood.getIdFood();
        nama_makanan = modelFood.getNameFood();
        gambar_makanan = modelFood.getImageFood();
        resep_makanan = modelFood.getRecipeFood();

        if (buttonState) {
            final int count = fav + 1;

            updateFav(nama_makanan, resep_makanan, gambar_makanan, id, count);

            SQLiteDatabase db = dBase.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, modelFood.getIdFood());
            values.put(COLUMN_NAMA_MAKANAN, modelFood.getNameFood());
            values.put(COLUMN_GAMBAR_MAKANAN, modelFood.getImageFood());
            values.put(COLUMN_RESEP_MAKANAN, modelFood.getRecipeFood());
            values.put(COLUMN_FAVORITE_MAKANAN, modelFood.getFavoritedFood());

            db.insert(TABLE_NAME, null, values);
            db.close();

            editor.putBoolean(String.valueOf(modelFood.getIdFood()), true);
            editor.apply();
        } else {
            final int count = fav - 1;
            updateFav(nama_makanan, resep_makanan, gambar_makanan, id, count);

            SQLiteDatabase db = dBase.getWritableDatabase();

            db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + modelFood.getIdFood() + "'");

            editor.putBoolean(String.valueOf(modelFood.getIdFood()), false);
            editor.apply();
        }
    }

    private void updateFav(String nama_makanan, String resep_makanan, String gambar_makanan, int id, int count) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Makanan");
        modelFood = new ModelFood(nama_makanan, resep_makanan, gambar_makanan, id, count);

        databaseReference.child(String.valueOf(id)).setValue(modelFood);
    }

    @Override
    public void onEventAnimationEnd(ImageView button, boolean buttonState) {

    }

    @Override
    public void onEventAnimationStart(ImageView button, boolean buttonState) {

    }
}