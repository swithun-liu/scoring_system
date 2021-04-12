/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-12 20:20:01
 */
package com.swithun.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import com.swithun.backend.dao.FileRepository;
import com.swithun.backend.dao.LoginRepository;
import com.swithun.backend.entity.FileEntity;
import com.swithun.backend.entity.LoginEntity;
import com.swithun.backend.tools.secret.tools.JwtTokenUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public FileEntity store(MultipartFile file, String token) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = new FileEntity(filename,file.getContentType(),file.getBytes());
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        fileEntity.setLoginEntity(loginRepository.findByUsername(username));
        return fileRepository.save(fileEntity);
    }

    public FileEntity getFile(String id){
        return fileRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileRepository.findAll().stream();
    }

    public List<FileEntity> studentGetMyPaper(String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token.substring(7));
        LoginEntity loginEntity = loginRepository.findByUsername(username);
        return fileRepository.findByLoginEntity(loginEntity);
    }
}
