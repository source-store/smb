package ru.smb.smb.repository.jdbc;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.BoxRepository;

import java.sql.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcBoxRepository implements BoxRepository {

    private static final RowMapper<SmbBox> ROW_MAPPER = BeanPropertyRowMapper.newInstance(SmbBox.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcBoxRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<SmbBox> getFromBox(User user) {
        return jdbcTemplate.query("SELECT * FROM ut_" + user.getTablename() + " ORDER BY id DESC limit "+user.getBuchsize(), ROW_MAPPER);
    }

    @Override
    public void putToBox(List<SmbBox> lists, User user) {
        jdbcTemplate.batchUpdate("INSERT INTO " + user.getTablename() + " (tablename, box, starttime, endtime) VALUES (?, ?, ?, ?)", lists, lists.size(),
                (ps, list) -> {
                    ps.setString(1, list.getTablename());
                    ps.setString(2, list.getBox());
                    ps.setDate(3, (Date) list.getStarttime());
                    ps.setDate(4, (Date) list.getEndtime());
                });
    }

    @Override
    public void delFromBox(List<SmbBox> lists, User user) {
        jdbcTemplate.batchUpdate("delete from " + user.getTablename() + " where id =", lists, lists.size(),
                (ps, list) -> {
                    ps.setInt(1, list.getId());
                });

    }


    @Override
    public void createBox(User user) {
        String table = "ut_" + user.getTablename();
        String sequence = table + "_seq";

        jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " START WITH 100000");

        jdbcTemplate.execute("CREATE TABLE " + table + " (\n" +
                "    id               INTEGER PRIMARY KEY DEFAULT nextval('" + sequence + "'),\n" +
                "    tablename        VARCHAR(30)         NOT NULL,\n" +
                "    box              VARCHAR             NOT NULL,\n" +
                "    registered       TIMESTAMP           DEFAULT now() NOT NULL,\n" +
                "    starttime        TIMESTAMP           DEFAULT NULL,\n" +
                "    endtime          TIMESTAMP           DEFAULT NULL\n" +
                ")");
    }

}