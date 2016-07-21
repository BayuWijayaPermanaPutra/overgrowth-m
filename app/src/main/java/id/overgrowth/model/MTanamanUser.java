package id.overgrowth.model;

/**
 * Created by bayu_wpp on 7/20/2016.
 */
public class MTanamanUser {
    private int idTanamanUser;
    private int idTanaman;
    private String namaTanaman;
    private String jenisTanaman;
    private int lamaPanen;
    private String deskripsiTanaman;
    private String fotoTanaman;
    private String cocokDiMusim;
    private String waktuMenanam;
    private String terakhirDiSiram;

    public MTanamanUser(int idTanamanUser, int idTanaman, String namaTanaman, String jenisTanaman, int lamaPanen, String deskripsiTanaman, String fotoTanaman, String cocokDiMusim, String waktuMenanam, String terakhirDiSiram) {
        this.idTanamanUser = idTanamanUser;
        this.idTanaman = idTanaman;
        this.namaTanaman = namaTanaman;
        this.jenisTanaman = jenisTanaman;
        this.lamaPanen = lamaPanen;
        this.deskripsiTanaman = deskripsiTanaman;
        this.fotoTanaman = fotoTanaman;
        this.cocokDiMusim = cocokDiMusim;
        this.waktuMenanam = waktuMenanam;
        this.terakhirDiSiram = terakhirDiSiram;
    }

    public MTanamanUser() {

    }

    public int getIdTanamanUser() {
        return idTanamanUser;
    }

    public void setIdTanamanUser(int idTanamanUser) {
        this.idTanamanUser = idTanamanUser;
    }

    public int getIdTanaman() {
        return idTanaman;
    }

    public void setIdTanaman(int idTanaman) {
        this.idTanaman = idTanaman;
    }

    public String getNamaTanaman() {
        return namaTanaman;
    }

    public void setNamaTanaman(String namaTanaman) {
        this.namaTanaman = namaTanaman;
    }

    public String getJenisTanaman() {
        return jenisTanaman;
    }

    public void setJenisTanaman(String jenisTanaman) {
        this.jenisTanaman = jenisTanaman;
    }

    public int getLamaPanen() {
        return lamaPanen;
    }

    public void setLamaPanen(int lamaPanen) {
        this.lamaPanen = lamaPanen;
    }

    public String getDeskripsiTanaman() {
        return deskripsiTanaman;
    }

    public void setDeskripsiTanaman(String deskripsiTanaman) {
        this.deskripsiTanaman = deskripsiTanaman;
    }

    public String getFotoTanaman() {
        return fotoTanaman;
    }

    public void setFotoTanaman(String fotoTanaman) {
        this.fotoTanaman = fotoTanaman;
    }

    public String getCocokDiMusim() {
        return cocokDiMusim;
    }

    public void setCocokDiMusim(String cocokDiMusim) {
        this.cocokDiMusim = cocokDiMusim;
    }

    public String getWaktuMenanam() {
        return waktuMenanam;
    }

    public void setWaktuMenanam(String waktuMenanam) {
        this.waktuMenanam = waktuMenanam;
    }

    public String getTerakhirDiSiram() {
        return terakhirDiSiram;
    }

    public void setTerakhirDiSiram(String terakhirDiSiram) {
        this.terakhirDiSiram = terakhirDiSiram;
    }
}
