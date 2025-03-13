package bdbt.project.korttenisowy.rezerwacja;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class RezerwacjaDAO {
    private final JdbcTemplate jdbcTemplate;

    public RezerwacjaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Rezerwacja rezerwacja) {
        String sql = """
                INSERT INTO REZERWACJE (ID_KLIENT, ID_KORT, DATA_REZERWACJI, 
                                        GODZINA_ROZPOCZECIA_REZERWACJI, GODZINA_ZAKONCZENIA_REZERWACJI, STATUS_AKTYWNOSCI)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        jdbcTemplate.update(sql,
                rezerwacja.getIdKlient(),
                rezerwacja.getIdKort(),
                rezerwacja.getDataRezerwacji(),
                Timestamp.valueOf(rezerwacja.getDataRezerwacji().toString() + " " + rezerwacja.getGodzinaRozpoczeciaRezerwacji() + ":00"),
                Timestamp.valueOf(rezerwacja.getDataRezerwacji().toString() + " " + rezerwacja.getGodzinaZakonczeniaRezerwacji() + ":00"),
                rezerwacja.getStatusAktywnosci());
    }

    public List<Rezerwacja> findAll() {
        String sql = """
        SELECT r.ID_REZERWACJI, r.ID_KLIENT, r.ID_KORT, r.DATA_REZERWACJI, 
               r.GODZINA_ROZPOCZECIA_REZERWACJI, r.GODZINA_ZAKONCZENIA_REZERWACJI, 
               r.STATUS_AKTYWNOSCI, k.IMIE AS IMIE_KLIENTA, k.NAZWISKO AS NAZWISKO_KLIENTA, 
               kt.NAZWA AS NAZWA_KORTU
        FROM REZERWACJE r
        JOIN KLIENCI k ON r.ID_KLIENT = k.ID_KLIENT
        JOIN KORTY_TENISOWE kt ON r.ID_KORT = kt.ID_KORT
        """;

        return jdbcTemplate.query(sql, new RowMapper<Rezerwacja>() {
            @Override
            public Rezerwacja mapRow(ResultSet rs, int rowNum) throws SQLException {
                Rezerwacja rezerwacja = new Rezerwacja();
                rezerwacja.setIdRezerwacji(rs.getInt("ID_REZERWACJI"));
                rezerwacja.setIdKlient(rs.getInt("ID_KLIENT"));
                rezerwacja.setIdKort(rs.getInt("ID_KORT"));
                rezerwacja.setDataRezerwacji(rs.getDate("DATA_REZERWACJI"));
                rezerwacja.setGodzinaRozpoczeciaRezerwacji(rs.getString("GODZINA_ROZPOCZECIA_REZERWACJI"));
                rezerwacja.setGodzinaZakonczeniaRezerwacji(rs.getString("GODZINA_ZAKONCZENIA_REZERWACJI"));
                rezerwacja.setStatusAktywnosci(rs.getString("STATUS_AKTYWNOSCI").charAt(0));
                rezerwacja.setImieKlienta(rs.getString("IMIE_KLIENTA"));
                rezerwacja.setNazwiskoKlienta(rs.getString("NAZWISKO_KLIENTA"));
                rezerwacja.setNazwaKortu(rs.getString("NAZWA_KORTU"));
                return rezerwacja;
            }
        });
    }

    public List<Rezerwacja> findByClientId(int idKlient) {
        String sql = """
        SELECT r.ID_REZERWACJI, r.ID_KLIENT, r.ID_KORT, r.DATA_REZERWACJI, 
               r.GODZINA_ROZPOCZECIA_REZERWACJI, r.GODZINA_ZAKONCZENIA_REZERWACJI, 
               r.STATUS_AKTYWNOSCI, kt.NAZWA AS NAZWA_KORTU
        FROM REZERWACJE r
        JOIN KORTY_TENISOWE kt ON r.ID_KORT = kt.ID_KORT
        WHERE r.ID_KLIENT = ?
        """;
        return jdbcTemplate.query(sql, new RowMapper<Rezerwacja>() {
            @Override
            public Rezerwacja mapRow(ResultSet rs, int rowNum) throws SQLException {
                Rezerwacja rezerwacja = new Rezerwacja();
                rezerwacja.setIdRezerwacji(rs.getInt("ID_REZERWACJI"));
                rezerwacja.setIdKlient(rs.getInt("ID_KLIENT"));
                rezerwacja.setIdKort(rs.getInt("ID_KORT"));
                rezerwacja.setDataRezerwacji(rs.getDate("DATA_REZERWACJI"));
                rezerwacja.setGodzinaRozpoczeciaRezerwacji(rs.getString("GODZINA_ROZPOCZECIA_REZERWACJI"));
                rezerwacja.setGodzinaZakonczeniaRezerwacji(rs.getString("GODZINA_ZAKONCZENIA_REZERWACJI"));
                rezerwacja.setStatusAktywnosci(rs.getString("STATUS_AKTYWNOSCI").charAt(0));
                rezerwacja.setNazwaKortu(rs.getString("NAZWA_KORTU"));
                return rezerwacja;
            }
        }, idKlient);
    }

    public void delete(int idRezerwacji) {
        String sql = "DELETE FROM REZERWACJE WHERE ID_REZERWACJI = ?";
        jdbcTemplate.update(sql, idRezerwacji);
    }
}