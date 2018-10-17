package dev.ri0arjuna.resepmamiapps.model;

public class ModelSearchMakanan {
    private String nama_makanan;
    private String resep_makanan;
    private String gambar_makanan;

    public ModelSearchMakanan(String nama_makanan, String resep_makanan, String gambar_makanan) {
        this.nama_makanan = nama_makanan;
        this.resep_makanan = resep_makanan;
        this.gambar_makanan = gambar_makanan;
    }

    public ModelSearchMakanan() {
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getResep_makanan() {
        return resep_makanan;
    }

    public void setResep_makanan(String resep_makanan) {
        this.resep_makanan = resep_makanan;
    }

    public String getGambar_makanan() {
        return gambar_makanan;
    }

    public void setGambar_makanan(String gambar_makanan) {
        this.gambar_makanan = gambar_makanan;
    }
}
