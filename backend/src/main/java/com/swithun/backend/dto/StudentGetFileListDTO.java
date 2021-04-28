/*
 * @Descripttion: 我的论文
 * @version: 
 * @@Company: None
 * @Author: Swithun Liu
 * @Date: 2021-04-27 13:56:49
 * @LastEditors: Swithun Liu
 * @LastEditTime: 2021-04-27 14:26:54
 */
package com.swithun.backend.dto;

public class StudentGetFileListDTO {
    public StudentGetFileListDTO(Integer id, String name, Integer score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
    public StudentGetFileListDTO() {
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
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    private Integer id;
    private String name;
    private Integer score;
}