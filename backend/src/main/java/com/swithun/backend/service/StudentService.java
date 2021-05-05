/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:58:43
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 12:29:50
 */
package com.swithun.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.StudentRepository;
import com.swithun.backend.dao.CommentForFileRepository;
import com.swithun.backend.dto.AddFileFileListDTO;
import com.swithun.backend.dto.StudentGetFileListDTO;
import com.swithun.backend.dto.CommentDTO;
import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.tools.convert.Entity2DTO;
import com.swithun.backend.entity.CommentForFileEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentR;
    @Autowired
    private StudentFileRepository fileR;
    @Autowired
    private CommentForFileRepository commentR;
    @Autowired
    private Entity2DTO converter;
    
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
    public List<StudentGetFileListDTO> getFileListForMyFiles(String username) {
        StudentEntity student = studentR.findByName(username);
        List<StudentFileEntity> files = fileR.findByStudentByStudentId(student);
        List<StudentGetFileListDTO> fileList = new ArrayList<>();
        for (StudentFileEntity file : files) {
            fileList.add(new StudentGetFileListDTO(file.getId(), file.getName(), file.getScore()));
        }
        return fileList;
    }

    /**
     * @description: 获取选中文件的所有评论
     * @param {Integer} fileId
     * @return {*}
     */
    public List<CommentForFileEntity> getTeacherCommentOfMyFIle(Integer fileId) {
        List<CommentForFileEntity> origin_comments = commentR.findAllByStudentFileByStudentFileIdAndCommentForFileByParentCommentId(new StudentFileEntity(fileId), new CommentForFileEntity(-1));
        return origin_comments;
    }

    public void addComment(Integer fileId, String str_comment, Integer parent_comment_id, String studentName) {
        StudentEntity student = studentR.findByName(studentName);
        StudentFileEntity file = new StudentFileEntity(fileId);
        CommentForFileEntity parent = new CommentForFileEntity(parent_comment_id);
        CommentForFileEntity comment = new CommentForFileEntity(str_comment, file ,student, parent);
        commentR.save(comment);
    }

}
/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 10:58:43
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 10:58:44
 */
