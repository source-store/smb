package ru.smb.smb.model;

/*
 * @autor Alexandr.Yakubov
 **/

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class SmbBox extends AbstractBaseEntity{

    @NotNull
    @NotBlank
    @JsonIgnore
    @Size(min = 5, max = 30)
    private String tablename;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 2000)
    private String box;

    @NotNull
    @JsonIgnore
    private Date registered = new Date();

    public SmbBox() {
    }

    public SmbBox(Integer id, String tablename, String box, Date registered) {
        super(id);
        this.tablename = tablename;
        this.box = box;
        this.registered = registered;
    }

    public SmbBox(SmbBox s) {
        this(s.id, s.tablename, s.box, s.registered);
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

    public String getTablename() {
        return tablename;
    }

    public String getBox() {
        return box;
    }

    public Date getRegistered() {
        return registered;
    }

    @Override
    public String toString() {
        return "SmbBox{" +
                "id=" + id +
                ", tablename=" + tablename +
                ", box=" + box +
                '}';
    }
}
