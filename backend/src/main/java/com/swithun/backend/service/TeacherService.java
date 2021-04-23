/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 08:48:58
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 10:05:44
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
import com.swithun.backend.dao.TeacherRepository;
import com.swithun.backend.dto.studentFileDTO;
import com.swithun.backend.entity.StudentFileEntity;
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
            studentFileDTOs.add(new studentFileDTO(var.getId(), var.getName(), var.getStudentByStudentId().getId(), var.getStudentByStudentId().getName()));
        }

        return studentFileDTOs;

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
 * @Date: 2021-04-23 08:48:58
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-04-23 08:49:00
 */
