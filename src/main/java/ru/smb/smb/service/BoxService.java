package ru.smb.smb.service;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.stereotype.Service;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.model.User;
import ru.smb.smb.repository.BoxRepository;
import ru.smb.smb.to.SmbBoxTo;

import java.util.List;

@Service
public class BoxService {

    private final BoxRepository repository;

    public BoxService(BoxRepository repository) {
        this.repository = repository;
    }

    public List<SmbBox> getFromBox(User user) {
        List<SmbBox> list = repository.getFromBox(user);
        return list;
    }

    public void createBox(User user) throws Exception {
        repository.createBox(user);
    }

    public void putToBox(List<SmbBoxTo> lists, User user) {
        repository.putToBox(lists, user);
    }

    public void delFromBox(List<SmbBox> lists, User user) {
        repository.delFromBox(lists, user);
    }
}
