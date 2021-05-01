/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-25 21:01:32
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 20:49:02
 */
package com.swithun.backend.dao;

import java.util.List;

import com.swithun.backend.entity.CommentForFileEntity;
import com.swithun.backend.entity.StudentFileEntity;
import com.swithun.backend.entity.TeacherEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentForFileRepository extends JpaRepository<CommentForFileEntity, Integer> {
    List<CommentForFileEntity> findAllByStudentFileByStudentFileIdAndTeacherByTeacherId(StudentFileEntity studentFileEntity, TeacherEntity teacherEntity);
    List<CommentForFileEntity> findAllByStudentFileByStudentFileId(StudentFileEntity studentFileEntity);
}
