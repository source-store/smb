package ru.smb.smb.repository;

/*
 * @autor Alexandr.Yakubov
 **/

import ru.smb.smb.model.SmbBox;
import ru.smb.smb.model.User;
import ru.smb.smb.to.SmbBoxTo;

import java.util.List;

public interface BoxRepository {

    List<SmbBox> getFromBox(User user);

    void putToBox(List<SmbBoxTo> lists, User user);

    void delFromBox(List<SmbBox> lists, User user);

    void createBox(User user);
}
