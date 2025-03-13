package bdbt.project.korttenisowy.rezerwacja;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Rezerwacja {
    private int idRezerwacji;
    private int idKlient;
    private int idKort;
    private Date dataRezerwacji;
    private String godzinaRozpoczeciaRezerwacji;
    private String godzinaZakonczeniaRezerwacji;
    private char statusAktywnosci;
    private String imieKlienta; // Dodane pole
    private String nazwiskoKlienta; // Dodane pole
    private String nazwaKortu; // Dodane pole

    // Gettery i settery
    public int getIdRezerwacji() { return idRezerwacji; }
    public void setIdRezerwacji(int idRezerwacji) { this.idRezerwacji = idRezerwacji; }

    public int getIdKlient() { return idKlient; }
    public void setIdKlient(int idKlient) { this.idKlient = idKlient; }

    public int getIdKort() { return idKort; }
    public void setIdKort(int idKort) { this.idKort = idKort; }

    public Date getDataRezerwacji() { return dataRezerwacji; }
    public void setDataRezerwacji(Date dataRezerwacji) { this.dataRezerwacji = dataRezerwacji; }

    public String getGodzinaRozpoczeciaRezerwacji() {
        return godzinaRozpoczeciaRezerwacji;
    }

    public void setGodzinaRozpoczeciaRezerwacji(String godzinaRozpoczeciaRezerwacji) {
        this.godzinaRozpoczeciaRezerwacji = godzinaRozpoczeciaRezerwacji;
    }

    public String getGodzinaZakonczeniaRezerwacji() {
        return godzinaZakonczeniaRezerwacji;
    }

    public void setGodzinaZakonczeniaRezerwacji(String godzinaZakonczeniaRezerwacji) {
        this.godzinaZakonczeniaRezerwacji = godzinaZakonczeniaRezerwacji;
    }

    public char getStatusAktywnosci() {
        return statusAktywnosci;
    }

    public void setStatusAktywnosci(char statusAktywnosci) {
        this.statusAktywnosci = statusAktywnosci;
    }

    public String getImieKlienta() { return imieKlienta; }
    public void setImieKlienta(String imieKlienta) { this.imieKlienta = imieKlienta; }

    public String getNazwiskoKlienta() { return nazwiskoKlienta; }
    public void setNazwiskoKlienta(String nazwiskoKlienta) { this.nazwiskoKlienta = nazwiskoKlienta; }

    public String getNazwaKortu() { return nazwaKortu; }
    public void setNazwaKortu(String nazwaKortu) { this.nazwaKortu = nazwaKortu; }

}
