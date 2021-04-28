/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 09:17:06
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-25 15:18:57
 */
package com.swithun.backend.dto;

public class TeacherGetFileListDTO {

    private Integer id;
    private String name;
    private Integer studentId;
    private String studentName;
    private Integer score;

    public TeacherGetFileListDTO(Integer id, String name, Integer studentId, String studentName, Integer score) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.studentName = studentName;
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
