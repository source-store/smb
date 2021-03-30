package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "smb_users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx")})
public class User extends AbstractBaseEntity{


    @Column(name = "login", nullable = false, unique = true)
    @NotBlank
    @Size(min = 4, max = 30)
    private String login;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @Column(name = "subscriber", nullable = false, columnDefinition = "bool default null")
    private boolean subscriber = Boolean.parseBoolean(null);

    @Column(name = "publisher", nullable = false, columnDefinition = "bool default null")
    private boolean publisher = Boolean.parseBoolean(null);

    public User() {
    }

    public User(Integer id, String login, String password, boolean subscriber, boolean publisher) {
        this(id, login, password, true, subscriber, publisher);
    }

    public User(User u) {
        this(u.id, u.login, u.password, u.enabled, u.subscriber, u.publisher);
    }

    public User(Integer id, String login, String password, boolean enabled, boolean subscriber, boolean publisher) {
        super(id);
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setSubscriber(boolean subscriber) {
        this.subscriber = subscriber;
    }

    public void setPublisher(boolean publisher) {
        this.publisher = publisher;
    }

    public String getLogin() {
        return login;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public boolean isSubscriber() {
        return subscriber;
    }

    public boolean isPublisher() {
        return publisher;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login=" + login +
                ", enabled=" + enabled +
                ", subscriber=" + subscriber +
                ", publisher=" + publisher +
                '}';
    }
}
