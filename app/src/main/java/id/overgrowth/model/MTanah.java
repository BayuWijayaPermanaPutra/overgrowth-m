package id.overgrowth.model;

/**
 * Created by bayu_wpp on 5/26/2016.
 */
public class MTanah {

    private int id_tanah;
    private String nama_tanah;
    private String deskripsi_tanah;

    public MTanah(int id_tanah, String nama_tanah, String deskripsi_tanah) {
        this.id_tanah = id_tanah;
        this.nama_tanah = nama_tanah;
        this.deskripsi_tanah = deskripsi_tanah;
    }

    public int getId_tanah() {
        return id_tanah;
    }

    public void setId_tanah(int id_tanah) {
        this.id_tanah = id_tanah;
    }

    public String getNama_tanah() {
        return nama_tanah;
    }

    public void setNama_tanah(String nama_tanah) {
        this.nama_tanah = nama_tanah;
    }

    public String getDeskripsi_tanah() {
        return deskripsi_tanah;
    }

    public void setDeskripsi_tanah(String deskripsi_tanah) {
        this.deskripsi_tanah = deskripsi_tanah;
    }
}
