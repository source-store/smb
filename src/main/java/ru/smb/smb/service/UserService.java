package ru.smb.smb.service;

/*
 * @autor Alexandr.Yakubov
 **/


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import ru.smb.smb.AuthorizedUser;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User get(int id) {
        return repository.get(id);
    }

    public User getByLogin(String login) {
        return repository.getByLogin(login);
    }

    @Cacheable("smb_users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @Transactional
    @CacheEvict(value = "smb_users", allEntries = true)
    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(user.getTablename(), "tablename must not be null");
        Assert.notNull(user.getPassword(), "password must not be null");
        User userCreate = repository.save(prepareToSave(user, passwordEncoder));
        return userCreate;
    }

    @Transactional
    @CacheEvict(value = "smb_users", allEntries = true)
    public void delete(int id) {
        repository.delete(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.getByLogin(login.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("Login " + login + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareToSave(User user, PasswordEncoder passwordEncoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? passwordEncoder.encode(password) : password);
        user.setLogin(user.getLogin().toLowerCase());
        return user;
    }

}
