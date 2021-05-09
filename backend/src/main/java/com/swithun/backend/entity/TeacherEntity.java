/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-05-08 14:28:21
 */
package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "teacher", schema = "scoring_system", catalog = "")
public class TeacherEntity {
    public TeacherEntity(String name) {
        this.name = name;
    }

    public TeacherEntity(Integer id) {
        this.id = id;
    }

    public TeacherEntity() {
    }

    private Integer id;
    private String name;
    @JsonIgnore
    private String password;
    @JsonManagedReference
    private Collection<StudentEntity> studentsById;
    @JsonIgnore
    private Collection<CommentForFileEntity> teacherCommentForFilesById;

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
        TeacherEntity that = (TeacherEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password);
    }

    @OneToMany(mappedBy = "teacherByTeacherId")
    public Collection<StudentEntity> getStudentsById() {
        return studentsById;
    }

    public void setStudentsById(Collection<StudentEntity> studentsById) {
        this.studentsById = studentsById;
    }

    @OneToMany(mappedBy = "teacherByTeacherId")
    public Collection<CommentForFileEntity> getTeacherCommentForFilesById() {
        return teacherCommentForFilesById;
    }

    public void setTeacherCommentForFilesById(Collection<CommentForFileEntity> teacherCommentForFilesById) {
        this.teacherCommentForFilesById = teacherCommentForFilesById;
    }
}
