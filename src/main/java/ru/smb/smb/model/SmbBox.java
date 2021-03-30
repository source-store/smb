package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class SmbBox extends AbstractBaseEntity{

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30)
    private String tablename;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 2000)
    private String box;

    @NotNull
    private Date registered = new Date();

    private Date starttime;

    private Date endtime;

    public SmbBox() {
    }

    public SmbBox(Integer id, String tablename, String box, Date registered, Date starttime, Date endtime) {
        super(id);
        this.tablename = tablename;
        this.box = box;
        this.registered = registered;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public SmbBox(SmbBox s) {
        this(s.id, s.tablename, s.box, s.registered, s.starttime, s.endtime);
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getTablename() {
        return tablename;
    }

    public String getBox() {
        return box;
    }

    public Date getRegistered() {
        return registered;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    @Override
    public String toString() {
        return "SmbBox{" +
                "id=" + id +
                ", tablename=" + tablename +
                ", box=" + box +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}
