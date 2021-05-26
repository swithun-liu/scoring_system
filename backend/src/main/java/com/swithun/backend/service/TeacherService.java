/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:48:58
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-26 17:26:38
 */
package com.swithun.backend.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.swithun.backend.dao.StudentFileRepository;
import com.swithun.backend.dao.CommentForFileRepository;
import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.dto.TeacherGetFileListDTO;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.CommentForFileEntity;
import com.swithun.backend.entity.TeacherEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private StudentFileRepository fileR;

    @Autowired
    private TeacherRepository teacherR;

    @Autowired
    private CommentForFileRepository commentR;

    Logger logger = LoggerFactory.getLogger(TeacherService.class);

    // 获取学生文章
    public List<TeacherGetFileListDTO> findStudentFileOfThisTeacher(String teacherName) {
        TeacherEntity teacherEntity = teacherR.findByName(teacherName);
        Integer teacherId = teacherEntity.getId();

        System.out.println("teacher id : " + teacherId);

        List<StudentFileEntity> studentFileEntities = fileR.findAll(new Specification<StudentFileEntity>() {
            @Override
            public Predicate toPredicate(Root<StudentFileEntity> root, CriteriaQuery<?> query,
                    CriteriaBuilder criteriaBuilder) {
                Join<Object, Object> join = root.join("studentByStudentId");
                Path<Object> teacherIdPath = join.get("teacherByTeacherId");
                Predicate predicate = criteriaBuilder.equal(teacherIdPath, teacherId);
                return predicate;
            }
        });
        List<TeacherGetFileListDTO> studentFileDTOs = new ArrayList<TeacherGetFileListDTO>();
        for (var var : studentFileEntities) {
            studentFileDTOs.add(new TeacherGetFileListDTO(var.getId(), var.getName(),
                    var.getStudentByStudentId().getId(), var.getStudentByStudentId().getName(), var.getScore()));
        }

        return studentFileDTOs;
    }

    // 文章评分
    public String score(Map<String, Object> mp) {
        Integer id = (Integer) mp.get("chosedFileId");
        Integer score = (Integer) mp.get("fileScore");

        logger.info("为文件 " + id + " 评分 " + score);

        StudentFileEntity file = fileR.findOneById(id);
        if (file != null) {
            file.setScore(score);
            fileR.save(file);
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    // 获取文章评论
    public List<CommentForFileEntity> getComments(Map<String, Object> mp, Principal principal) {

        Integer fileId = (Integer) mp.get("chosedFileId"); // 文件Id
        String teacherName = principal.getName(); // 教师姓名

        System.out.println("teacherController getAllCommentsOfThisFileOfMine" + fileId + " " + teacherName);

        StudentFileEntity file = new StudentFileEntity(fileId);
        return commentR.findAllByStudentFileByStudentFileIdAndCommentForFileByParentCommentId(file, null);
    }

    // 添加评论
    public void addComment(Map<String, Object> mp, Principal principal) {

        Integer fileId = (Integer) mp.get("chosedFileId");
        String str_comment = (String) mp.get("comment");
        Integer parent_comment_id = (Integer) mp.get("chosedCommentId");
        String teacherName = principal.getName();

        System.out.println("teacherController AddCommentForThisFile" + fileId + " " + str_comment);

        TeacherEntity teacher = teacherR.findByName(teacherName);
        StudentFileEntity file = new StudentFileEntity(fileId);
        CommentForFileEntity parent = null;
        if (parent_comment_id != -1) {
            parent = new CommentForFileEntity(parent_comment_id);
        }
        CommentForFileEntity comment = new CommentForFileEntity(str_comment, file, teacher, parent);
        commentR.save(comment);
    }

}