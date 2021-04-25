/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-25 20:15:46
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-25 20:16:17
 */
package com.swithun.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teacherCommentForFile", schema = "scoring_system", catalog = "")
public class TeacherCommentForFileEntity {
    private Integer id;
    private String comments;
    private StudentFileEntity studentFileByStudentFileId;
    private TeacherEntity teacherByTeacherId;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherCommentForFileEntity that = (TeacherCommentForFileEntity) o;
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
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public TeacherEntity getTeacherByTeacherId() {
        return teacherByTeacherId;
    }

    public void setTeacherByTeacherId(TeacherEntity teacherByTeacherId) {
        this.teacherByTeacherId = teacherByTeacherId;
    }
}
