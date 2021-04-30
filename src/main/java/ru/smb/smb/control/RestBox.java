package ru.smb.smb.control;

/*
 * @author Alexandr.Yakubov
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smb.smb.SecurityUtil;
import ru.smb.smb.View;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.service.BoxService;
import ru.smb.smb.to.SmbBoxTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestBox.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestBox {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/box";

    @Autowired
    private BoxService service;

    @GetMapping
    public List<SmbBox> getAll() {
        log.info("getAll");
        List<SmbBox> resultSmbBox = service.getFromBox(SecurityUtil.safeGet().getUser());
        try {
            if (resultSmbBox != null && resultSmbBox.size() > 0) {
                service.delFromBox(resultSmbBox, SecurityUtil.safeGet().getUser());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }finally {
            return resultSmbBox;
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createWithLocation(@RequestBody List<SmbBoxTo> smbBoxTo) {
        System.out.printf("12");
        service.putToBox(smbBoxTo, SecurityUtil.safeGet().getUser());

        URI uriOfNewListSmbBox = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();

        return ResponseEntity.created(uriOfNewListSmbBox).build();
    }


}
