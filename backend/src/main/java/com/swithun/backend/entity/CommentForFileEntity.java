package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "CommentForFile", schema = "scoring_system", catalog = "")
public class CommentForFileEntity {
    private Integer id;
    private String comments;
    private StudentFileEntity studentFileByStudentFileId;
    private StudentEntity studentByStudentId;
    private TeacherEntity teacherByTeacherId;
    @JsonIgnore
    private CommentForFileEntity commentForFileByParentCommentId;
    private Collection<CommentForFileEntity> commentForFilesById;
    private Collection<TagCommentEntity> tagCommentsById;

    public CommentForFileEntity(String comments, StudentFileEntity studentFileByStudentFileId,
            TeacherEntity teacherByTeacherId, CommentForFileEntity commentForFileByParentCommentId) {
        this.comments = comments;
        this.studentFileByStudentFileId = studentFileByStudentFileId;
        this.teacherByTeacherId = teacherByTeacherId;
        this.commentForFileByParentCommentId = commentForFileByParentCommentId;
    }

    public CommentForFileEntity(String comments, StudentFileEntity studentFileByStudentFileId,
            StudentEntity studentByStudentId, CommentForFileEntity commentForFileByParentCommentId) {
        this.comments = comments;
        this.studentFileByStudentFileId = studentFileByStudentFileId;
        this.studentByStudentId = studentByStudentId;
        this.commentForFileByParentCommentId = commentForFileByParentCommentId;
    }

    public CommentForFileEntity(Integer id) {
        this.id = id;
    }

    public CommentForFileEntity() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CommentForFileEntity that = (CommentForFileEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comments);
    }

    @ManyToOne
    @JoinColumn(name = "student_file_id", referencedColumnName = "id")
    public StudentFileEntity getStudentFileByStudentFileId() {
        return studentFileByStudentFileId;
    }

    public void setStudentFileByStudentFileId(StudentFileEntity studentFileByStudentFileId) {
        this.studentFileByStudentFileId = studentFileByStudentFileId;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    public StudentEntity getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(StudentEntity studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public TeacherEntity getTeacherByTeacherId() {
        return teacherByTeacherId;
    }

    public void setTeacherByTeacherId(TeacherEntity teacherByTeacherId) {
        this.teacherByTeacherId = teacherByTeacherId;
    }

    @ManyToOne
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "id")
    public CommentForFileEntity getCommentForFileByParentCommentId() {
        return commentForFileByParentCommentId;
    }

    public void setCommentForFileByParentCommentId(CommentForFileEntity commentForFileByParentCommentId) {
        this.commentForFileByParentCommentId = commentForFileByParentCommentId;
    }

    @OneToMany(mappedBy = "commentForFileByParentCommentId")
    public Collection<CommentForFileEntity> getCommentForFilesById() {
        return commentForFilesById;
    }

    public void setCommentForFilesById(Collection<CommentForFileEntity> commentForFilesById) {
        this.commentForFilesById = commentForFilesById;
    }

    @OneToMany(mappedBy = "commentForFileByCommentId")
    public Collection<TagCommentEntity> getTagCommentsById() {
        return tagCommentsById;
    }

    public void setTagCommentsById(Collection<TagCommentEntity> tagCommentsById) {
        this.tagCommentsById = tagCommentsById;
    }
}
