/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:48:58
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-04 14:18:06
 */
package com.swithun.backend.service;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * @description: 查找所有这名老师的学生的文章
     * @param {String} teacherName
     * @return {*}
     */
    public List<TeacherGetFileListDTO> findStudentFileOfThisTeacher(String teacherName) {

        TeacherEntity teacherEntity = teacherR.findByName(teacherName);
        Integer teacherId = teacherEntity.getId();

        System.out.println("teacher id : " + teacherId);

        List<StudentFileEntity> studentFileEntities = fileR
                .findAll(new Specification<StudentFileEntity>() {
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

    /**
     * @description: 老师为文章打分
     * @param {Integer} id
     * @param {Integer} score
     * @return {*}
     */
    public String scoreThisPaper(Integer id, Integer score) {
        StudentFileEntity file = fileR.findOneById(id);
        if (file != null) {
            file.setScore(score);
            fileR.save(file);
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    /**
     * @description: 查找所有属于这名老师的对于这篇文章的评论
     * @param {Integer} fileId
     * @param {String}  teacherName
     * @return {*}
     */
    public List<CommentForFileEntity> findAllCommnetsOfThisFileOfThisTeacher(Integer fileId,
            String teacherName) {
        StudentFileEntity file = new StudentFileEntity(fileId);
        TeacherEntity teacher = teacherR.findByName(teacherName);
        return commentR.findAllByStudentFileByStudentFileIdAndTeacherByTeacherIdAndCommentForFileByParentCommentId(file, teacher, new CommentForFileEntity(-1));
    }

    /**
     * @description: 老师为文章添加评论
     * @param {Integer} fileId
     * @param {String} comments
     * @param {String} teacherName
     * @return {*}
     */
    public void addComment(Integer fileId, String str_comment, Integer parent_comment_id, String teacherName) {
        TeacherEntity teacher = teacherR.findByName(teacherName);
        // StudentFileEntity file = studentFileRepository.findOneById(fileId);
        StudentFileEntity file = new StudentFileEntity(fileId);
        CommentForFileEntity parent = new CommentForFileEntity(parent_comment_id);
        CommentForFileEntity comment = new CommentForFileEntity(str_comment, file, teacher, parent);
        commentR.save(comment);
    }

}