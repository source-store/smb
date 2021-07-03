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
import org.springframework.util.CollectionUtils;
import ru.smb.smb.model.Role;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.UserRepository;

import java.util.*;

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
        return setRoles(users.stream().findFirst().orElse(null));
    }

    @Override
    public User getByLogin(String login) {
        List<User> users = jdbcTemplate.query("SELECT * FROM smb_users WHERE login=?", ROW_MAPPER, login);
        return setRoles(users.stream().findFirst().orElse(null));
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM smb_users", ROW_MAPPER);

        Map<Integer, Set<Role>> map = new HashMap<>();

        jdbcTemplate.query("SELECT * FROM user_roles", rs -> {
            map.computeIfAbsent(rs.getInt("user_id"), userId -> EnumSet.noneOf(Role.class))
                    .add(Role.valueOf(rs.getString("role")));
        });

        users.forEach(u -> u.setRoles(map.get(u.getId())));
        return users;
    }

    @Override
    public User save(User user) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate).withTableName("smb_users").usingGeneratedKeyColumns("id");

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE smb_users SET login=:login, password=:password, path=:path, 
                       tablename=:tablename, subscriber=:subscriber, publisher=:publisher WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
            deleteRoles(user);
            insertRoles(user);
        }
        return user;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM smb_users WHERE id=?", id);
    }

    private User setRoles(User u) {
        if (u != null) {
            List<Role> roles = jdbcTemplate.queryForList("SELECT role FROM user_roles  WHERE user_id=?", Role.class, u.getId());
            u.setRoles(roles);
        }
        return u;
    }

    private void deleteRoles(User u) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", u.getId());
    }

    private void insertRoles(User u) {
        Set<Role> roles = u.getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?, ?)", roles, roles.size(),
                    (ps, role) -> {
                        ps.setInt(1, u.getId());
                        ps.setString(2, role.name());
                    });
        }
    }

}
