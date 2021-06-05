/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-17 14:26:03
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-06-04 16:36:33
 */
package com.swithun.backend.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "studentFile", schema = "scoring_system", catalog = "")
public class StudentFileEntity {

    public StudentFileEntity(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String name;
    private String type;
    private byte[] data;
    private Integer score;
    private String fileAbstract;

    @JsonIgnore
    private StudentEntity studentByStudentId;
    @JsonIgnore
    private Collection<CommentForFileEntity> teacherCommentForFilesById;
    private DirectionEntity directionByDirection;

    public StudentFileEntity() {
    }

    public StudentFileEntity(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Basic
    @Column(name = "file_abstract", length = 1000)
    public String getFileAbstract() {
        return fileAbstract;
    }

    public void setFileAbstract(String fileAbstract) {
        this.fileAbstract = fileAbstract;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "score")
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Basic
    @Lob
    @Column(name = "data")
    @JsonIgnore
    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        StudentFileEntity that = (StudentFileEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    public StudentEntity getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(StudentEntity studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @OneToMany(mappedBy = "studentFileByStudentFileId", cascade = CascadeType.ALL)
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
