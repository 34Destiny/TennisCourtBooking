package bdbt.project.korttenisowy.adres;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdresDAO {
    private final JdbcTemplate jdbcTemplate;

    public AdresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
