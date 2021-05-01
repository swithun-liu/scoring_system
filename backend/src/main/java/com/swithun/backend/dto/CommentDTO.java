/*
 * @Descripttion: 老师对文章的评论
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-26 15:09:11
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 21:07:09
 */
package com.swithun.backend.dto;

public class CommentDTO {
    public CommentDTO(String comment, String teacherName) {
		this.comment = comment;
		this.teacherName = teacherName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public CommentDTO(String comment) {
        this.comment = comment;
    }

    public CommentDTO() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
    private String teacherName;
}
