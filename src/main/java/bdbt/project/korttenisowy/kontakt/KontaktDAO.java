package bdbt.project.korttenisowy.kontakt;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KontaktDAO {
    private final JdbcTemplate jdbcTemplate;

    public KontaktDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
