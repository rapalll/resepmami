package dev.ri0arjuna.resepmamiapps.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelTopFood implements Parcelable {
    private String nameFood, recipeFood, imageFood;
    private int idFood, favoritedFood;

    public ModelTopFood(String nameFood, String recipeFood, String imageFood, int idFood, int favoritedFood) {
        this.nameFood = nameFood;
        this.recipeFood = recipeFood;
        this.imageFood = imageFood;
        this.idFood = idFood;
        this.favoritedFood = favoritedFood;
    }

    public ModelTopFood() {
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getRecipeFood() {
        return recipeFood;
    }

    public void setRecipeFood(String recipeFood) {
        this.recipeFood = recipeFood;
    }

    public String getImageFood() {
        return imageFood;
    }

    public void setImageFood(String imageFood) {
        this.imageFood = imageFood;
    }

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
    }

    public int getFavoritedFood() {
        return favoritedFood;
    }

    public void setFavoritedFood(int favoritedFood) {
        this.favoritedFood = favoritedFood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameFood);
        dest.writeString(this.recipeFood);
        dest.writeString(this.imageFood);
        dest.writeInt(this.idFood);
        dest.writeInt(this.favoritedFood);
    }

    protected ModelTopFood(Parcel in) {
        this.nameFood = in.readString();
        this.recipeFood = in.readString();
        this.imageFood = in.readString();
        this.idFood = in.readInt();
        this.favoritedFood = in.readInt();
    }

    public static final Creator<ModelTopFood> CREATOR = new Creator<ModelTopFood>() {
        @Override
        public ModelTopFood createFromParcel(Parcel source) {
            return new ModelTopFood(source);
        }

        @Override
        public ModelTopFood[] newArray(int size) {
            return new ModelTopFood[size];
        }
    };
}

//
//    String nama_makanan;
//    String resep_makanan;
//    String gambar_makanan;
//    int id, favorited;
//
//    public ModelTopFood(String nama_makanan, String resep_makanan, String gambar_makanan, int id, int favorited) {
//        this.nama_makanan = nama_makanan;
//        this.resep_makanan = resep_makanan;
//        this.gambar_makanan = gambar_makanan;
//        this.id = id;
//        this.favorited = favorited;
//    }
//
//    public ModelTopFood() {
//    }
////
////    @Override
////    public String toString() {
////        return "ModelTopFood{" +
////                "nama_makanan='" + nama_makanan + '\'' +
////                ", resep_makanan='" + resep_makanan + '\'' +
////                ", gambar_makanan='" + gambar_makanan + '\'' +
////                ", id=" + id +
////                ", favorited=" + favorited +
////                '}';
////    }
//
//    public String getNama_makanan() {
//        return nama_makanan;
//    }
//
//    public void setNama_makanan(String nama_makanan) {
//        this.nama_makanan = nama_makanan;
//    }
//
//    public String getResep_makanan() {
//        return resep_makanan;
//    }
//
//    public void setResep_makanan(String resep_makanan) {
//        this.resep_makanan = resep_makanan;
//    }
//
//    public String getGambar_makanan() {
//        return gambar_makanan;
//    }
//
//    public void setGambar_makanan(String gambar_makanan) {
//        this.gambar_makanan = gambar_makanan;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public int getFavorited() {
//        return favorited;
//    }
//
//    public void setFavorited(int favorited) {
//        this.favorited = favorited;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.nama_makanan);
//        dest.writeString(this.resep_makanan);
//        dest.writeString(this.gambar_makanan);
//        dest.writeInt(this.id);
//        dest.writeInt(this.favorited);
//    }
//
//    protected ModelTopFood(Parcel in) {
//        this.nama_makanan = in.readString();
//        this.resep_makanan = in.readString();
//        this.gambar_makanan = in.readString();
//        this.id = in.readInt();
//        this.favorited = in.readInt();
//    }
//
//    public static final Parcelable.Creator<ModelTopFood> CREATOR = new Parcelable.Creator<ModelTopFood>() {
//        @Override
//        public ModelTopFood createFromParcel(Parcel source) {
//            return new ModelTopFood(source);
//        }
//
//        @Override
//        public ModelTopFood[] newArray(int size) {
//            return new ModelTopFood[size];
//        }
//    };