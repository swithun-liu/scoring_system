/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-17 14:35:43
 */
package com.swithun.backend.dao;

import org.springframework.stereotype.Repository;

import com.swithun.backend.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity,String>{
    StudentEntity findByName(String username);
}