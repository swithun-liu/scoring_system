
/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 16:09:28
 */
package com.swithun.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.StudentFileEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired
    private StudentFileRepository studentFileRepository;
    @Autowired
    private StudentRepository studentRepository;

    /**
     * @description: 存储论文
     * @param {MultipartFile} file
     * @param {String}        token
     * @return {*}
     */
    public StudentFileEntity store(MultipartFile file, String username) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        StudentFileEntity studentFileEntity = new StudentFileEntity(filename, file.getContentType(), file.getBytes());

        studentFileEntity.setStudentByStudentId(studentRepository.findByName(username));
        return studentFileRepository.save(studentFileEntity);
    }

    public StudentFileEntity getFile(Integer id) {
        return studentFileRepository.findById(id).get();
    }

    public Stream<StudentFileEntity> getAllFiles() {
        return studentFileRepository.findAll().stream();
    }

    /**
     * @description: 通过学生姓名寻找他上传的文件
     * @param {String} token
     * @return {*}
     */
    public List<StudentFileEntity> studentGetMyPaper(String username) {
        // String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        StudentEntity studentEntity = studentRepository.findByName(username);
        return studentFileRepository.findByStudentByStudentId(studentEntity);
    }

    public StudentFileEntity downloadThisFile(Integer fileId) {
        return studentFileRepository.findById(fileId).get();
    }
}