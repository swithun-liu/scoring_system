package com.swithun.backend.controller;

import com.swithun.backend.dao.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    @PostMapping(value = "/login")
    public String login() {
        return "login-return";
    }
    LoginRepository a=loginRepository;
}
