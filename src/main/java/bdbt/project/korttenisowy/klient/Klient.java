package bdbt.project.korttenisowy.klient;

import bdbt.project.korttenisowy.adres.Adres;
import bdbt.project.korttenisowy.kontakt.Kontakt;

public class Klient {
    private int idKlient;
    private String imie;
    private String nazwisko;
    private String haslo;
    private String ranga;
    private Adres adres;
    private Kontakt kontakt;

    // Getters and setters
    public int getIdKlient() { return idKlient; }
    public void setIdKlient(int idKlient) { this.idKlient = idKlient; }

    public String getImie() { return imie; }
    public void setImie(String imie) { this.imie = imie; }

    public String getNazwisko() { return nazwisko; }
    public void setNazwisko(String nazwisko) { this.nazwisko = nazwisko; }

    public String getHaslo() { return haslo; }
    public void setHaslo(String haslo) { this.haslo = haslo; }

    public String getRanga() { return ranga; }
    public void setRanga(String ranga) { this.ranga = ranga; }

    public Adres getAdres() { return adres; }
    public void setAdres(Adres adres) { this.adres = adres; }

    public Kontakt getKontakt() { return kontakt; }
    public void setKontakt(Kontakt kontakt) { this.kontakt = kontakt; }
}
