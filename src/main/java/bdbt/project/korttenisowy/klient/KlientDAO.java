package bdbt.project.korttenisowy.klient;

import bdbt.project.korttenisowy.adres.Adres;
import bdbt.project.korttenisowy.kontakt.Kontakt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class KlientDAO {
    private static final Logger logger = LoggerFactory.getLogger(KlientDAO.class);
    private final JdbcTemplate jdbcTemplate;

    public KlientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Klient findByEmail(String email) {
        String sql = """
        SELECT 
            k.ID_KLIENT, 
            k.IMIE, 
            k.NAZWISKO, 
            k.HASLO, 
            k.RANGA,
            a.KRAJ, 
            a.MIASTO, 
            a.ULICA, 
            a.NUMER_BUDYNKU, 
            c.NUMER_TELEFONU, 
            c.ADRES_EMAIL 
        FROM KLIENCI k 
        LEFT JOIN ADRESY a ON k.ID_ADRES = a.ID_ADRES 
        LEFT JOIN KONTAKTY c ON k.ID_KONTAKT = c.ID_KONTAKT 
        WHERE c.ADRES_EMAIL = ?""";

        return jdbcTemplate.queryForObject(sql, new KlientRowMapper(), email);
    }

    public void update(Klient klient) {
        if (klient.getRanga() == null) {
            klient.setRanga(get(klient.getIdKlient()).getRanga());
        }
        String sqlUpdateKlient = """
        UPDATE Klienci
        SET Imie = ?, Nazwisko = ?, Haslo = ?, RANGA = ?
        WHERE ID_KLIENT = ?
    """;
        jdbcTemplate.update(sqlUpdateKlient,
                klient.getImie(),
                klient.getNazwisko(),
                klient.getHaslo(),
                klient.getRanga(),
                klient.getIdKlient()
        );

        String sqlUpdateAdres = """
        UPDATE Adresy
        SET Kraj = ?, Miasto = ?, Ulica = ?, Numer_Budynku = ?
        WHERE ID_ADRES = (
            SELECT ID_ADRES 
            FROM Klienci 
            WHERE ID_KLIENT = ?
        )
    """;
        jdbcTemplate.update(sqlUpdateAdres,
                klient.getAdres().getKraj(),
                klient.getAdres().getMiasto(),
                klient.getAdres().getUlica(),
                klient.getAdres().getNumerBudynku(),
                klient.getIdKlient()
        );
        String sqlUpdateKontakt = """
        UPDATE Kontakty
        SET Numer_Telefonu = ?, Adres_Email = ?
        WHERE ID_KONTAKT = (
            SELECT ID_KONTAKT 
            FROM Klienci 
            WHERE ID_KLIENT = ?
        )
    """;
        jdbcTemplate.update(sqlUpdateKontakt,
                klient.getKontakt().getTelefon(),
                klient.getKontakt().getEmail(),
                klient.getIdKlient()
        );

        logger.info("Zaktualizowano klienta o ID: {}", klient.getIdKlient());
    }

    public void delete(int idKlient) {
        String sqlDeleteKlient = """
        DELETE FROM Klienci 
       WHERE ID_KLIENT = ?
        """;
        jdbcTemplate.update(sqlDeleteKlient, idKlient);
    }


    public Klient get(int idKlient) {
        String sql = """
                SELECT
                    k.ID_KLIENT,
                    k.IMIE,
                    k.NAZWISKO,
                    k.HASLO,
                    k.RANGA,
                    a.KRAJ,
                    a.MIASTO,
                    a.ULICA,
                    a.NUMER_BUDYNKU,
                    c.NUMER_TELEFONU,
                    c.ADRES_EMAIL
                FROM
                    KLIENCI k
                LEFT JOIN
                    ADRESY a
                ON
                    k.ID_ADRES = a.ID_ADRES
                LEFT JOIN
                    Kontakty c
                ON
                    k.ID_KONTAKT = c.ID_KONTAKT
                WHERE
                    k.ID_KLIENT = ?""";
        return jdbcTemplate.queryForObject(sql, new KlientDAO.KlientRowMapper(), idKlient);
    }

    public void save(Klient klient) {
        klient.setHaslo(passwordEncoder.encode(klient.getHaslo()));
        if (klient.getRanga() == null || klient.getRanga().isEmpty()) {
            klient.setRanga("ROLE_USER");
        }
        if (klient.getAdres() != null) {
            String sqlAdres = "INSERT INTO Adresy (Kraj, Miasto, Ulica, Numer_Budynku) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlAdres, new String[] {"ID_adres"});
                ps.setString(1, klient.getAdres().getKraj());
                ps.setString(2, klient.getAdres().getMiasto());
                ps.setString(3, klient.getAdres().getUlica());
                ps.setString(4, klient.getAdres().getNumerBudynku());
                return ps;
            }, keyHolder);

            klient.getAdres().setIdAdres(keyHolder.getKey().intValue());
        }
        if (klient.getKontakt() != null) {
            String sqlKontakt = "INSERT INTO Kontakty (Numer_Telefonu, Adres_Email) VALUES (?, ?)";
            KeyHolder keyHolderKontakt = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlKontakt, new String[] {"ID_kontakt"});
                ps.setString(1, klient.getKontakt().getTelefon());
                ps.setString(2, klient.getKontakt().getEmail());
                return ps;
            }, keyHolderKontakt);
            klient.getKontakt().setIdKontakt(keyHolderKontakt.getKey().intValue());
        }

        String sqlKlient = "INSERT INTO Klienci (Imie, Nazwisko,HASLO, RANGA, ID_adres, ID_kontakt) VALUES (?, ?, ?, ?, ?,?)";
        jdbcTemplate.update(sqlKlient, klient.getImie(), klient.getNazwisko(),klient.getHaslo(),klient.getRanga(), klient.getAdres().getIdAdres(), klient.getKontakt().getIdKontakt());
    }

    public List<Klient> list() {
        String sql = """
                SELECT 
                    k.ID_KLIENT, 
                    k.IMIE, 
                    k.NAZWISKO, 
                    k.HASLO,
                    k.RANGA,
                    a.KRAJ, 
                    a.MIASTO, 
                    a.ULICA, 
                    a.NUMER_BUDYNKU, 
                    c.NUMER_TELEFONU, 
                    c.ADRES_EMAIL 
                FROM 
                    KLIENCI k 
                LEFT JOIN 
                    ADRESY a 
                ON 
                    k.ID_ADRES = a.ID_ADRES
                LEFT JOIN 
                    Kontakty c 
                ON 
                    k.ID_KONTAKT = c.ID_KONTAKT""";
        return jdbcTemplate.query(sql, new KlientRowMapper());
    }

    private static class KlientRowMapper implements RowMapper<Klient> {
        @Override
        public Klient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Klient klient = new Klient();
            klient.setIdKlient(rs.getInt("ID_KLIENT"));
            klient.setImie(rs.getString("IMIE"));
            klient.setNazwisko(rs.getString("NAZWISKO"));
            klient.setHaslo(rs.getString("HASLO"));
            klient.setRanga(rs.getString("RANGA"));

            Adres adres = new Adres();
            adres.setKraj(rs.getString("KRAJ"));
            adres.setMiasto(rs.getString("MIASTO"));
            adres.setUlica(rs.getString("ULICA"));
            adres.setNumerBudynku(rs.getString("NUMER_BUDYNKU").trim());

            klient.setAdres(adres);

            Kontakt kontakt = new Kontakt();
            kontakt.setTelefon(rs.getString("NUMER_TELEFONU"));
            kontakt.setEmail(rs.getString("ADRES_EMAIL"));

            klient.setKontakt(kontakt);
            return klient;
        }
    }
}
