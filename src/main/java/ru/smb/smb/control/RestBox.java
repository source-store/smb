package ru.smb.smb.control;

/*
 * @autor Alexandr.Yakubov
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.smb.smb.service.BoxService;
import ru.smb.smb.service.UserService;

@RestController
@RequestMapping(value = RestBox.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestBox {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/box";

    @Autowired
    private BoxService service;





}
