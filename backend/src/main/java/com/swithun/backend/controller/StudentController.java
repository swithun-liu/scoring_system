package com.swithun.backend.controller;

import com.swithun.backend.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * StudentController
 */
@RestController
@CrossOrigin
public class StudentController {

    @Autowired
    private FileService fileService;

    @PostMapping("/studentuploadpaper")
    public String student_upload_paper(@RequestParam("file") MultipartFile file) {
        try {
            fileService.store(file);
            return String.format("File << %s  >> uploaded succesfully", file.getOriginalFilename());
        } catch (Exception e) {
            return String.format("Cannot upload File << %s >> ", file.getOriginalFilename());
        }
    }
}