/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-01 21:18:57
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-07 14:25:01
 */
package com.swithun.backend.tools.convert;

import com.swithun.backend.dto.CommentDTO;
import com.swithun.backend.entity.CommentForFileEntity;

import org.springframework.stereotype.Component;

@Component
public class Entity2DTO {
    public CommentDTO CommentForFileEntity2DTO(CommentForFileEntity entity) {
        Integer userType;
        String userName;
        if (entity.getStudentByStudentId() != null) {
            userType = 0;
            userName = entity.getStudentByStudentId().getName();
        } else {
            userType = 1;
            userName = entity.getTeacherByTeacherId().getName();
        }
        CommentDTO dto = new CommentDTO();
        dto.setComment(entity.getComments());
        // todo 确定 nikeName
        dto.setUserName(userName);
        dto.setNikeName("");
        dto.setUserType(userType);
        // todo 确定 评论类型
        dto.setCommentType(0);
        // todo 确定 是否处理过
        dto.setHasDealt(false);
        return dto;
    }
}
