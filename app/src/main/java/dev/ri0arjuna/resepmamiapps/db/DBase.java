package dev.ri0arjuna.resepmamiapps.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.LinkedList;
import java.util.List;

import dev.ri0arjuna.resepmamiapps.model.ModelMakanan;

public class DBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db_favorite";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Favorite";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAMA_MAKANAN = "nama_makanan";
    public static final String COLUMN_GAMBAR_MAKANAN = "gambar_makanan";
    public static final String COLUMN_RESEP_MAKANAN = "resep_makanan";
    Context context;

    public DBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA_MAKANAN + " TEXT NOT NULL, " +
                COLUMN_GAMBAR_MAKANAN + " NUMBER NOT NULL, " +
                COLUMN_RESEP_MAKANAN + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /**
     * Query records, give options to filter results
     **/
    public List<ModelMakanan> makananList() {
        String query;
        query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAMA_MAKANAN + " ASC";

        List<ModelMakanan> makananLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        ModelMakanan modelMakanan;

        if (cursor.moveToFirst()) {
            do {
                modelMakanan = new ModelMakanan();

                modelMakanan.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                modelMakanan.setNama_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_MAKANAN)));
                modelMakanan.setGambar_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_GAMBAR_MAKANAN)));
                modelMakanan.setResep_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_RESEP_MAKANAN)));
                makananLinkedList.add(modelMakanan);
            } while (cursor.moveToNext());
        }
        return makananLinkedList;
    }

    /**
     * Query only 1 record
     **/
    public ModelMakanan getMakanan(long nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE nama_makanan=" + /*PAKE LIKE QUERY*/ nama;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);

        ModelMakanan receivedMakanan = new ModelMakanan();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedMakanan.setNama_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_NAMA_MAKANAN)));
            receivedMakanan.setGambar_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_GAMBAR_MAKANAN)));
            receivedMakanan.setResep_makanan(cursor.getString(cursor.getColumnIndex(COLUMN_RESEP_MAKANAN)));
        }
        return receivedMakanan;
    }
}
