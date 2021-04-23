/*
 * @Descripttion: 
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-23 09:17:06
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-23 09:47:46
 */
package com.swithun.backend.dto;

public class studentFileDTO {

    private Integer id;
    private String name;
    private Integer studentId;
    private String studentName;

    public studentFileDTO(Integer id, String name, Integer studentId, String studentName) {
        this.id = id;
        this.name = name;
        this.studentId = studentId;
        this.studentName = studentName;
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
