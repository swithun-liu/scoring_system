/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:12:38
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-23 20:30:43
 */
package com.swithun.backend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.swithun.backend.dao.CommentForFileRepository;
import com.swithun.backend.dao.TagCommentRepository;
import com.swithun.backend.dao.TagRepository;
import com.swithun.backend.entity.CommentForFileEntity;
import com.swithun.backend.entity.TagCommentEntity;
import com.swithun.backend.entity.TagEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    private Logger logger = LoggerFactory.getLogger(TagService.class);

    @Autowired
    private TagCommentRepository tagCommentR;

    public void addNewTag(Map<String, Object> mp) {
        String name = (String) mp.get("name");
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tagR.save(tag);
    }

    public void addTagForComment(Map<String, Object> mp) {
        TagCommentEntity tagComment = new TagCommentEntity();
        String tagName = (String) mp.get("tagName");
        Integer commentId = (Integer) mp.get("commentId");
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

    public List<List<Object>> statisticTag() {
        // Map<String, Object> mp = new HashMap<String, Object>();
        // List<TagCommentEntity> findAll = tagCommentR.findAll(new
        // Specification<TagCommentEntity>(){

        // @Override
        // public Predicate toPredicate(Root<TagCommentEntity> root, CriteriaQuery<?>
        // query,
        // CriteriaBuilder criteriaBuilder) {
        // Predicate predication = criteriaBuilder.conjunction();
        // Path<Object> tagPath = root.get("tagByTagId");
        // query.groupBy(tagPath);
        // return predication;
        // }
        // });
        // for (TagCommentEntity tagCommentEntity : findAll) {
        // logger.info(tagCommentEntity.getTagByTagId().getName());
        // }

        Map<String, Integer> mp = new HashMap<String, Integer>();
        List<TagCommentEntity> tagComments = tagCommentR.findAll();
        for (TagCommentEntity tagComment : tagComments) {
            if (tagComment.getTagByTagId() != null) {
                if (mp.get(tagComment.getTagByTagId().getName()) == null) {
                    mp.put(tagComment.getTagByTagId().getName(), 0);
                }
                mp.put(tagComment.getTagByTagId().getName(), mp.get(tagComment.getTagByTagId().getName()) + 1);
            }
        }
        List<List<Object>> res = new ArrayList<>();
        Set<String> keySet = mp.keySet();
        for (String tagName : keySet) {
            List<Object> list = new ArrayList<>();
            list.add(tagName);
            list.add(mp.get(tagName));
            res.add(list);
        }
        return res;
    }

}