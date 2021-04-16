package com.swithun.backend.dao;

import org.springframework.stereotype.Repository;

import com.swithun.backend.entity.StudentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepository extends JpaRepository<StudentEntity,String>{
    StudentEntity findByUsername(String username);
}