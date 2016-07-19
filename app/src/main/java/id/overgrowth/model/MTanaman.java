package id.overgrowth.model;

import java.util.Date;

/**
 * Created by bayu_wpp on 5/23/2016.
 */
public class MTanaman {
    private int idtanaman;
    private String namatanaman;
    private String jenistanaman;
    private String awalpanen;
    private String lamapanen;
    private String deskripsi;
    private String fototanaman;
    private String cocokdimusim;

    public MTanaman(int idtanaman, String namatanaman, String jenistanaman, String awalpanen, String lamapanen, String deskripsi, String fototanaman, String cocokdimusim) {
        this.idtanaman = idtanaman;
        this.namatanaman = namatanaman;
        this.jenistanaman = jenistanaman;
        this.awalpanen = awalpanen;
        this.lamapanen = lamapanen;
        this.deskripsi = deskripsi;
        this.fototanaman = fototanaman;
        this.cocokdimusim = cocokdimusim;
    }

    public MTanaman() {

    }

    public int getIdtanaman() {
        return idtanaman;
    }

    public void setIdtanaman(int idtanaman) {
        this.idtanaman = idtanaman;
    }

    public String getNamatanaman() {
        return namatanaman;
    }

    public void setNamatanaman(String namatanaman) {
        this.namatanaman = namatanaman;
    }

    public String getJenistanaman() {
        return jenistanaman;
    }

    public void setJenistanaman(String jenistanaman) {
        this.jenistanaman = jenistanaman;
    }

    public String getAwalpanen() {
        return awalpanen;
    }

    public void setAwalpanen(String awalpanen) {
        this.awalpanen = awalpanen;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLamapanen() {
        return lamapanen;
    }

    public void setLamapanen(String lamapanen) {
        this.lamapanen = lamapanen;
    }

    public String getFototanaman() {
        return fototanaman;
    }

    public void setFototanaman(String fototanaman) {
        this.fototanaman = fototanaman;
    }

    public String getCocokdimusim() {
        return cocokdimusim;
    }

    public void setCocokdimusim(String cocokdimusim) {
        this.cocokdimusim = cocokdimusim;
    }
}
