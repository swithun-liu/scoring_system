/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-24 15:20:40
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-24 16:28:35
 */
package com.swithun.backend.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.swithun.backend.service.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class ProfileController {

    @Autowired
    private ProfileService profileS;

    @GetMapping(value="/getProfile")
    public List<Map<String, Object>> getProfile(HttpServletRequest request) {
        return profileS.getProfile(request);
    }
    
}
