package bdbt.project.korttenisowy.kort;

import bdbt.project.korttenisowy.adres.Adres;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class KortDAO {
    private final JdbcTemplate jdbcTemplate;

    public KortDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Kort kort) {
        if (kort.getAdres() != null) {
            String sqlAdres = "INSERT INTO Adresy (Kraj, Miasto, Ulica, Numer_Budynku) VALUES (?, ?, ?, ?)";
            KeyHolder keyHolderAdres = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlAdres, new String[]{"ID_Adres"});
                ps.setString(1, kort.getAdres().getKraj());
                ps.setString(2, kort.getAdres().getMiasto());
                ps.setString(3, kort.getAdres().getUlica());
                ps.setString(4, kort.getAdres().getNumerBudynku());
                return ps;
            }, keyHolderAdres);
            kort.getAdres().setIdAdres(keyHolderAdres.getKey().intValue());
        }

        String sqlKort = "INSERT INTO Korty_Tenisowe (ID_Adres, Nazwa, Opis) VALUES (?, ?, ?)";
        KeyHolder keyHolderKort = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlKort, new String[]{"ID_Kort"});
            ps.setObject(1, kort.getAdres() != null ? kort.getAdres().getIdAdres() : null);
            ps.setString(2, kort.getNazwa());
            ps.setString(3, kort.getOpis());
            return ps;
        }, keyHolderKort);

        kort.setIdKort(keyHolderKort.getKey().intValue());
    }


    public List<Kort> findAll() {
        String sql = "SELECT * FROM Korty_Tenisowe";
        return jdbcTemplate.query(sql, new KortRowMapper());
    }

    private static class KortRowMapper implements RowMapper<Kort> {
        @Override
        public Kort mapRow(ResultSet rs, int rowNum) throws SQLException {
            Kort kort = new Kort();
            kort.setIdKort(rs.getInt("ID_Kort"));
            kort.setNazwa(rs.getString("Nazwa"));
            kort.setOpis(rs.getString("Opis"));
            return kort;
        }
    }

    public Kort findById(int idKort) {
        String sql = "SELECT k.*, a.ID_Adres, a.Kraj, a.Miasto, a.Ulica, a.Numer_Budynku " +
                "FROM Korty_Tenisowe k " +
                "LEFT JOIN Adresy a ON k.ID_Adres = a.ID_Adres " +
                "WHERE k.ID_Kort = ?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<Kort>() {
            @Override
            public Kort mapRow(ResultSet rs, int rowNum) throws SQLException {
                Adres adres = rs.getObject("ID_Adres") != null ? new Adres(
                        rs.getInt("ID_Adres"),
                        rs.getString("Kraj"),
                        rs.getString("Miasto"),
                        rs.getString("Ulica"),
                        rs.getString("Numer_Budynku")
                ) : null;

                return new Kort(
                        rs.getInt("ID_Kort"),
                        adres,
                        rs.getString("Nazwa"),
                        rs.getString("Opis")
                );
            }
        }, idKort);
    }

    public void deleteById(int idKort) {
        String sql = "DELETE FROM Korty_Tenisowe WHERE ID_Kort = ?";
        jdbcTemplate.update(sql, idKort);
    }

    public void update(Kort kort) {
        if (kort.getAdres() != null && kort.getAdres().getIdAdres() != 0) {
            String sqlUpdateAdres = """
            UPDATE Adresy
            SET Kraj = ?, Miasto = ?, Ulica = ?, Numer_Budynku = ?
            WHERE ID_Adres = ?
        """;
            jdbcTemplate.update(sqlUpdateAdres,
                    kort.getAdres().getKraj(),
                    kort.getAdres().getMiasto(),
                    kort.getAdres().getUlica(),
                    kort.getAdres().getNumerBudynku(),
                    kort.getAdres().getIdAdres()
            );
        }

        String sqlUpdateKort = """
        UPDATE Korty_Tenisowe
        SET ID_Adres = ?, Nazwa = ?, Opis = ?
        WHERE ID_Kort = ?
    """;
        jdbcTemplate.update(sqlUpdateKort,
                kort.getAdres() != null ? kort.getAdres().getIdAdres() : null,
                kort.getNazwa(),
                kort.getOpis(),
                kort.getIdKort()
        );
    }

}
