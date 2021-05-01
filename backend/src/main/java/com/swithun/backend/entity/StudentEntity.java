/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-01 20:49:13
 */
package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "scoring_system", catalog = "")
public class StudentEntity {
    public StudentEntity(Integer id) {
        this.id = id;
    }

    public StudentEntity() {
    }

    private Integer id;
    private String name;
    private String password;
    @JsonBackReference
    private TeacherEntity teacherByTeacherId;
    @JsonManagedReference
    private Collection<StudentFileEntity> studentFilesById;
    @JsonIgnore
    private Collection<CommentForFileEntity> commentForFilesById;

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
    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentEntity that = (StudentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    public TeacherEntity getTeacherByTeacherId() {
        return teacherByTeacherId;
    }

    public void setTeacherByTeacherId(TeacherEntity teacherByTeacherId) {
        this.teacherByTeacherId = teacherByTeacherId;
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<StudentFileEntity> getStudentFilesById() {
        return studentFilesById;
    }

    public void setStudentFilesById(Collection<StudentFileEntity> studentFilesById) {
        this.studentFilesById = studentFilesById;
    }

    @OneToMany(mappedBy = "studentByStudentId")
    public Collection<CommentForFileEntity> getCommentForFilesById() {
        return commentForFilesById;
    }

    public void setCommentForFilesById(Collection<CommentForFileEntity> commentForFilesById) {
        this.commentForFilesById = commentForFilesById;
    }
}
