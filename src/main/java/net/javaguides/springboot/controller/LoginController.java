package net.javaguides.springboot.controller;

import ch.qos.logback.core.model.Model;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller

public class LoginController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }
    @GetMapping("/signup")
    public String signup(Model model) {

        return "signup";
    }
}