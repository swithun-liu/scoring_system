package com.swithun.backend.controller;


import com.swithun.backend.dao.LoginRepository;
import com.swithun.backend.entity.LoginEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginRepository l;

    @PostMapping(value = "/login")
    public LoginEntity login() {
        // LoginEntity le = new LoginEntity();
        // le.setUserid(111);
        // le.setUsername("");
        // le.setUsertype(0);
        // le.setPassword("temp_token (登陆验证暂时缺失 = 成功)");
        LoginEntity le = l.findByUsername("javainuse");
        return le;
    }

    @PostMapping(value = "/string")
    public String for_string() {
        return "temp_token (登陆验证暂时缺失 = 成功)";
    }

    @PostMapping(value = "/test_jwt")
    public String test_jwt() {
        return "hello jwt";
    }
}
