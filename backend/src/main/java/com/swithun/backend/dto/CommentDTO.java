/*
 * @Descripttion: 老师对文章的评论
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-26 15:09:11
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 21:24:55
 */
package com.swithun.backend.dto;

public class CommentDTO {
    public CommentDTO(String comment, String userName, String nikeName, Integer userType, Integer commentType,
            boolean hasDealt) {
        this.comment = comment;
        this.userName = userName;
        this.nikeName = nikeName;
        this.userType = userType;
        this.commentType = commentType;
        this.hasDealt = hasDealt;
    }
    public CommentDTO() {
    }
    private String comment;
    private String userName;
    private String nikeName;
    private Integer userType;
    private Integer commentType;
    private boolean hasDealt;

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getNikeName() {
        return nikeName;
    }
    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }
    public Integer getUserType() {
        return userType;
    }
    public void setUserType(Integer userType) {
        this.userType = userType;
    }
    public Integer getCommentType() {
        return commentType;
    }
    public void setCommentType(Integer commentType) {
        this.commentType = commentType;
    }
    public boolean isHasDealt() {
        return hasDealt;
    }
    public void setHasDealt(boolean hasDealt) {
        this.hasDealt = hasDealt;
    }
}
