package ru.smb.smb.service;

/*
 * @autor Alexandr.Yakubov
 **/


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.BoxRepository;
import ru.smb.smb.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;
    private final BoxRepository boxRepository;


    public UserService(UserRepository repository, BoxRepository boxRepository) {
        this.repository = repository;
        this.boxRepository = boxRepository;
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

    @CacheEvict(value = "smb_users", allEntries = true)
    public User save(User user) {
        return repository.save(user);
    }

    @CacheEvict(value = "smb_users", allEntries = true)
    public void delete(int id) {
        repository.delete(id);
    }

    @CacheEvict(value = "smb_users", allEntries = true)
    public void create(User user) {
        Assert.notNull(user, "user must not be null");
        Assert.notNull(user.getTablename(), "tablename must not be null");
        Assert.notNull(user.getPassword(), "password must not be null");
        User check = getByLogin(user.getLogin());
        Assert.isNull(check, "login alrady used");
        user.setId(null);
        save(user);
    }

}
