package com.swithun.backend.dao;

import org.springframework.stereotype.Repository;

import com.swithun.backend.entity.LoginEntity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity,String>{
    
}