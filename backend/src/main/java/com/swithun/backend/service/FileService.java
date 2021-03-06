package com.swithun.backend.service;

import java.io.IOException;
import java.util.stream.Stream;

import com.swithun.backend.dao.FileRepository;
import com.swithun.backend.entity.FileEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;

    public FileEntity store(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = new FileEntity(filename,file.getContentType(),file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFile(String id){
        return fileRepository.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileRepository.findAll().stream();
    }
}
