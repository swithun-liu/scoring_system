/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-21 09:55:16
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-25 21:15:31
 */
package com.swithun.backend.dao;

import com.swithun.backend.entity.TeacherEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {
    TeacherEntity findByName(String username);
}