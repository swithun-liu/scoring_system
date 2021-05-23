/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:36:39
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-23 20:14:32
 */
package com.swithun.backend.dao;

import com.swithun.backend.entity.TagCommentEntity;
import com.swithun.backend.entity.TagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagCommentRepository
        extends JpaRepository<TagCommentEntity, Integer>, JpaSpecificationExecutor<TagCommentEntity> {
    long countByTagByTagId(TagEntity tag);
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
 * @Date: 2021-05-16 20:36:39
 * 
 * @LastEditors: Swithun Liu
 * 
 * @LastEditTime: 2021-05-16 20:36:40
 */
