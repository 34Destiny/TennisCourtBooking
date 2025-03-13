package bdbt.project.korttenisowy.kort;

import bdbt.project.korttenisowy.adres.Adres;

public class Kort {
    private int idKort;
    private Adres adres;
    private String nazwa;
    private String opis;

    public Kort() {}

    public Kort(int idKort, Adres adres, String nazwa,String opis) {
        this.idKort = idKort;
        this.adres = adres;
        this.nazwa = nazwa;
        this.opis = opis;
    }

    public int getIdKort() {
        return idKort;
    }

    public void setIdKort(int idKort) {
        this.idKort = idKort;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
}
