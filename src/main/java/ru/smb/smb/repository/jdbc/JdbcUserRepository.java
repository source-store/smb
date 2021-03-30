package ru.smb.smb.repository.jdbc;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.UserRepository;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM smb_users WHERE id=?", ROW_MAPPER, id);
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public User getByLogin(String login) {
        List<User> users = jdbcTemplate.query("SELECT * FROM smb_users WHERE login=?", ROW_MAPPER, login);
        return users.stream().findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM smb_users", ROW_MAPPER);
    }

    @Override
    public User save(User user) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("smb_users").usingGeneratedKeyColumns("id");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE smb_users SET login=:login, password=:password,
                       tablename=:tablename, subscriber=:subscriber, publisher=:publisher WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
        }
        return user;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM smb_users WHERE id=?", id);
    }
}
