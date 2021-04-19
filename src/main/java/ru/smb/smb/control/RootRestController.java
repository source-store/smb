package ru.smb.smb.control;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.smb.smb.service.BoxService;
import ru.smb.smb.service.UserService;

@Controller
public class RootRestController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

}
