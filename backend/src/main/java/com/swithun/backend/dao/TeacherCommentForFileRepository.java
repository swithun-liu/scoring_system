/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-25 21:01:32
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-26 08:50:07
 */
package com.swithun.backend.dao;

import java.util.List;

import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.TeacherCommentForFileEntity;
import com.swithun.backend.entity.TeacherEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCommentForFileRepository extends JpaRepository<TeacherCommentForFileEntity, Integer> {
    List<TeacherCommentForFileEntity> findAllByStudentFileByStudentFileIdAndTeacherByTeacherId(StudentFileEntity studentFileEntity, TeacherEntity teacherEntity);
}
