package ru.smb.smb.repository;

/*
 * @autor Alexandr.Yakubov
 **/

import ru.smb.smb.model.Role;
import ru.smb.smb.model.User;

import java.util.List;

public interface UserRepository {

    User get(int id);

    User getByLogin(String login);

    List<User> getAll();

    User save(User user);

    void delete(int id);

}
