package com.swithun.backend.controller;

import com.swithun.backend.entity.LoginEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @PostMapping(value = "/login")
    public LoginEntity login() {
        LoginEntity le = new LoginEntity();
        le.setPass_word("password");
        le.setUser_id(111);
        le.setUser_name("user_name");
        return le;
    }

    @PostMapping(value = "/string")
    public String for_string() {
        return "dsfsd";
    }
}
