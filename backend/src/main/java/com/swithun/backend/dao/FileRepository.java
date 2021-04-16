/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-03-06 17:40:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-12 20:17:56
 */
package com.swithun.backend.dao;

import java.util.List;

import com.swithun.backend.entity.FileEntity;
import com.swithun.backend.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String>{
    List<FileEntity> findByLoginEntity(StudentEntity loginEntity);
}
