/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-18 14:52:49
 */
package com.swithun.backend.controller;

import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.service.FileService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.GetMapping;


/**
 * StudentController
 */
@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    private FileService fileService;

    /**
     * @description: 学生端上传论文
     * @param {*}
     * @return {*}
     */
    @PostMapping("/student/studentuploadpaper")
    public String student_upload_paper(@RequestParam("file") MultipartFile file, @RequestHeader(value = "authorization") String token) {
        try {
            fileService.store(file, token);
            return String.format("File << %s  >> uploaded succesfully", file.getOriginalFilename());
        } catch (Exception e) {
            return String.format("Cannot upload File << %s >> ", file.getOriginalFilename());
        }
    }

    /**
     * @description: 学生端请求自己的论文
     * @param {*}
     * @return {*}
     */
    @GetMapping(value="/student/studentgetmypaper")
    public List<StudentFileEntity> studentGetMyPaper(@RequestHeader(value = "authorization") String token) {
        return fileService.studentGetMyPaper(token);
    }
    
}