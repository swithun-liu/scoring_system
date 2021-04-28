/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 16:03:57
 */
package com.swithun.backend.dao;

import java.util.List;

import com.swithun.backend.entity.StudentEntity;
import com.swithun.backend.entity.StudentFileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentFileRepository extends JpaRepository<StudentFileEntity, Integer>, JpaSpecificationExecutor<StudentFileEntity>{
    List<StudentFileEntity> findByStudentByStudentId(StudentEntity studentEntity);
    StudentFileEntity findOneById(Integer id);
	<S extends StudentFileEntity> S save(S entity);
}
