package com.swithun.backend.controller;

import com.swithun.backend.entity.LoginEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @PostMapping(value = "/login")
    public LoginEntity for_string() {
        LoginEntity le = new LoginEntity();
        le.setUser_id(111);
        le.setUser_name("");
        le.setUser_type(0);
        le.setPass_word("temp_token (登陆验证暂时缺失 = 成功)");
        return le;
    }

    @PostMapping(value = "/string")
    public String login() {
        return "temp_token (登陆验证暂时缺失 = 成功)";
    }
}
