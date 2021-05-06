/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-06 17:17:36
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-06 19:56:17
 */
package com.swithun.backend.dao;

import com.swithun.backend.entity.AdminEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findByName(String username);
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
 * @Date: 2021-05-06 17:17:36
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-05-06 17:17:37
 */
