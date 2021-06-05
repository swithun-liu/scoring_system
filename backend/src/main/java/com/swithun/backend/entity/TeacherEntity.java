/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-04 21:16:50
 */
package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String nickName;
    private Collection<StudentEntity> studentsById;
    @JsonIgnore
    private Collection<CommentForFileEntity> teacherCommentForFilesById;
    private DirectionEntity directionByDirection;

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

    @Basic
    @Column(name = "nick_name")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TeacherEntity that = (TeacherEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(password, that.password)
                && Objects.equals(nickName, that.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, nickName);
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

    @ManyToOne
    @JoinColumn(name = "direction", referencedColumnName = "id")
    public DirectionEntity getDirectionByDirection() {
        return directionByDirection;
    }

    public void setDirectionByDirection(DirectionEntity directionByDirection) {
        this.directionByDirection = directionByDirection;
    }
}
