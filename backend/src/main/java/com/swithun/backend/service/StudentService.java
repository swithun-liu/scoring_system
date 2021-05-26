/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:58:43
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-26 16:15:28
 */
package com.swithun.backend.service;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.dao.CommentForFileRepository;
import com.swithun.backend.dto.AddFileFileListDTO;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.TagCommentEntity;
import com.swithun.backend.entity.CommentForFileEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentR;
    @Autowired
    private StudentFileRepository fileR;
    @Autowired
    private CommentForFileRepository commentR;

    /**
     * @description: 为 添加论文 页面 获取 文件列表
     * @param {String} username
     * @return {*}
     */
    public List<AddFileFileListDTO> getFileListForAddFile(String username) {
        StudentEntity student = studentR.findByName(username);
        List<StudentFileEntity> files = fileR.findByStudentByStudentId(student);
        List<AddFileFileListDTO> fileList = new ArrayList<>();
        for (StudentFileEntity file : files) {
            fileList.add(new AddFileFileListDTO(file.getName(), "https://www.baidu.com"));
        }
        return fileList;
    }

    /**
     * @description: 为 我的论文 页面 获取 文件列表
     * @param {String} username
     * @return {*}
     */
    public List<StudentFileEntity> getFileListForMyFiles(String username) {
        StudentEntity student = studentR.findByName(username);
        List<StudentFileEntity> files = fileR.findByStudentByStudentId(student);
        return files;
    }

    /**
     * @description: 获取选中文件的所有评论
     * @param {Integer} fileId
     * @return {*}
     */
    public List<CommentForFileEntity> getComments(Integer fileId) {
        List<CommentForFileEntity> origin_comments = commentR
                .findAllByStudentFileByStudentFileIdAndCommentForFileByParentCommentId(new StudentFileEntity(fileId),
                        null);
        return origin_comments;
    }

    public void addComment(Integer fileId, String str_comment, Integer parent_comment_id, String studentName) {
        StudentEntity student = studentR.findByName(studentName);
        StudentFileEntity file = new StudentFileEntity(fileId);
        CommentForFileEntity parent = null;
        if (parent_comment_id != -1) {
            parent = new CommentForFileEntity(parent_comment_id);
        }
        CommentForFileEntity comment = new CommentForFileEntity(str_comment, file, student, parent);
        commentR.save(comment);
    }

    public void addAbstract(StudentFileEntity file) {
        StudentFileEntity oldFile = fileR.findOneById(file.getId());
        oldFile.setFileAbstract(file.getFileAbstract());
        fileR.save(oldFile);
    }

    public void refreshFile(MultipartFile file, Integer id) throws IOException {
        StudentFileEntity original_file = fileR.findById(id).get();
        original_file.setName(file.getOriginalFilename());
        original_file.setType(file.getContentType());
        original_file.setData(file.getBytes());
        fileR.save(original_file);
    }

    public boolean deleteFile(Integer id, Principal principal) {
        StudentFileEntity file = fileR.findById(id).get();
        if (file != null && file.getStudentByStudentId().getName().equals(principal.getName())) {
            fileR.deleteById(id);
            return true;
        }
        return false;
    }

}
/*
 * @Descripttion:
 * 
 * @version:
 * 
 * @@Company: None
 * 
 * @Author: Swithun Liu
 * 
 * @Date: 2021-04-27 10:58:43
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-04-27 10:58:44
 */
