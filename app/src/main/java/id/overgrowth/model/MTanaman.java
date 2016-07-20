package id.overgrowth.model;

import java.util.Date;

/**
 * Created by bayu_wpp on 5/23/2016.
 */
public class MTanaman {
    private int idtanaman;
    private String namatanaman;
    private String jenistanaman;
    private int lamapanen;
    private String deskripsi;
    private String fototanaman;
    private String cocokdimusim;

    public MTanaman(int idtanaman, String namatanaman, String jenistanaman, int lamapanen, String deskripsi, String fototanaman, String cocokdimusim) {
        this.idtanaman = idtanaman;
        this.namatanaman = namatanaman;
        this.jenistanaman = jenistanaman;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getLamapanen() {
        return lamapanen;
    }

    public void setLamapanen(int lamapanen) {
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
