/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:15:20
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-16 20:16:02
 */
package com.swithun.backend.dao;

import com.swithun.backend.entity.TagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer>{
    
}
/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:15:20
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-16 20:15:20
 */
