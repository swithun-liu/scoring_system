/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-05-16 20:03:11
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-22 14:46:20
 */
package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@Entity
@Table(name = "tag_comment", schema = "scoring_system", catalog = "")
public class TagCommentEntity {
    private Integer id;
    private TagEntity tagByTagId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CommentForFileEntity commentForFileByCommentId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TagCommentEntity that = (TagCommentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    public TagEntity getTagByTagId() {
        return tagByTagId;
    }

    public void setTagByTagId(TagEntity tagByTagId) {
        this.tagByTagId = tagByTagId;
    }

    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    public CommentForFileEntity getCommentForFileByCommentId() {
        return commentForFileByCommentId;
    }

    public void setCommentForFileByCommentId(CommentForFileEntity commentForFileByCommentId) {
        this.commentForFileByCommentId = commentForFileByCommentId;
    }
}
