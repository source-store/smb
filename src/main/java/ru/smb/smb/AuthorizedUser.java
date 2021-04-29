package ru.smb.smb;

/*
 * @autor Alexandr.Yakubov
 **/

import ru.smb.smb.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{
    private static final long serialVersionUID = 7496650878122997321L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getLogin(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public int getId() {
        return this.user.getId();
    }

    @Override
    public String toString() {
        return user.toString();
    }
}
