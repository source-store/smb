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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smb.smb.SecurityUtil;
import ru.smb.smb.model.SmbBox;
import ru.smb.smb.service.BoxService;
import ru.smb.smb.to.SmbBoxTo;

import java.net.URI;
import java.util.List;

/**
 * GET    /rest/box               get message package (package size setup in user property)
 * POST   /rest/box               put message in the box user
 */
@RestController
@RequestMapping(value = RestBox.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestBox {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String REST_URL = "/rest/box";

    @Autowired
    private BoxService service;

    @GetMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<SmbBox> getAll() {
        log.info("getAll");
        List<SmbBox> resultSmbBox = service.getFromBox(SecurityUtil.safeGet().getUser());
        try {
            if (resultSmbBox != null && resultSmbBox.size() > 0) {
                service.delFromBox(resultSmbBox, SecurityUtil.safeGet().getUser());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return resultSmbBox;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity createWithLocation(@RequestBody List<SmbBoxTo> smbBoxTo) {
        service.putToBox(smbBoxTo, SecurityUtil.safeGet().getUser());
        URI uriOfNewListSmbBox = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).buildAndExpand().toUri();
        return ResponseEntity.created(uriOfNewListSmbBox).build();
    }

}
