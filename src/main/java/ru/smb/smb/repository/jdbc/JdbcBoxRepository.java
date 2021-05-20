package ru.smb.smb.repository.jdbc;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.BoxRepository;
import ru.smb.smb.to.SmbBoxTo;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcBoxRepository implements BoxRepository {

    private static final RowMapper<SmbBox> ROW_MAPPER = BeanPropertyRowMapper.newInstance(SmbBox.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBoxRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SmbBox> getFromBox(User user) {
        return jdbcTemplate.query("SELECT * FROM ut_" + user.getTablename() + " ORDER BY id DESC limit " + user.getBuchsize(), ROW_MAPPER);
    }

    @Override
    @Transactional
    public void putToBox(List<SmbBoxTo> lists, User user) {
        jdbcTemplate.batchUpdate("INSERT INTO ut_" + user.getTablename() + " (tablename, box) VALUES (?, ?)", lists, lists.size(),
                (ps, list) -> {
                    ps.setString(1, user.getTablename());
                    ps.setString(2, list.getBox());
                });
    }

    @Override
    @Transactional
    public void delFromBox(List<SmbBox> lists, User user) {
        jdbcTemplate.batchUpdate("delete from ut_" + user.getTablename() + " where id =?", lists, lists.size(),
                (ps, list) -> {
                    ps.setInt(1, list.getId());
                });

    }


    @Override
    @Transactional
    public void createBox(User user) {
        String table = "ut_" + user.getTablename();
        String sequence = table + "_seq";

        jdbcTemplate.execute("CREATE SEQUENCE " + sequence + " START WITH 100000");

        jdbcTemplate.execute("CREATE TABLE " + table + " (\n" +
                "    id               INTEGER PRIMARY KEY DEFAULT nextval('" + sequence + "'),\n" +
                "    tablename        VARCHAR(30)         NOT NULL,\n" +
                "    box              VARCHAR             NOT NULL,\n" +
                "    registered       TIMESTAMP           DEFAULT now() NOT NULL\n" +
                ")");
    }

}