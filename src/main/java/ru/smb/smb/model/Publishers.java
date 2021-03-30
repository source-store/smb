package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "smb_publishers")
public class Publishers extends AbstractBaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "tablename", nullable = false)
    @NotBlank
    @Size(min = 4, max = 20)
    private String tablename;

    @Column(name = "starttime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime starttime;

    @Column(name = "endtime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endtime;

    public Publishers() {
    }

    public Publishers(Publishers s) {
        this(s.id, s.user, s.tablename, s.starttime, s.endtime);
    }
    public Publishers(Integer id, User user, String tablename, LocalTime starttime, LocalTime endtime) {
        super(id);
        this.user = user;
        this.tablename = tablename;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setStarttime(LocalTime starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(LocalTime endtime) {
        this.endtime = endtime;
    }

    public User getUser() {
        return user;
    }

    public String getTablename() {
        return tablename;
    }

    public LocalTime getStarttime() {
        return starttime;
    }

    public LocalTime getEndtime() {
        return endtime;
    }

    @Override
    public String toString() {
        return "Publishers{" +
                "id=" + id +
                ", user_id=" + user.getId() +
                ", tablename='" + tablename +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}
