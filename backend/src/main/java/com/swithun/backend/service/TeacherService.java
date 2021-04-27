/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:48:58
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-26 09:41:59
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
import com.swithun.backend.dao.TeacherCommentForFileRepository;
import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.dto.studentFileDTO;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.TeacherCommentForFileEntity;
import com.swithun.backend.entity.TeacherEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private StudentFileRepository studentFileRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherCommentForFileRepository teacherCommentForFileRepository;

    public List<studentFileDTO> findStudentFileOfThisTeacher(String teacherName) {

        System.out.println("开始查找 teacher");
        TeacherEntity teacherEntity = teacherRepository.findByName(teacherName);
        Integer teacherId = teacherEntity.getId();
        System.out.println("teacher id : " + teacherId);

        List<StudentFileEntity> studentFileEntities = studentFileRepository.findAll(

                new Specification<StudentFileEntity>() {

                    @Override
                    public Predicate toPredicate(Root<StudentFileEntity> root, CriteriaQuery<?> query,
                            CriteriaBuilder criteriaBuilder) {
                        Join<Object, Object> join = root.join("studentByStudentId");
                        Path<Object> teacherIdPath = join.get("teacherByTeacherId");
                        Predicate predicate = criteriaBuilder.equal(teacherIdPath, teacherId);
                        return predicate;
                    }
                }

        );
        List<studentFileDTO> studentFileDTOs = new ArrayList<studentFileDTO> ();
        for (var var : studentFileEntities) {
            studentFileDTOs.add(new studentFileDTO(var.getId(), var.getName(), var.getStudentByStudentId().getId(), var.getStudentByStudentId().getName(), var.getScore()));
        }

        return studentFileDTOs;
    }

    public String scoreThisPaper(Integer id, Integer score) {
        StudentFileEntity file = studentFileRepository.findOneById(id);
        if (file != null) {
            file.setScore(score);
            studentFileRepository.save(file);
            return "修改成功";
        }
        else{
            return "修改失败";
        }
    }

    public List<TeacherCommentForFileEntity> findAllCommnetsOfThisFileOfThisTeacher(Integer fileId, String teacherName) {
        StudentFileEntity studentFileEntity = new StudentFileEntity(fileId);
        TeacherEntity teacherEntity = teacherRepository.findByName(teacherName);
        return teacherCommentForFileRepository.findAllByStudentFileByStudentFileIdAndTeacherByTeacherId(studentFileEntity, teacherEntity);
    }

    public void addComment(Integer fileId, String comments, String teacherName) {
        TeacherEntity teacher = teacherRepository.findByName(teacherName);
        StudentFileEntity file = studentFileRepository.findOneById(fileId);
        TeacherCommentForFileEntity comment = new TeacherCommentForFileEntity(comments, file, teacher);
        teacherCommentForFileRepository.save(comment);
    }

}