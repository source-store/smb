package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
public class User extends AbstractBaseEntity{

    @NotBlank
    @Size(min = 4, max = 30)
    private String login;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @NotBlank
    @Size(min = 5, max = 30)
    private String tablename;

    @NotNull
    private Integer buchsize;

    @NotNull
    private boolean enabled = true;

    @NotNull
    private Date registered = new Date();

    private boolean subscriber = Boolean.parseBoolean(null);

    private boolean publisher = Boolean.parseBoolean(null);

    public User() {
    }

    public User(Integer id, String login, String password, String tablename, Integer buchsize, boolean subscriber, boolean publisher) {
        this(id, login, password, tablename, buchsize, true, subscriber, publisher);
    }

    public User(User u) {
        this(u.id, u.login, u.password, u.tablename, u.buchsize, u.enabled, u.subscriber, u.publisher);
    }

    public User(Integer id, String login, String password, String tablename, Integer buchsize, boolean enabled, boolean subscriber, boolean publisher) {
        super(id);
        this.login = login;
        this.password = password;
        this.tablename = tablename;
        this.buchsize = buchsize;
        this.enabled = enabled;
        this.subscriber = subscriber;
        this.publisher = publisher;
    }

    public Integer getBuchsize() {
        return buchsize;
    }

    public String getPassword() {
        return password;
    }

    public String getTablename() {
        return tablename;
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
