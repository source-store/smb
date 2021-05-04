package ru.smb.smb.control;

/*
 * @autor Alexandr.Yakubov
 **/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRestController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

}
