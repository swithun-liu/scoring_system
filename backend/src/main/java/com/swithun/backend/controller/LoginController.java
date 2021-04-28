/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-12 21:42:41
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 10:58:34
 */
package com.swithun.backend.controller;

import java.util.Map;

import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.entity.StudentEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping(value = "/login")
    public StudentEntity login(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(String.format("Header '%s' = %s", key, value));
        });
        StudentEntity le = studentRepository.findByName("javainuse");
        return le;
    }

    @PostMapping(value = "/test_jwt")
    public String test_jwt() {
        return "hello jwt";
    }
}
