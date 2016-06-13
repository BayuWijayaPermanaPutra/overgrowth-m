package id.overgrowth.model;

/**
 * Created by bayu_wpp on 5/23/2016.
 */
public class MTanaman {
    private int idtanaman;
    private String namatanaman;
    private String namatanamanenglish;
    private String namailmiahtanaman;
    private String jenistanaman;
    private int lamasiram; //dalam jam misal 8 jam
    private int lamapanen; //dalam hari misal 230 hari
    private String fototanaman;

    public MTanaman(int idtanaman, String namatanaman, String namatanamanenglish, String namailmiahtanaman, String jenistanaman, int lamasiram, int lamapanen, String fototanaman) {
        this.idtanaman = idtanaman;
        this.namatanaman = namatanaman;
        this.namatanamanenglish = namatanamanenglish;
        this.namailmiahtanaman = namailmiahtanaman;
        this.jenistanaman = jenistanaman;
        this.lamasiram = lamasiram;
        this.lamapanen = lamapanen;
        this.fototanaman = fototanaman;
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

    public String getNamatanamanenglish() {
        return namatanamanenglish;
    }

    public void setNamatanamanenglish(String namatanamanenglish) {
        this.namatanamanenglish = namatanamanenglish;
    }

    public String getNamailmiahtanaman() {
        return namailmiahtanaman;
    }

    public void setNamailmiahtanaman(String namailmiahtanaman) {
        this.namailmiahtanaman = namailmiahtanaman;
    }

    public String getJenistanaman() {
        return jenistanaman;
    }

    public void setJenistanaman(String jenistanaman) {
        this.jenistanaman = jenistanaman;
    }

    public int getLamasiram() {
        return lamasiram;
    }

    public void setLamasiram(int lamasiram) {
        this.lamasiram = lamasiram;
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
}
