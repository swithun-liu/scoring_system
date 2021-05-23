/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:09:52
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-22 20:13:40
 */
package com.swithun.backend.controller;

import java.util.List;
import java.util.Map;

import com.swithun.backend.entity.TagCommentEntity;
import com.swithun.backend.entity.TagEntity;
import com.swithun.backend.service.TagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagS;

    Logger logger = LoggerFactory.getLogger(TagController.class);

    @PostMapping(value="/tag/addNew")
    public String addNewTag(@RequestBody Map<String, Object> mp) {
        tagS.addNewTag(mp);
        return "添加成功";
    }

    @GetMapping(value="/tag/getAll")
    public List<TagEntity> getAllTag() {
        return tagS.getAllTag();
    }
    

    @PostMapping(value="/tag/CommentAddTag")
    public String addTagForComment(@RequestBody Map<String, Object> mp) {
        tagS.addTagForComment(mp);
        return "添加成功";
    }
    
}