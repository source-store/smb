package ru.smb.smb.control;

/*
 * @author Alexandr.Yakubov
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smb.smb.model.User;
import ru.smb.smb.service.BoxService;
import ru.smb.smb.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * GET    /rest/users                  get all users
 * GET    /rest/users/{id}             get user by id
 * GET    /rest/users/by?login=login   get user by login
 * POST   /rest/users                  create new user
 * PUT    /rest/users/{id}             update user by id
 * DELETE /rest/users/{id}             update user by id
 */
@RestController
@RequestMapping(value = RestUser.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestUser {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    static final String REST_URL = "/rest/users";

    @Autowired
    private UserService service;

    @Autowired
    private BoxService boxService;

    @GetMapping
    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        log.info("get id={}", id);
        return service.get(id);
    }

    @GetMapping("/by")
    public User getByLogin(@RequestParam String login) {
        log.info("getByLogin login={}", login);
        return service.getByLogin(login);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("createWithLocation user={}", user);
        User created = service.save(user);
        try {
            boxService.createBox(created);
        } catch (Exception e) {
            log.error("createBox user={}, {}", user, e.getMessage());
        }
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @PathVariable int id) {
        log.info("update id={}, user={}", id, user);
        user.setId(id);
        service.save(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete id={}", id);
        service.delete(id);
    }

}
