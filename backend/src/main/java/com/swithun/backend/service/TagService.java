/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:12:38
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-16 20:40:33
 */
package com.swithun.backend.service;

import java.util.Map;

import com.swithun.backend.dao.TagCommentRepository;
import com.swithun.backend.dao.TagRepository;
import com.swithun.backend.entity.TagCommentEntity;
import com.swithun.backend.entity.TagEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TagService
 */
@Service
public class TagService {

    @Autowired
    private TagRepository tagR;

    @Autowired
    private TagCommentRepository tagCommentR;

    public void addNewTag(Map<String, Object> mp) {
        String name = (String)mp.get("name");
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tagR.save(tag);
    }

    public void addTagForComment(TagCommentEntity tagComment) {
        tagCommentR.save(tagComment);
    }

}