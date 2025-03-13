package bdbt.project.korttenisowy.adres;

public class Adres {
    private int idAdres;
    private String kraj;
    private String miasto;
    private String ulica;
    private String numerBudynku;

    public Adres() {}

    public Adres(int idAdres, String kraj, String miasto, String ulica, String numerBudynku) {
        this.idAdres = idAdres;
        this.kraj = kraj;
        this.miasto = miasto;
        this.ulica = ulica;
        this.numerBudynku = numerBudynku;
    }

    public int getIdAdres() {
        return idAdres;
    }

    public void setIdAdres(int idAdres) {
        this.idAdres = idAdres;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public String getNumerBudynku() {
        return numerBudynku;
    }

    public void setNumerBudynku(String numerBudynku) {
        this.numerBudynku = numerBudynku;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "idAdres=" + idAdres +
                ", kraj='" + kraj + '\'' +
                ", miasto='" + miasto + '\'' +
                ", ulica='" + ulica + '\'' +
                ", numerBudynku='" + numerBudynku + '\'' +
                '}';
    }
}
