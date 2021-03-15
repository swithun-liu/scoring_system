package com.swithun.backend.controller;

import java.util.Map;

import com.swithun.backend.dao.LoginRepository;
import com.swithun.backend.entity.LoginEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginRepository l;

    @PostMapping(value = "/login")
    public LoginEntity login(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
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
