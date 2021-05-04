package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
public class User extends AbstractBaseEntity {

    @NotBlank
    @NotNull
    @Size(min = 4, max = 30)
    private String login;

    @NotBlank
    @NotNull
    @Size(min = 5, max = 100)
    private String password;

    @NotBlank
    @NotNull
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


    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_idx")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Role> roles;

    public User() {
    }

    public User(Integer id, String login, String password, String tablename, Integer buchsize, boolean subscriber, boolean publisher, Role role, Role... roles) {
        this(id, login, password, tablename, buchsize, true, subscriber, publisher, EnumSet.of(role, roles));
    }

    public User(User u) {
        this(u.id, u.login, u.password, u.tablename, u.buchsize, u.enabled, u.subscriber, u.publisher, u.getRoles());
    }

    public User(Integer id, String login, String password, String tablename, Integer buchsize, boolean enabled, boolean subscriber, boolean publisher, Collection<Role> roles) {
        super(id);
        this.login = login;
        this.password = password;
        this.tablename = tablename;
        this.buchsize = buchsize;
        this.enabled = enabled;
        this.subscriber = subscriber;
        this.publisher = publisher;
        setRoles(roles);
    }

    public Integer getBuchsize() {
        return buchsize;
    }

    public Set<Role> getRoles() {
        return roles;
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

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setBuchsize(Integer buchsize) {
        this.buchsize = buchsize;
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
                ", roles=" + roles +
                '}';
    }
}
