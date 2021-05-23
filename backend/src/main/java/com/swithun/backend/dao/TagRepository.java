/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:15:20
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-22 20:10:03
 */
package com.swithun.backend.dao;

import com.swithun.backend.entity.TagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer>{
    TagEntity findByName(String name);
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
