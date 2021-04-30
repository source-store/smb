package ru.smb.smb.to;

/*
 * @author Alexandr.Yakubov
 **/

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class SmbBoxTo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4783984044914957103L;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 2000)
    private String box;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30)
    private String tablename;

    private Date starttime = new Date();

    private Date endtime = null;

    public SmbBoxTo() {
    }

    public SmbBoxTo(String box, String tablename, Date starttime, Date endtime) {
        this.box = box;
        this.tablename = tablename;
        this.starttime = starttime;
        this.endtime = endtime;
    }


    public SmbBoxTo(String box, String tablename) {
        this.box = box;
        this.tablename = tablename;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBox() {
        return box;
    }

    public String getTablename() {
        return tablename;
    }

    @Override
    public String toString() {
        return "SmbBoxTo{" +
                "box=" + box +
                ", tablename=" + tablename +
                ", starttime='" + starttime +
                ", endtime=" + endtime +
                '}';
    }
}
