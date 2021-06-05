/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-06-04 21:13:42
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-04 21:56:02
 */
package com.swithun.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "direction", schema = "scoring_system", catalog = "")
public class DirectionEntity {

    private Integer id;
    private String name;
    private DirectionEntity directionByPId;
    private Collection<DirectionEntity> directionsById;
    private Collection<StudentEntity> studentsById;
    private Collection<TeacherEntity> teachersById;
    private Collection<StudentFileEntity> studentFilesById;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectionEntity that = (DirectionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @ManyToOne
    @JoinColumn(name = "p_id", referencedColumnName = "id")
    public DirectionEntity getDirectionByPId() {
        return directionByPId;
    }

    public void setDirectionByPId(DirectionEntity directionByPId) {
        this.directionByPId = directionByPId;
    }

    @OneToMany(mappedBy = "directionByPId")
    public Collection<DirectionEntity> getDirectionsById() {
        return directionsById;
    }

    public void setDirectionsById(Collection<DirectionEntity> directionsById) {
        this.directionsById = directionsById;
    }

    @OneToMany(mappedBy = "directionByDirection")
    public Collection<StudentEntity> getStudentsById() {
        return studentsById;
    }

    public void setStudentsById(Collection<StudentEntity> studentsById) {
        this.studentsById = studentsById;
    }

    @OneToMany(mappedBy = "directionByDirection")
    public Collection<TeacherEntity> getTeachersById() {
        return teachersById;
    }

    public void setTeachersById(Collection<TeacherEntity> teachersById) {
        this.teachersById = teachersById;
    }

    @OneToMany(mappedBy = "directionByDirection")
    public Collection<StudentFileEntity> getStudentFilesById() {
        return studentFilesById;
    }

    public void setStudentFilesById(Collection<StudentFileEntity> studentFilesById) {
        this.studentFilesById = studentFilesById;
    }
}
