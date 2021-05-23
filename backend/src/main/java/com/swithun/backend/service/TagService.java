/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:12:38
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-22 20:33:19
 */
package com.swithun.backend.service;

import java.util.List;
import java.util.Map;

import com.swithun.backend.dao.CommentForFileRepository;
import com.swithun.backend.dao.TagCommentRepository;
import com.swithun.backend.dao.TagRepository;
import com.swithun.backend.entity.CommentForFileEntity;
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
    private CommentForFileRepository commentR;

    @Autowired
    private TagCommentRepository tagCommentR;
    private TagEntity tag;

    public void addNewTag(Map<String, Object> mp) {
        String name = (String)mp.get("name");
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tagR.save(tag);
    }

    public void addTagForComment(Map<String, Object> mp) {
        TagCommentEntity tagComment = new TagCommentEntity();
        String tagName = (String)mp.get("tagName");
        Integer commentId = (Integer)mp.get("commentId");
        TagEntity tag = tagR.findByName(tagName);
        if (tag == null) {
            TagEntity newTag = new TagEntity();
            newTag.setName(tagName);
            tag = tagR.save(newTag);
        }
        CommentForFileEntity comment = commentR.findById(commentId).get();
        tagComment.setTagByTagId(tag);
        tagComment.setCommentForFileByCommentId(comment);
        tagCommentR.save(tagComment);
    }

    public List<TagEntity> getAllTag() {
        return tagR.findAll();
    }

}